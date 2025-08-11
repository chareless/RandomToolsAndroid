package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityRastgeleNumaraBinding

class RastgeleNumaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRastgeleNumaraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRastgeleNumaraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun rastgelesecClick(view: View) {
        val sayi1Text = binding.sayiText1.text.toString()
        val randomYText = binding.randomYText.text.toString()

        if (sayi1Text.isNotEmpty() && randomYText.isNotEmpty()) {
            val x = sayi1Text.toIntOrNull()
            val y = randomYText.toIntOrNull()
            if (x == null || y == null) {
                binding.resultText.setText("Hatalı İşlem")
                return
            }

            if (x > y) {
                binding.resultText.setText("Hatalı İşlem")
            } else {
                val result = (x..y).random()
                binding.resultText.setText(result.toString())
            }
        }
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
