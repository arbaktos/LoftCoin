package com.example.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
class CmcCoinsRepoImpl implements CoinsRepo {

    private final CoinApi api;
    private ExecutorService executor;

    private final LoftDatabase db;

    @Inject
    public CmcCoinsRepoImpl(CoinApi api, ExecutorService executor, LoftDatabase db) {

        this.api = api;
        this.db = db;
        this.executor = executor;
    }

    @NonNull
    @Override
    public List<? extends CmcCoin> getListings(@NonNull String currency) throws IOException {
        final Response<Listing> response = api.getListing(currency).execute();
        if (response.isSuccessful()) {
            final Listing listing = response.body();
            if (listing != null && listing.data() != null) {
                return listing.data();
            } else {
                final ResponseBody responseBody = response.errorBody();
                if (responseBody != null) {
                    throw new IOException(responseBody.string());
                }
            }
        }
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public LiveData<List<Coin>> listings(@NonNull Query query) {
        final MutableLiveData<Boolean> refresh = new MutableLiveData<>();
        executor.submit(() -> refresh.postValue(query.forceUpdate() || db.coinsDao().coinsCount() == 0));
        return Transformations.switchMap(refresh, (r) -> {
            if (r) return fetchFromNetwork(query);
            else return fetchFromDb(query);
        });
    }

    private LiveData<List<Coin>> fetchFromDb(Query query) {
        return Transformations.map(db.coinsDao().fetchAll(), (coins) -> new ArrayList<>(coins));
    }

    private LiveData<List<Coin>> fetchFromNetwork(Query query) {
        final MutableLiveData<List<Coin>> liveData = new MutableLiveData<>();
        executor.submit(() -> {
            try {
                final Response<Listing> response = api.getListing(query.currency()).execute();
                if (response.isSuccessful()) {
                    final Listing listing = response.body();
                    if (listing != null) {
                        final List<AutoValue_CmcCoin> cmcCoins = listing.data();
                        saveCoinsIntoDb(cmcCoins);
                        liveData.postValue(new ArrayList<>(cmcCoins));
                    } else {
                        final ResponseBody responseBody = response.errorBody();
                        if (responseBody != null) {
                            throw new IOException(responseBody.string());
                        }
                    }
                }
            } catch (IOException e) {
                Timber.e(e);
            }
        });
        return liveData;
    }

    private void saveCoinsIntoDb(List<? extends Coin> coins) {
        List<RoomCoin> roomCoins = new ArrayList<>(coins.size());
        for (Coin coin: coins) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.price(),
                    coin.change24h(),
                    coin.rank(),
                    coin.id()
            ));
            db.coinsDao().insert(roomCoins);
        }
    }

}
