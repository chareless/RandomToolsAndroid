package com.deniz.randomtools

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Sifre(

    @ColumnInfo(name="baslik")
    var baslik:String,

    @ColumnInfo(name="kadi")
    var kadi:String ,

    @ColumnInfo(name="sifre")
    var sifre:String,

    @ColumnInfo(name="aciklama")
    var aciklama:String


): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id=0
}