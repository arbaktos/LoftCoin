package com.vasilisasycheva.loftcoin.data;


import androidx.annotation.WorkerThread;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
abstract class CoinsDao {

    @Query("SELECT * from RoomCoin")
    abstract Observable<List<RoomCoin>> fetchAll();

    @Query("SELECT * from RoomCoin ORDER BY price DESC")
    abstract Observable<List<RoomCoin>> fetchAllSortByPrice();

    @Query("SELECT * from RoomCoin ORDER BY rank ASC")
    abstract Observable<List<RoomCoin>> fetchAllSortByRank();

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(List<RoomCoin> coins);

    @Query("SELECT COUNT(id) FROM RoomCoin")
    abstract int coinsCount();

    @Query("SELECT * from RoomCoin WHERE id=:id")
    public abstract Single<RoomCoin> fetchOne(Long id);

    @Query("SELECT * FROM RoomCoin WHERE id NOT IN (:ids) ORDER BY rank ASC LIMIT 1")
    abstract Single<RoomCoin> nextPopularCoin(List<Integer> ids);

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC LIMIT :limit")
    public abstract Observable<List<RoomCoin>> fetchTop(int limit);
}
