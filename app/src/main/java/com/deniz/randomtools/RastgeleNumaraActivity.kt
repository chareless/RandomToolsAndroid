package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_rastgele_numara.*

class RastgeleNumaraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rastgele_numara)
    }

    fun rastgelesecClick(view:View){
        if(sayiText1.text.isNotEmpty() && randomYText.text.isNotEmpty()){
            val x = Integer.parseInt(sayiText1.text.toString())
            val y = Integer.parseInt(randomYText.text.toString())
            if(x>y){
                resultText.setText("Hatalı İşlem")
            }
            else
            {
                val result = (x..y).random()
                resultText.setText(result.toString())
            }

        }
    }

    fun backClick(view:View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}