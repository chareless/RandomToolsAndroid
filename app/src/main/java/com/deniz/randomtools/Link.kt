package com.deniz.randomtools

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Link(

    @ColumnInfo(name="baslik")
    var baslik:String,

    @ColumnInfo(name="link")
    var link:String ,

    @ColumnInfo(name="aciklama")
    var aciklama:String


): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id=0
}