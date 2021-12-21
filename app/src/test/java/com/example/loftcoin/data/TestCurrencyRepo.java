package com.example.loftcoin.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Observable;

public class TestCurrencyRepo extends CurrencyRepoImpl {

    public TestCurrencyRepo(@NonNull Context context) {
        super(context);
    }
}
