package com.deniz.randomtools

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface SifreDao {

    @Query("SELECT * FROM Sifre")
    fun getAll() : Flowable<List<Sifre>>

    @Insert
    fun insert(sifre : Sifre) : Completable

    @Delete
    fun delete(sifre : Sifre) : Completable

    @Update
    fun update(sifre : Sifre) : Completable



}