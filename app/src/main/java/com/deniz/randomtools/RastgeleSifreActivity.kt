package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.deniz.randomtools.databinding.ActivityRastgeleSifreBinding
import kotlin.random.Random

class RastgeleSifreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRastgeleSifreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRastgeleSifreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun olusturClick(view: View) {
        val inputText = binding.sayiText1.text.toString()
        val sifreSayi = inputText.toIntOrNull()

        if (sifreSayi == null) {
            binding.sifreTextView.text = "Karakter sayısını girin."
            return
        }

        if (sifreSayi !in 1..16) {
            binding.sifreTextView.text = "Karakter aralığı (1..16) olmalı."
            return
        }

        val kucuk = binding.kucukCheck.isChecked
        val buyuk = binding.buyukCheck.isChecked
        val ozel = binding.ozelCheck.isChecked
        val sayi = binding.sayiCheck.isChecked

        var totalChars = ""
        if (kucuk) totalChars += "abcdefghijklmnopqrstuvwxyz"
        if (buyuk) totalChars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        if (sayi) totalChars += "0123456789"
        if (ozel) totalChars += "@#=+!£$%&?"

        if (totalChars.isEmpty()) {
            binding.sifreTextView.text = "En az 1 karakter türü seçin."
            return
        }

        val pass = buildString {
            repeat(sifreSayi) {
                append(totalChars[Random.nextInt(totalChars.length)])
            }
        }

        binding.sifreTextView.text = pass
    }

    fun sifrekopyalaClick(view: View) {
        val clipboard = getSystemService(ClipboardManager::class.java)
        val clip = ClipData.newPlainText("randomtools", binding.sifreTextView.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Şifre kopyalandı.", Toast.LENGTH_SHORT).show()
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
