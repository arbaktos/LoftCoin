package com.example.loftcoin.utils;

import android.os.Build;
import android.os.LocaleList;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class PriceFormatter implements Formatter<Double> {

    private static final Map<String, Locale> LOCALES = new HashMap<>();

    static {
        LOCALES.put("RUB", new Locale("ru", "RU"));
        LOCALES.put("EUR", Locale.GERMANY);
    }

    @Inject
    public PriceFormatter() {
    }

    public String format(@NonNull String currency, @NonNull Double value) {
        return format(value);
    }

    @NonNull
    @Override
    public String format(@NonNull Double value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return android.icu.text.NumberFormat.getCurrencyInstance().format(value);
        } else {
            return java.text.NumberFormat.getCurrencyInstance().format(value);
        }
    }
}
