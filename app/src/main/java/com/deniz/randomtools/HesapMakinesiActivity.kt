package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_hesap_makinesi.*

class HesapMakinesiActivity : AppCompatActivity() {

    private var result : Float = 0.0f
    private var kontrol : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hesap_makinesi)
    }

    fun toplaClick(view : View){
        if(number1Text.text.isNotEmpty() && number2Text.text.isNotEmpty()){
            kontrol = false
            if(!number1Text.text.toString().equals("-") && !number2Text.text.toString().equals("-")
                && !number1Text.text.toString().equals(".") && !number2Text.text.toString().equals(".")){
                kontrol=true
            }
            if(kontrol){
                val number1 = (number1Text.text.toString()).toFloat()
                val number2 = (number2Text.text.toString()).toFloat()
                result = (number1 + number2)
                resultText.text = result.toString()
                var myStr : String = String.format("%.4f", result)
                resultText.text = myStr
            }
        }
    }

    fun eksiClick(view:View){
        if(number1Text.text.isNotEmpty() && number2Text.text.isNotEmpty()){
            kontrol = false
            if(!number1Text.text.toString().equals("-") && !number2Text.text.toString().equals("-")
                && !number1Text.text.toString().equals(".") && !number2Text.text.toString().equals(".")){
                kontrol=true
            }
            if(kontrol){
                val number1 = (number1Text.text.toString()).toFloat()
                val number2 = (number2Text.text.toString()).toFloat()
                result = (number1 - number2)
                resultText.text = result.toString()
                var myStr : String = String.format("%.4f", result)
                resultText.text = myStr
            }
        }
    }

    fun carpiClick(view:View){
        if(number1Text.text.isNotEmpty() && number2Text.text.isNotEmpty()){
            kontrol = false
            if(!number1Text.text.toString().equals("-") && !number2Text.text.toString().equals("-")
                && !number1Text.text.toString().equals(".") && !number2Text.text.toString().equals(".")){
                kontrol=true
            }
            if(kontrol){
                val number1 = (number1Text.text.toString()).toFloat()
                val number2 = (number2Text.text.toString()).toFloat()
                result = (number1 * number2)
                resultText.text = result.toString()
                var myStr : String = String.format("%.4f", result)
                resultText.text = myStr
            }
        }
    }

    fun bolClick(view:View){
        if(number1Text.text.isNotEmpty() && number2Text.text.isNotEmpty()){
            kontrol = false
            if(!number1Text.text.toString().equals("-") && !number2Text.text.toString().equals("-")
                && !number1Text.text.toString().equals(".") && !number2Text.text.toString().equals(".")){
                kontrol=true
            }
            if(kontrol){
                val number1 = (number1Text.text.toString()).toFloat()
                val number2 = (number2Text.text.toString()).toFloat()
                if(number2!=0f){
                    result = (number1 / number2)
                    resultText.text = result.toString()
                    var myStr : String = String.format("%.4f", result)
                    resultText.text = myStr
                }
                else{
                    resultText.text = "Geçersiz İşlem"
                }
            }
        }
    }

    fun backClick(view:View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}