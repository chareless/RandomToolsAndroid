package com.deniz.randomtools

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kilo.*
import kotlinx.android.synthetic.main.activity_yuzdelik.*

class KiloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilo)
    }

    fun hesaplaClick(view : View){
        if((erkekCheck.isChecked || kadinCheck.isChecked) && boyText.text.isNotEmpty() && kiloText.text.isNotEmpty()){
            val boy = boyText.text.toString().toDouble()
            val inc = boy / 2.54
            val kilo = kiloText.text.toString().toDouble()
            val bmi = kilo/(boy*boy)*10000
            if(erkekCheck.isChecked){
                val ideal = 50+(2.3*(inc-60))
                var myStr : String = String.format("%.2f", ideal)
                idealText.text = "$myStr kg"
            }
            if(kadinCheck.isChecked){
                val ideal = 45.5+(2.3*(inc-60))
                var myStr : String = String.format("%.2f", ideal)
                idealText.text = "$myStr kg"
            }
            var myStr : String = String.format("%.2f", bmi)
            bmiText.text = "$myStr"
            if(bmi<18.5){
                sonucText.text = "İdeal kilonuzun altındasınız."
            }
            if(bmi>=18.5 && bmi<25){
                sonucText.text = "Kilonuz normal."
            }
            if(bmi>=25 && bmi<30){
                sonucText.text = "İdeal kilonuzun üstündesiniz."
            }
            if(bmi>=30){
                sonucText.text="Obez."
            }
        }
        else{
            sonucText.text = "Lütfen tüm alanları doldurun."
        }
    }

    fun backClick(view: View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}