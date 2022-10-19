package com.deniz.randomtools

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class IBAN(

    @ColumnInfo(name="baslik")
    var baslik:String,

    @ColumnInfo(name="banka")
    var banka:String ,

    @ColumnInfo(name="iban")
    var iban:String,

    @ColumnInfo(name="aciklama")
    var aciklama:String


): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id=0
}