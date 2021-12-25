package com.vasilisasycheva.loftcoin.utils;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;

public interface RxSchedulers {

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler cmp();

    @NonNull
    Scheduler main();
}
