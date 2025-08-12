package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deniz.randomtools.databinding.ActivityParaSayBinding

class ParaSayActivity : AppCompatActivity() {

    private var toplamTL: Int = 0
    private var toplamKrs: Int = 0
    private lateinit var binding: ActivityParaSayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParaSayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun hesaplaClick(view: View) {
        // EditText'ler boşsa 0 yaz
        if (binding.tl200Text.text.isEmpty()) binding.tl200Text.setText("0")
        if (binding.tl100Text.text.isEmpty()) binding.tl100Text.setText("0")
        if (binding.tl50Text.text.isEmpty()) binding.tl50Text.setText("0")
        if (binding.tl20Text.text.isEmpty()) binding.tl20Text.setText("0")
        if (binding.tl10Text.text.isEmpty()) binding.tl10Text.setText("0")
        if (binding.tl5Text.text.isEmpty()) binding.tl5Text.setText("0")
        if (binding.tl1Text.text.isEmpty()) binding.tl1Text.setText("0")
        if (binding.krs50Text.text.isEmpty()) binding.krs50Text.setText("0")
        if (binding.krs25Text.text.isEmpty()) binding.krs25Text.setText("0")
        if (binding.krs10Text.text.isEmpty()) binding.krs10Text.setText("0")
        if (binding.krs5Text.text.isEmpty()) binding.krs5Text.setText("0")
        if (binding.krs1Text.text.isEmpty()) binding.krs1Text.setText("0")

        toplamTL = (binding.tl200Text.text.toString().toInt() * 200
                + binding.tl100Text.text.toString().toInt() * 100
                + binding.tl50Text.text.toString().toInt() * 50
                + binding.tl20Text.text.toString().toInt() * 20
                + binding.tl10Text.text.toString().toInt() * 10
                + binding.tl5Text.text.toString().toInt() * 5
                + binding.tl1Text.text.toString().toInt() * 1)

        toplamKrs = (binding.krs50Text.text.toString().toInt() * 50
                + binding.krs25Text.text.toString().toInt() * 25
                + binding.krs10Text.text.toString().toInt() * 10
                + binding.krs5Text.text.toString().toInt() * 5
                + binding.krs1Text.text.toString().toInt() * 1)

        toplamTL += toplamKrs / 100
        toplamKrs %= 100

        binding.paraText.text = "$toplamTL TL $toplamKrs KRŞ"
    }

    fun temizleClick(view: View) {
        binding.tl200Text.setText("0")
        binding.tl100Text.setText("0")
        binding.tl50Text.setText("0")
        binding.tl20Text.setText("0")
        binding.tl10Text.setText("0")
        binding.tl5Text.setText("0")
        binding.tl1Text.setText("0")
        binding.krs50Text.setText("0")
        binding.krs25Text.setText("0")
        binding.krs10Text.setText("0")
        binding.krs5Text.setText("0")
        binding.krs1Text.setText("0")
        toplamTL = 0
        toplamKrs = 0
        binding.paraText.text = "$toplamTL TL $toplamKrs KRŞ"
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
