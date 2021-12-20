package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.utils.ImageLoader;
import com.example.loftcoin.utils.Notifier;
import com.example.loftcoin.utils.RxSchedulers;


public interface BaseComponent {

    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    ImageLoader imageLoader();

    RxSchedulers rxSchedulers();

    WalletsRepo walletsRepo();

    Notifier notifier();
}
