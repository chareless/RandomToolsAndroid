package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityKuraBinding

class KuraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKuraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun kuracekClick(view: View) {
        val katilanlarText = binding.kuraKatilanlarText.text.toString()
        val sayiText = binding.sayiText1.text.toString()

        if (katilanlarText.isNotEmpty() && sayiText.isNotEmpty()) {
            val katilimcilar = katilanlarText.split("\n")
            val kazanacakSayi = sayiText.toIntOrNull()
            if (kazanacakSayi != null) {
                var kazananlar = ""
                val mySet = mutableSetOf<Int>()
                val myNumberSet = katilimcilar.indices.toMutableSet()

                if (kazanacakSayi > 0 && katilimcilar.size > 1 && kazanacakSayi <= katilimcilar.size) {
                    repeat(kazanacakSayi) {
                        val randomIndex = (0 until myNumberSet.size).random()
                        val chosen = myNumberSet.elementAt(randomIndex)
                        mySet.add(chosen)
                        myNumberSet.remove(chosen)
                    }
                    for (i in mySet) {
                        kazananlar += katilimcilar[i] + "\n"
                    }
                    binding.kuraKazananlarText.setText(kazananlar);
                } else {
                    binding.kuraKazananlarText.setText("Hatalı İşlem");
                }
            } else {
                binding.kuraKazananlarText.setText("Hatalı İşlem");
            }
        } else {
            binding.kuraKazananlarText.setText("Hatalı İşlem");
        }
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
