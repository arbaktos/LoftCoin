package com.example.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.CmcCoinsRepoImpl;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RatesViewmodel extends ViewModel {

    private final MutableLiveData<List<? extends Coin>> coins = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final CoinsRepo repo;

    private Future<?> future;

    public RatesViewmodel() {
        this.repo = new CmcCoinsRepoImpl();
        refresh();
    }

    @NonNull
    LiveData<List<? extends Coin>> coins() {
        return coins;
    }

    final void refresh() {
        future = executor.submit( () -> {
            try {
                this.coins.postValue(repo.getListings("USD"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onCleared() {
        if (future != null) {
            future.cancel(true);
        }
        super.onCleared();
    }
}
