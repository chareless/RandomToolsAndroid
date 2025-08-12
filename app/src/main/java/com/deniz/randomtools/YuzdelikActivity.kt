package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityYuzdelikBinding

class YuzdelikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYuzdelikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYuzdelikBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun sonuc1Hesapla() {
        val aText = binding.sayiText1.text.toString()
        val bText = binding.sayiText2.text.toString()
        if (aText.isNotEmpty() && bText.isNotEmpty()) {
            val a = aText.toFloatOrNull()
            val b = bText.toFloatOrNull()
            if (a == null || b == null || a == 0f || b == 0f) {
                binding.sonuc1Text.text = "Geçersiz İşlem"
            } else {
                val c = a / 100 * b
                binding.sonuc1Text.text = String.format("%.4f", c)
            }
        }
    }

    private fun sonuc2Hesapla() {
        val aText = binding.sayiText3.text.toString()
        val bText = binding.sayiText4.text.toString()
        if (aText.isNotEmpty() && bText.isNotEmpty()) {
            val a = aText.toFloatOrNull()
            val b = bText.toFloatOrNull()
            if (a == null || b == null || a == 0f || b == 0f) {
                binding.sonuc2Text.text = "Geçersiz İşlem"
            } else {
                val c = 100 * b / a
                binding.sonuc2Text.text = String.format("%.4f", c)
            }
        }
    }

    private fun sonuc3Hesapla() {
        val aText = binding.sayiText5.text.toString()
        val bText = binding.sayiText6.text.toString()
        if (aText.isNotEmpty() && bText.isNotEmpty()) {
            val a = aText.toFloatOrNull()
            val b = bText.toFloatOrNull()
            if (a == null || b == null || a == 0f || b == 0f) {
                binding.sonuc3Text.text = "Geçersiz İşlem"
            } else {
                val c = 100 * a / b
                binding.sonuc3Text.text = String.format("%.4f", c)
            }
        }
    }

    private fun sonuc4Hesapla() {
        val aText = binding.sayiText7.text.toString()
        val bText = binding.sayiText8.text.toString()
        if (aText.isNotEmpty() && bText.isNotEmpty()) {
            val a = aText.toFloatOrNull()
            val b = bText.toFloatOrNull()
            if (a == null || b == null || a == 0f || b == 0f) {
                binding.sonuc4Text.text = "Geçersiz İşlem"
            } else {
                val c = a + a * b / 100
                binding.sonuc4Text.text = String.format("%.4f", c)
            }
        }
    }

    private fun sonuc5Hesapla() {
        val aText = binding.sayiText9.text.toString()
        val bText = binding.sayiText10.text.toString()
        if (aText.isNotEmpty() && bText.isNotEmpty()) {
            val a = aText.toFloatOrNull()
            val b = bText.toFloatOrNull()
            if (a == null || b == null || a == 0f || b == 0f) {
                binding.sonuc5Text.text = "Geçersiz İşlem"
            } else {
                val c = a - a * b / 100
                binding.sonuc5Text.text = String.format("%.4f", c)
            }
        }
    }

    fun yuzdeClick(view: View) {
        sonuc1Hesapla()
        sonuc2Hesapla()
        sonuc3Hesapla()
        sonuc4Hesapla()
        sonuc5Hesapla()
    }

    fun temizleClick(view: View) {
        binding.sayiText1.setText("")
        binding.sayiText2.setText("")
        binding.sayiText3.setText("")
        binding.sayiText4.setText("")
        binding.sayiText5.setText("")
        binding.sayiText6.setText("")
        binding.sayiText7.setText("")
        binding.sayiText8.setText("")
        binding.sayiText9.setText("")
        binding.sayiText10.setText("")

        binding.sonuc1Text.text = "Sonuç"
        binding.sonuc2Text.text = "Sonuç"
        binding.sonuc3Text.text = "Sonuç"
        binding.sonuc4Text.text = "Sonuç"
        binding.sonuc5Text.text = "Sonuç"
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
