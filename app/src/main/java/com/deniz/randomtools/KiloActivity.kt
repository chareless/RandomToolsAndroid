package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityKiloBinding

class KiloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKiloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKiloBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun hesaplaClick(view: View) {
        if ((binding.erkekCheck.isChecked || binding.kadinCheck.isChecked)
            && binding.boyText.text.isNotEmpty() && binding.kiloText.text.isNotEmpty()
        ) {
            val boy = binding.boyText.text.toString().toDouble()
            val inc = boy / 2.54
            val kilo = binding.kiloText.text.toString().toDouble()
            val bmi = kilo / (boy * boy) * 10000

            if (binding.erkekCheck.isChecked) {
                val ideal = 50 + (2.3 * (inc - 60))
                val myStr = String.format("%.2f", ideal)
                binding.idealText.text = "$myStr kg"
            }
            if (binding.kadinCheck.isChecked) {
                val ideal = 45.5 + (2.3 * (inc - 60))
                val myStr = String.format("%.2f", ideal)
                binding.idealText.text = "$myStr kg"
            }

            val myStr = String.format("%.2f", bmi)
            binding.bmiText.text = "$myStr"

            binding.sonucText.text = when {
                bmi < 18.5 -> "İdeal kilonuzun altındasınız."
                bmi < 25 -> "Kilonuz normal."
                bmi < 30 -> "İdeal kilonuzun üstündesiniz."
                else -> "Obez."
            }
        } else {
            binding.sonucText.text = "Lütfen tüm alanları doldurun."
        }
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
