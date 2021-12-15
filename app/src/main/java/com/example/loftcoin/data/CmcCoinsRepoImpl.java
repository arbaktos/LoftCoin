package com.example.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.io.IOException;
import java.util.ArrayList;
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
    public LiveData<List<Coin>> listings(@NonNull Query query) {
        fetchFromNetworkIfNecessary(query);
        return fetchFromDb(query);
    }

    private LiveData<List<Coin>> fetchFromDb(Query query) {
        LiveData<List<RoomCoin>> coins;
        if (query.sortBy() == SortBy.PRICE) {
            coins = db.coinsDao().fetchAllSortByPrice();
        } else {
            coins = db.coinsDao().fetchAllSortByRank();
        }
        return Transformations.map(coins, ArrayList::new);
    }

    private void fetchFromNetworkIfNecessary(Query query) {
        executor.submit(() -> {
            if (query.forceUpdate() || db.coinsDao().coinsCount() == 0) {
                try {
                    final Response<Listing> response = api.getListing(query.currency()).execute();
                    if (response.isSuccessful()) {
                        final Listing listing = response.body();
                        if (listing != null) {
                            saveCoinsIntoDb(query, listing.data());
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
            }
        });
    }

    private void saveCoinsIntoDb(Query query, List<? extends Coin> coins) {
        List<RoomCoin> roomCoins = new ArrayList<>(coins.size());
        for (Coin coin: coins) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.price(),
                    coin.change24h(),
                    coin.rank(),
                    query.currency(),
                    coin.id()
            ));
            db.coinsDao().insert(roomCoins);
        }
    }

}
