package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.example.loftcoin.utils.RxSchedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestRxSchedulers implements RxSchedulers {
    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler cmp() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler main() {
        return Schedulers.trampoline();
    }
}
