package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class TestCoinsRepo implements CoinsRepo{

    public final Subject<List<Coin>> listings = PublishSubject.create();

    public Query lastListingsQuery;


    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {
        lastListingsQuery = query;
        return listings;
    }

    @NonNull
    @Override
    public Single<Coin> coin(Currency currency, Long id) {
        return Single.error(() -> new AssertionError("Stub!"));
    }
}
