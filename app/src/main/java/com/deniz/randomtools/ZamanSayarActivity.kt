package com.deniz.randomtools

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deniz.randomtools.databinding.ActivityZamanSayarBinding
import java.lang.Exception

class ZamanSayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZamanSayarBinding

    private var kronoSaat: Int = 0
    private var kronoDakika: Int = 0
    private var kronoSaniye: Int = 0
    private var kFinish: Boolean = false

    private var countSaat: Int = 0
    private var countDakika: Int = 0
    private var countSaniye: Int = 0
    private var cFinish: Boolean = false

    private val Khandler: Handler = Handler()
    private val Chandler: Handler = Handler()

    private lateinit var Krunnable: Runnable
    private lateinit var Crunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZamanSayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kronodurButton.visibility = View.INVISIBLE
        binding.countdurButton.visibility = View.INVISIBLE
        binding.countbaslaButton.visibility = View.INVISIBLE
    }

    private fun kronoFormatCheck() {
        val part1 = if (kronoSaat < 10) "0$kronoSaat:" else "$kronoSaat:"
        val part2 = if (kronoDakika < 10) "0$kronoDakika:" else "$kronoDakika:"
        val part3 = if (kronoSaniye < 10) "0$kronoSaniye" else "$kronoSaniye"
        binding.kronoTimer.text = part1 + part2 + part3
    }

    fun kronobaslaClick(view: View) {
        Krunnable = object : Runnable {
            override fun run() {
                kronoFormatCheck()
                if (!kFinish) {
                    kronoSaniye++
                    if (kronoSaniye == 60) {
                        kronoSaniye = 0
                        kronoDakika++
                    }
                    if (kronoDakika == 60) {
                        kronoDakika = 0
                        kronoSaat++
                    }
                    if (kronoSaat == 23 && kronoDakika == 59 && kronoSaniye == 59) {
                        kFinish = true
                    }
                    Khandler.postDelayed(this, 1000)
                    binding.kronobaslaButton.visibility = View.INVISIBLE
                    binding.kronodurButton.visibility = View.VISIBLE
                } else {
                    Khandler.removeCallbacks(Krunnable)
                    binding.kronobaslaButton.visibility = View.VISIBLE
                    binding.kronodurButton.visibility = View.INVISIBLE
                }
            }
        }
        Khandler.post(Krunnable)
    }

    fun kronodurClick(view: View) {
        Khandler.removeCallbacks(Krunnable)
        binding.kronobaslaButton.visibility = View.VISIBLE
        binding.kronodurButton.visibility = View.INVISIBLE
    }

    fun kronosifirlaClick(view: View) {
        Khandler.removeCallbacks(Krunnable)
        binding.kronobaslaButton.visibility = View.VISIBLE
        binding.kronodurButton.visibility = View.INVISIBLE
        kronoSaat = 0
        kronoDakika = 0
        kronoSaniye = 0
        kFinish = false
        kronoFormatCheck()
    }

    private fun countFormatCheck() {
        val part1 = if (countSaat < 10) "0$countSaat:" else "$countSaat:"
        val part2 = if (countDakika < 10) "0$countDakika:" else "$countDakika:"
        val part3 = if (countSaniye < 10) "0$countSaniye" else "$countSaniye"
        binding.countTimer.text = part1 + part2 + part3
    }

    fun countayarlaClick(view: View) {
        val editing = binding.countEditText.text.toString()
        try {
            val times = editing.split(":")
            countSaat = times[0].toInt()
            countDakika = times[1].toInt()
            countSaniye = times[2].toInt()
            cFinish = false
            binding.countbaslaButton.visibility = View.VISIBLE
            binding.countdurButton.visibility = View.INVISIBLE
        } catch (e: Exception) {
            Toast.makeText(this, "Lütfen geçerli formatta değer giriniz.", Toast.LENGTH_SHORT).show()
            countSaat = 0
            countDakika = 0
            countSaniye = 0
            cFinish = true
            countFormatCheck()
            binding.countbaslaButton.visibility = View.INVISIBLE
            binding.countdurButton.visibility = View.INVISIBLE
        }

        if (countSaat !in 0..23 || countDakika !in 0..59 || countSaniye !in 0..59) {
            Toast.makeText(this, "Lütfen geçerli formatta değer giriniz.", Toast.LENGTH_SHORT).show()
            countSaat = 0
            countDakika = 0
            countSaniye = 0
            cFinish = true
            binding.countbaslaButton.visibility = View.INVISIBLE
            binding.countdurButton.visibility = View.INVISIBLE
        }

        binding.countEditText.setText("")
        if (::Crunnable.isInitialized) {
            Chandler.removeCallbacks(Crunnable)
        }
        countFormatCheck()
    }

    fun countbaslaClick(view: View) {
        Crunnable = object : Runnable {
            override fun run() {
                countFormatCheck()
                if (!cFinish) {
                    countSaniye--
                    if (countSaniye < 0) {
                        countSaniye = 59
                        countDakika--
                    }
                    if (countDakika < 0) {
                        countDakika = 59
                        countSaat--
                    }
                    if (countSaat == 0 && countDakika == 0 && countSaniye == 0) {
                        cFinish = true
                    }
                    Chandler.postDelayed(this, 1000)
                    binding.countbaslaButton.visibility = View.INVISIBLE
                    binding.countdurButton.visibility = View.VISIBLE
                } else {
                    Chandler.removeCallbacks(Crunnable)
                    binding.countbaslaButton.visibility = View.VISIBLE
                    binding.countdurButton.visibility = View.INVISIBLE
                }
            }
        }
        Chandler.post(Crunnable)
    }

    fun countdurClick(view: View) {
        Chandler.removeCallbacks(Crunnable)
        binding.countbaslaButton.visibility = View.VISIBLE
        binding.countdurButton.visibility = View.INVISIBLE
    }

    fun countsifirlaClick(view: View) {
        Chandler.removeCallbacks(Crunnable)
        binding.countbaslaButton.visibility = View.INVISIBLE
        binding.countdurButton.visibility = View.INVISIBLE
        countSaat = 0
        countDakika = 0
        countSaniye = 0
        cFinish = false
        countFormatCheck()
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
