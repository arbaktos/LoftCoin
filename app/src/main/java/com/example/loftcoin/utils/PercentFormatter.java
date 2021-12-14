package com.example.loftcoin.utils;

import androidx.annotation.NonNull;

import java.util.Locale;

public class PercentFormatter implements Formatter<Double> {
    @NonNull
    @Override
    public String format(@NonNull Double value) {
        return String.format(Locale.US,"%.2f%%", value);
    }
}
