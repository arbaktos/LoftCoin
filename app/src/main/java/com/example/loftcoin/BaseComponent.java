package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;


public interface BaseComponent {

    abstract Context context();

    abstract CoinsRepo coinsRepo();

    //abstract CurrencyRepo currencysRepo(); TODO
}
