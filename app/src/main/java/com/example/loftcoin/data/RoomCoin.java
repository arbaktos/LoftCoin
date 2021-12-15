package com.example.loftcoin.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

@Entity
@AutoValue
abstract class RoomCoin implements Coin {

    static RoomCoin create(String name,
                           String symbol,
                           double price,
                           double change24h,
                           int rank,
                           int id) {
        return new AutoValue_RoomCoin(name, symbol, price, change24h, rank, id);
    }

    @Override
    @PrimaryKey
    @AutoValue.CopyAnnotations
    public abstract int id();
}
