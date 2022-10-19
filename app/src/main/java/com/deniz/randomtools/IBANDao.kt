package com.deniz.randomtools

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface IBANDao {

    @Query("SELECT * FROM IBAN")
    fun getAll() : Flowable<List<IBAN>>

    @Insert
    fun insert(iban : IBAN) : Completable

    @Delete
    fun delete(iban : IBAN) : Completable



}