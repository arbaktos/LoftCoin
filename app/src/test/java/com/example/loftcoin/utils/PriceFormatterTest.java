package com.example.loftcoin.utils;


import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.NumberFormat;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class PriceFormatterTest {

    private PriceFormatter formatter;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        formatter = new PriceFormatter(context);
    }

    @Test
    public void format_Eur() {
        assertThat(
                formatter.format("EUR", 1.23)).isEqualTo(NumberFormat
                .getCurrencyInstance(Locale.FRANCE).format(1.23));
    }

    @Test
    public void format_Rub() {
        assertThat(
                formatter.format("RUB", 1.23)).isEqualTo(NumberFormat
                .getCurrencyInstance(new Locale("ru", "RU")).format(1.23));
    }

    @Test
    public void format_default() {
        LocaleListCompat locales = ConfigurationCompat.getLocales(context.getResources().getConfiguration());
        assertThat(
                formatter.format("CAD", 1.23)).isEqualTo(NumberFormat
                .getCurrencyInstance(locales.get(0)).format(1.23));
    }
}