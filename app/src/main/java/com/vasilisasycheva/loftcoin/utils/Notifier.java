package com.vasilisasycheva.loftcoin.utils;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

public interface Notifier {
    @NonNull
    Completable sendMessage(@NonNull String title,
                            @NonNull String message,
                            @NonNull Class<?> receiver);
}
