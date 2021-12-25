package com.vasilisasycheva.loftcoin.utils;

import androidx.annotation.NonNull;

public interface Formatter<T> {

    @NonNull
    String format(@NonNull T value);
}
