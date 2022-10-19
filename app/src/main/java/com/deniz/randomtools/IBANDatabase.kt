package com.deniz.randomtools

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [IBAN::class],version=1)
abstract class IBANDatabase : RoomDatabase() {

    abstract fun IBANDao():IBANDao

}