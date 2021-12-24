package com.vasilisasycheva.loftcoin.utils;

import androidx.annotation.NonNull;

import com.vasilisasycheva.loftcoin.data.Wallet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BalanceFormatter implements Formatter<Wallet>{

    @Inject
    public BalanceFormatter() {
    }

    @NonNull
    @Override
    public String format(@NonNull Wallet value) {
        final DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.CANADA_FRENCH);
        final DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(value.coin().symbol());
        format.setDecimalFormatSymbols(symbols);
        return format.format(value.balance());
    }
}
