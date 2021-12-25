package com.vasilisasycheva.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.vasilisasycheva.loftcoin.data.Coin;
import com.vasilisasycheva.loftcoin.data.CoinsRepo;
import com.vasilisasycheva.loftcoin.data.CurrencyRepo;
import com.vasilisasycheva.loftcoin.data.SortBy;
import com.vasilisasycheva.loftcoin.utils.RxSchedulers;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RatesViewModel extends ViewModel {

    private final Subject<Boolean> isRefreshing = BehaviorSubject.create();

    private final Subject<Class<?>> pullToRefresh = BehaviorSubject.createDefault(Void.TYPE);

    private final Subject<SortBy> sortBy = BehaviorSubject.createDefault(SortBy.RANK);

    private final AtomicBoolean forceUpdate = new AtomicBoolean();

    private final Observable<List<Coin>> coins;

    private int sortingIndex = 1;
    private RxSchedulers schedulers;

    @Inject
    public RatesViewModel(CoinsRepo repo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;
        this.coins = pullToRefresh
                .map((ptr) -> CoinsRepo.Query.builder())
                .switchMap((qb) -> currencyRepo.currency()
                        .map((c) -> qb.currency(c.code()))
                )
                .doOnNext((qb) -> forceUpdate.set(true))
                .doOnNext((qb) -> isRefreshing.onNext(true))
                .switchMap((qb) -> sortBy.map(qb::sortBy))
                .map((qb) -> qb.forceUpdate(forceUpdate.getAndSet(false))
                        .build())
                .switchMap(repo::listings)
                .doOnEach((ntf) -> isRefreshing.onNext(false));

    }

    @NonNull
    Observable<List<Coin>> coins() {
        return coins.observeOn(schedulers.main());
    }

    @NonNull
    Observable<Boolean> isRefreshing() {
        return isRefreshing.observeOn(schedulers.main());
    }

    final void refresh() {
        pullToRefresh.onNext(Void.TYPE);
    }

    void switchSortingOrder() {
        sortBy.onNext(SortBy.values()[sortingIndex++ % SortBy.values().length]);
    }
}
