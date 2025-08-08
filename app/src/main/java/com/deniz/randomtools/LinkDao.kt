package com.deniz.randomtools

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface LinkDao {

    @Query("SELECT * FROM Link")
    fun getAll() : Flowable<List<Link>>

    @Insert
    fun insert(link : Link) : Completable

    @Delete
    fun delete(link : Link) : Completable

    @Update
    fun update(link : Link) : Completable



}