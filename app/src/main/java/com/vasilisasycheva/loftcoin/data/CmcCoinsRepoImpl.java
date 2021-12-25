package com.vasilisasycheva.loftcoin.data;

import androidx.annotation.NonNull;

import com.vasilisasycheva.loftcoin.utils.RxSchedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

@Singleton
class CmcCoinsRepoImpl implements CoinsRepo {

    private final CoinApi api;

    private final LoftDatabase db;
    private RxSchedulers schedulers;

    @Inject
    public CmcCoinsRepoImpl(CoinApi api, LoftDatabase db, RxSchedulers schedulers) {

        this.api = api;
        this.db = db;
        this.schedulers = schedulers;
    }

    @NonNull
    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {
        return Observable
                .fromCallable(() -> query.forceUpdate() || db.coinsDao().coinsCount() == 0)
                .switchMap((f) -> f ? api.getListing(query.currency()) : Observable.empty())
                .map((listings) -> mapToRoomCoins(query, listings.data()))
                .doOnNext((coins) -> db.coinsDao().insert(coins))
                .switchMap((coins) -> fetchFromDb(query))
                .switchIfEmpty(fetchFromDb(query))
                .<List<Coin>>map(Collections::unmodifiableList)
                .subscribeOn(schedulers.io())
                ;
    }

    @NonNull
    @Override
    public Single<Coin> coin(Currency currency, Long id) {
        return listings(Query.builder().currency(currency.code()).forceUpdate(false).sortBy(SortBy.RANK).build())
                .switchMapSingle((coins) -> db.coinsDao().fetchOne(id))
                .firstOrError()
                .map((coin) -> coin);
    }

    @Override
    public Observable<List<Coin>> topCoins(Currency currency) {
        return listings(Query.builder().currency(currency.code()).forceUpdate(false).sortBy(SortBy.RANK).build())
                .switchMap((coins) -> db.coinsDao().fetchTop(3))
                .map(Collections::unmodifiableList);
    }

    private ObservableSource<List<RoomCoin>> fetchFromDb(Query query) {
        if (query.sortBy() == SortBy.PRICE) {
            return db.coinsDao().fetchAllSortByPrice();
        } else {
            return db.coinsDao().fetchAllSortByRank();
        }
    }

    private List<RoomCoin> mapToRoomCoins(Query query, List<? extends Coin> coins) {
        List<RoomCoin> roomCoins = new ArrayList<>(coins.size());
        for (Coin coin : coins) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.price(),
                    coin.change24h(),
                    coin.rank(),
                    query.currency(),
                    coin.id()
            ));
        }
        return roomCoins;
    }
}
