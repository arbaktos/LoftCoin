package com.vasilisasycheva.loftcoin.data;

import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
public abstract class Transaction {

    public static Transaction create(String uid,
                                     Coin coin,
                                     double amount,
                                     Date time) {
        return new AutoValue_Transaction(uid, coin, amount, time);
    }

    public abstract String uid();

    public abstract Coin coin();

    public abstract Double amount();

    public abstract Date time();


}
