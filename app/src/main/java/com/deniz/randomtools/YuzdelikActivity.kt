package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kura.sayiText1
import kotlinx.android.synthetic.main.activity_yuzdelik.*

class YuzdelikActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yuzdelik)
    }

    fun sonuc1Hesapla(){
        if(sayiText1.text.isNotEmpty() && sayiText2.text.isNotEmpty()){
            val a = sayiText1.text.toString().toFloat()
            val b = sayiText2.text.toString().toFloat()
            if(a == 0f || b == 0f){
                sonuc1Text.text = "Geçersiz İşlem"
            }
            else{
                val c = a/100*b
                sonuc1Text.text = c.toString()
                var myStr : String = String.format("%.4f", c)
                sonuc1Text.text = myStr
            }
        }
    }

    fun sonuc2Hesapla(){
        if(sayiText3.text.isNotEmpty() && sayiText4.text.isNotEmpty()){
            val a = sayiText3.text.toString().toFloat()
            val b = sayiText4.text.toString().toFloat()
            if(a == 0f || b == 0f){
                sonuc2Text.text = "Geçersiz İşlem"
            }
            else{
                val c = 100*b/a
                sonuc2Text.text = c.toString()
                var myStr : String = String.format("%.4f", c)
                sonuc2Text.text = myStr
            }
        }
    }

    fun sonuc3Hesapla(){
        if(sayiText5.text.isNotEmpty() && sayiText6.text.isNotEmpty()){
            val a = sayiText5.text.toString().toFloat()
            val b = sayiText6.text.toString().toFloat()
            if(a == 0f || b == 0f){
                sonuc3Text.text = "Geçersiz İşlem"
            }
            else{
                val c = 100*a/b
                sonuc3Text.text = c.toString()
                var myStr : String = String.format("%.4f", c)
                sonuc3Text.text = myStr
            }
        }
    }

    fun sonuc4Hesapla(){
        if(sayiText7.text.isNotEmpty() && sayiText8.text.isNotEmpty()){
            val a = sayiText7.text.toString().toFloat()
            val b = sayiText8.text.toString().toFloat()
            if(a == 0f || b == 0f){
                sonuc4Text.text = "Geçersiz İşlem"
            }
            else{
                val c = a + a*b/100
                sonuc4Text.text = c.toString()
                var myStr : String = String.format("%.4f", c)
                sonuc4Text.text = myStr
            }
        }
    }

    fun sonuc5Hesapla(){
        if(sayiText9.text.isNotEmpty() && sayiText10.text.isNotEmpty()){
            val a = sayiText9.text.toString().toFloat()
            val b = sayiText10.text.toString().toFloat()
            if(a == 0f || b == 0f){
                sonuc5Text.text = "Geçersiz İşlem"
            }
            else{
                val c = a - a*b/100
                sonuc5Text.text = c.toString()
                var myStr : String = String.format("%.4f", c)
                sonuc5Text.text = myStr
            }
        }
    }

    fun yuzdeClick(view: View){
        sonuc1Hesapla()
        sonuc2Hesapla()
        sonuc3Hesapla()
        sonuc4Hesapla()
        sonuc5Hesapla()
    }

    fun temizleClick(view : View){
        sayiText1.setText("")
        sayiText2.setText("")
        sayiText3.setText("")
        sayiText4.setText("")
        sayiText5.setText("")
        sayiText6.setText("")
        sayiText7.setText("")
        sayiText8.setText("")
        sayiText9.setText("")
        sayiText10.setText("")
        sonuc1Text.text = "Sonuç"
        sonuc2Text.text = "Sonuç"
        sonuc3Text.text = "Sonuç"
        sonuc4Text.text = "Sonuç"
        sonuc5Text.text = "Sonuç"
    }

    fun backClick(view: View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}