package com.deniz.randomtools

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sifre::class],version=1)
abstract class SifreDatabase : RoomDatabase() {

    abstract fun sifreDao():SifreDao

}