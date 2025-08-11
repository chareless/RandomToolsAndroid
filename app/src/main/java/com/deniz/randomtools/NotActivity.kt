package com.deniz.randomtools

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.deniz.randomtools.databinding.ActivityNotBinding

class NotActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityNotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("com.deniz.randomtools", Context.MODE_PRIVATE)
        val not = sharedPreferences.getString("not", "") ?: ""
        binding.notText.setText(not)
    }

    fun kaydetClick(view: View) {
        val not = binding.notText.text.toString()
        sharedPreferences.edit().putString("not", not).apply()
        Toast.makeText(this, "Not kaydedildi.", Toast.LENGTH_SHORT).show()
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
