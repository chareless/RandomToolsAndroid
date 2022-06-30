package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kura.*

class KuraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kura)
    }

    fun kuracekClick(view: View){
        if(kuraKatilanlarText.text.isNotEmpty() && sayiText1.text.isNotEmpty()){
            val katilimcilar = kuraKatilanlarText.text.split("\n")
            val kazanacakSayi = Integer.parseInt(sayiText1.text.toString())
            var kazananlar : String? = ""
            val mySet = mutableSetOf<Int>()
            val myNumberSet = mutableSetOf<Int>()
            if(kazanacakSayi>0 && katilimcilar.size>1 && kazanacakSayi<=katilimcilar.size){
                for(i in katilimcilar.indices){
                    myNumberSet.add(i)
                }
                for(i in 0 until kazanacakSayi){
                    val randomNumber = (0 until myNumberSet.size).random()
                    mySet.add(myNumberSet.elementAt(randomNumber))
                    myNumberSet.remove(myNumberSet.elementAt(randomNumber))
                }
                for(i in 0 until mySet.size){
                    kazananlar += katilimcilar[mySet.elementAt(i)] + "\n"
                }
                kuraKazananlarText.setText(kazananlar)
            }
            else{
                kuraKazananlarText.setText("Hatalı İşlem")
            }
        }
        else{
            kuraKazananlarText.setText("Hatalı İşlem")
        }
    }

    fun backClick(view:View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}