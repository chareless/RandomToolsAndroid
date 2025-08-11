package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityHesapMakinesiBinding

class HesapMakinesiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHesapMakinesiBinding
    private var result: Float = 0.0f
    private var kontrol: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHesapMakinesiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun toplaClick(view: View) {
        if (binding.number1Text.text.isNotEmpty() && binding.number2Text.text.isNotEmpty()) {
            kontrol = false
            if (binding.number1Text.text.toString() != "-" && binding.number2Text.text.toString() != "-" &&
                binding.number1Text.text.toString() != "." && binding.number2Text.text.toString() != "."
            ) {
                kontrol = true
            }
            if (kontrol) {
                val number1 = binding.number1Text.text.toString().toFloat()
                val number2 = binding.number2Text.text.toString().toFloat()
                result = number1 + number2
                val myStr = String.format("%.4f", result)
                binding.resultText.text = myStr
            }
        }
    }

    fun eksiClick(view: View) {
        if (binding.number1Text.text.isNotEmpty() && binding.number2Text.text.isNotEmpty()) {
            kontrol = false
            if (binding.number1Text.text.toString() != "-" && binding.number2Text.text.toString() != "-" &&
                binding.number1Text.text.toString() != "." && binding.number2Text.text.toString() != "."
            ) {
                kontrol = true
            }
            if (kontrol) {
                val number1 = binding.number1Text.text.toString().toFloat()
                val number2 = binding.number2Text.text.toString().toFloat()
                result = number1 - number2
                val myStr = String.format("%.4f", result)
                binding.resultText.text = myStr
            }
        }
    }

    fun carpiClick(view: View) {
        if (binding.number1Text.text.isNotEmpty() && binding.number2Text.text.isNotEmpty()) {
            kontrol = false
            if (binding.number1Text.text.toString() != "-" && binding.number2Text.text.toString() != "-" &&
                binding.number1Text.text.toString() != "." && binding.number2Text.text.toString() != "."
            ) {
                kontrol = true
            }
            if (kontrol) {
                val number1 = binding.number1Text.text.toString().toFloat()
                val number2 = binding.number2Text.text.toString().toFloat()
                result = number1 * number2
                val myStr = String.format("%.4f", result)
                binding.resultText.text = myStr
            }
        }
    }

    fun bolClick(view: View) {
        if (binding.number1Text.text.isNotEmpty() && binding.number2Text.text.isNotEmpty()) {
            kontrol = false
            if (binding.number1Text.text.toString() != "-" && binding.number2Text.text.toString() != "-" &&
                binding.number1Text.text.toString() != "." && binding.number2Text.text.toString() != "."
            ) {
                kontrol = true
            }
            if (kontrol) {
                val number1 = binding.number1Text.text.toString().toFloat()
                val number2 = binding.number2Text.text.toString().toFloat()
                if (number2 != 0f) {
                    result = number1 / number2
                    val myStr = String.format("%.4f", result)
                    binding.resultText.text = myStr
                } else {
                    binding.resultText.text = "Geçersiz İşlem"
                }
            }
        }
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
