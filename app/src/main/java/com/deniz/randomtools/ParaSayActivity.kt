package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_para_say.*

class ParaSayActivity : AppCompatActivity() {

    private var toplamTL:Int = 0
    private var toplamKrs:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_para_say)
    }

    fun hesaplaClick(view : View){

        if(tl200Text.text.isEmpty()){
            tl200Text.setText("0")
        }
        if(tl100Text.text.isEmpty()){
            tl100Text.setText("0")
        }
        if(tl50Text.text.isEmpty()){
            tl50Text.setText("0")
        }
        if(tl20Text.text.isEmpty()){
            tl20Text.setText("0")
        }
        if(tl10Text.text.isEmpty()){
            tl10Text.setText("0")
        }
        if(tl5Text.text.isEmpty()){
            tl5Text.setText("0")
        }
        if(tl1Text.text.isEmpty()){
            tl1Text.setText("0")
        }
        if(krs50Text.text.isEmpty()){
            krs50Text.setText("0")
        }
        if(krs25Text.text.isEmpty()){
            krs25Text.setText("0")
        }
        if(krs10Text.text.isEmpty()){
            krs10Text.setText("0")
        }
        if(krs5Text.text.isEmpty()){
            krs5Text.setText("0")
        }
        if(krs1Text.text.isEmpty()){
            krs1Text.setText("0")
        }

        toplamTL = (tl200Text.text.toString().toInt()*200
                + tl100Text.text.toString().toInt()*100
                + tl50Text.text.toString().toInt()*50
                + tl20Text.text.toString().toInt()*20
                + tl10Text.text.toString().toInt()*10
                + tl5Text.text.toString().toInt()*5
                + tl1Text.text.toString().toInt()*1)

        toplamKrs = (krs50Text.text.toString().toInt()*50
                    + krs25Text.text.toString().toInt()*25
                    + krs10Text.text.toString().toInt()*10
                    + krs5Text.text.toString().toInt()*5
                    + krs1Text.text.toString().toInt()*1)

        toplamTL += toplamKrs/100
        toplamKrs %= 100
        paraText.text="$toplamTL TL $toplamKrs KRŞ"
    }

    fun temizleClick(view : View){
        tl200Text.setText("0")
        tl100Text.setText("0")
        tl50Text.setText("0")
        tl20Text.setText("0")
        tl10Text.setText("0")
        tl5Text.setText("0")
        tl1Text.setText("0")
        krs50Text.setText("0")
        krs25Text.setText("0")
        krs10Text.setText("0")
        krs5Text.setText("0")
        krs1Text.setText("0")
        toplamTL=0
        toplamKrs=0
        paraText.text="${toplamTL} TL ${toplamKrs} KRŞ"
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}