package com.example.loftcoin.data;


import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
abstract class CoinsDao {

    @Query("SELECT * from RoomCoin")
    abstract LiveData<List<RoomCoin>> fetchAll();

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(List<RoomCoin> coins);

    @Query("SELECT COUNT(id) FROM RoomCoin")
    abstract int coinsCount();
}
