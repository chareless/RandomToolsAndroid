package com.deniz.randomtools

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Link::class],version=1)
abstract class LinkDatabase : RoomDatabase() {

    abstract fun LinkDao():LinkDao

}