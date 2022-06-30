package com.deniz.randomtools

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_not.*

class NotActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    private lateinit var not : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not)
        sharedPreferences = this.getSharedPreferences("com.deniz.randomtools", Context.MODE_PRIVATE)
        not = sharedPreferences.getString("not","").toString()
        notText.setText(not)
    }

    fun kaydetClick(view : View) {
        not = notText.text.toString()
        sharedPreferences.edit().putString("not",not).apply()
        Toast.makeText(this,"Not kaydedildi.",Toast.LENGTH_SHORT).show()
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}