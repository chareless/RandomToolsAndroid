package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_rastgele_sifre.*

class RastgeleSifreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rastgele_sifre)
    }

    fun olusturClick(view: View){
        if(sayiText1.text.isNotEmpty()){
            val sifreSayi = Integer.parseInt(sayiText1.text.toString())

            if(sifreSayi in 1..16){
                var totalChars : String=""
                var pass:String = ""
                val kucuk = kucukCheck.isChecked
                val buyuk = buyukCheck.isChecked
                val ozel = ozelCheck.isChecked
                val sayi = sayiCheck.isChecked

                if(kucuk){
                    totalChars+="abcdefghijklmnopqrstuvwxyz"
                }
                if(buyuk){
                    totalChars+="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                }
                if(sayi){
                    totalChars+="0123456789"
                }
                if(ozel){
                    totalChars+="@#=+!£$%&?"
                }

                if(kucuk || buyuk || ozel || sayi){
                    for(i in 1..sifreSayi){
                        pass += totalChars[Math.floor(Math.random() * totalChars.length).toInt()].toString()
                    }
                    sifreTextView.text = pass.toString()
                }
                else{
                    sifreTextView.text = "En az 1 karakter türü seçin."
                }
            }
            else{
                sifreTextView.text = "Karakter aralığı (0,17) olmalı."
            }
        }
        else{
            sifreTextView.text = "Karakter sayısını girin."
        }
    }

    fun sifrekopyalaClick(view:View){
        val clipboard = getSystemService(ClipboardManager::class.java)
        val clip = ClipData.newPlainText("randomtools",sifreTextView.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this,"Şifre kopyalandı",Toast.LENGTH_SHORT).show()
    }

    fun backClick(view: View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}