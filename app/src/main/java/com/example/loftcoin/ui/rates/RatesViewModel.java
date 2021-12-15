package com.example.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.CmcCoin;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import timber.log.Timber;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();

    private final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>(false);

    private final LiveData<List<Coin>> coins;

    @Inject
    public RatesViewModel(CoinsRepo repo, CurrencyRepo currencyRepo) {
        LiveData<CoinsRepo.Query> query = Transformations.switchMap(forceRefresh, (r) -> {
            isRefreshing.postValue(true);
            return Transformations.map(currencyRepo.currency(), (c) -> {
                return CoinsRepo.Query.builder()
                        .currency(c.code())
                        .forceUpdate(r)
                        .build();
            });
        });
        final LiveData<List<Coin>> coins = Transformations.switchMap(query, repo::listings);
        this.coins = Transformations.map(coins, (c) -> {
            isRefreshing.postValue(false);
            return c;
        });
    }

    @NonNull
    LiveData<List<Coin>> coins() {
        return coins;
    }

    @NonNull
    LiveData<Boolean> isRefreshing() {
        return isRefreshing;
    }

    final void refresh() {
        forceRefresh.postValue(true);
    }
}
