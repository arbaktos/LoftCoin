package com.vasilisasycheva.loftcoin;

import android.content.Context;

import com.vasilisasycheva.loftcoin.data.CoinsRepo;
import com.vasilisasycheva.loftcoin.data.CurrencyRepo;
import com.vasilisasycheva.loftcoin.data.WalletsRepo;
import com.vasilisasycheva.loftcoin.utils.ImageLoader;
import com.vasilisasycheva.loftcoin.utils.Notifier;
import com.vasilisasycheva.loftcoin.utils.RxSchedulers;


public interface BaseComponent {

    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    ImageLoader imageLoader();

    RxSchedulers rxSchedulers();

    WalletsRepo walletsRepo();

    Notifier notifier();
}
