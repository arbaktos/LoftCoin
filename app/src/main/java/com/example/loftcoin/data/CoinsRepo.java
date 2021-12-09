package com.example.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.io.IOException;
import java.util.List;

public interface CoinsRepo {

    @NonNull
    @WorkerThread
    List<? extends Coin> getListings(@NonNull String currency) throws IOException;
}
