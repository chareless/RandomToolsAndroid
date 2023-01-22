package com.deniz.randomtools

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_zaman_sayar.*
import java.lang.Exception

class ZamanSayarActivity : AppCompatActivity(), Runnable {

    var Krunnable: Runnable = Runnable {  }
    var Crunnable: Runnable = Runnable {  }
    var Khandler: Handler = Handler()
    var Chandler: Handler = Handler()
    var kronoSaat : Int = 0
    var kronoDakika : Int = 0
    var kronoSaniye : Int = 0
    var kFinish : Boolean = false
    var countSaat : Int = 0
    var countDakika : Int = 0
    var countSaniye : Int = 0
    var cFinish : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaman_sayar)
        kronodurButton.visibility = View.INVISIBLE
        countdurButton.visibility = View.INVISIBLE
        countbaslaButton.visibility = View.INVISIBLE
    }

    fun kronoFormatCheck(){
        var part1:String
        var part2:String
        var part3:String
        if(kronoSaat<10){
            part1 = "0$kronoSaat:"
        }
        else{
            part1 = "$kronoSaat:"
        }

        if(kronoDakika<10){
            part2= "0$kronoDakika:"
        }
        else{
            part2="$kronoDakika:"
        }

        if(kronoSaniye <10){
            part3 = "0$kronoSaniye"
        }
        else{
            part3= "$kronoSaniye"
        }
        kronoTimer.text = part1+part2+part3
    }

    fun kronobaslaClick(view : View){

        Krunnable = object : Runnable {
            override fun run() {
                kronoFormatCheck()
                if(!kFinish){
                    kronoSaniye++
                    if(kronoSaniye == 60){
                        kronoSaniye=0
                        kronoDakika++
                    }
                    if(kronoDakika == 60) {
                        kronoDakika=0
                        kronoSaat++
                    }
                    if(kronoSaat ==23 && kronoDakika == 59 && kronoSaniye == 59){
                        kFinish=true
                    }
                    Khandler.postDelayed(this, 1000)
                    kronobaslaButton.visibility = View.INVISIBLE
                    kronodurButton.visibility = View.VISIBLE
                }
                else{
                    Khandler.removeCallbacks(Krunnable)
                    kronobaslaButton.visibility = View.VISIBLE
                    kronodurButton.visibility = View.INVISIBLE
                }
            }
        }
        Khandler.post(Krunnable)
    }

    fun kronodurClick(view : View){
        Khandler.removeCallbacks(Krunnable)
        kronobaslaButton.visibility = View.VISIBLE
        kronodurButton.visibility = View.INVISIBLE
    }

    fun kronosifirlaClick(view : View){
        Khandler.removeCallbacks(Krunnable)
        kronobaslaButton.visibility = View.VISIBLE
        kronodurButton.visibility = View.INVISIBLE
        kronoSaniye=0
        kronoDakika=0
        kronoSaat=0
        kFinish=false
        kronoFormatCheck()
    }

    fun countFormatCheck(){
        var part1:String
        var part2:String
        var part3:String
        if(countSaat<10){
            part1 = "0$countSaat:"
        }
        else{
            part1 = "$countSaat:"
        }

        if(countDakika<10){
            part2= "0$countDakika:"
        }
        else{
            part2="$countDakika:"
        }

        if(countSaniye <10){
            part3 = "0$countSaniye"
        }
        else{
            part3= "$countSaniye"
        }
        countTimer.text = part1+part2+part3
    }

    fun countayarlaClick(view : View){
        var editing = countEditText.text.toString()
        try{
            var times = editing.split(":")
            countSaat = times[0].toInt()
            countDakika = times[1].toInt()
            countSaniye = times[2].toInt()
            cFinish=false
            countbaslaButton.visibility = View.VISIBLE
            countdurButton.visibility = View.INVISIBLE
        }catch(e : Exception) {
            Toast.makeText(this,"Lütfen geçerli formatta değer giriniz.",Toast.LENGTH_SHORT).show()
            countSaat = 0
            countDakika = 0
            countSaniye = 0
            cFinish=true
            countFormatCheck()
            countbaslaButton.visibility = View.INVISIBLE
            countdurButton.visibility = View.INVISIBLE
        }

        if(countSaat>23 || countDakika >59 || countSaniye >59 || countSaat <0 || countDakika <0 || countSaniye <0){
            Toast.makeText(this,"Lütfen geçerli formatta değer giriniz.",Toast.LENGTH_SHORT).show()
            countSaat = 0
            countDakika = 0
            countSaniye = 0
            cFinish=true
            countbaslaButton.visibility = View.INVISIBLE
            countdurButton.visibility = View.INVISIBLE
        }

        countEditText.setText("")
        Chandler.removeCallbacks(Crunnable)
        countFormatCheck()
    }

    fun countbaslaClick(view : View){
        Crunnable = object : Runnable {
            override fun run() {
                countFormatCheck()
                if(!cFinish){
                    countSaniye--
                    if(countSaniye<0){
                        countSaniye = 59
                        countDakika --
                    }
                    if(countDakika <0){
                        countDakika = 59
                        countSaat --
                    }
                    if(countSaat == 0 && countDakika == 0 && countSaniye == 0){
                        cFinish=true
                    }
                    Chandler.postDelayed(this, 1000)
                    countbaslaButton.visibility = View.INVISIBLE
                    countdurButton.visibility = View.VISIBLE
                }
                else{
                    Chandler.removeCallbacks(Crunnable)
                    countbaslaButton.visibility = View.VISIBLE
                    countdurButton.visibility = View.INVISIBLE
                }
            }
        }
        Chandler.post(Crunnable)
    }

    fun countdurClick(view : View){
        Chandler.removeCallbacks(Crunnable)
        countbaslaButton.visibility = View.VISIBLE
        countdurButton.visibility = View.INVISIBLE
    }

    fun countsifirlaClick(view : View){
        Chandler.removeCallbacks(Crunnable)
        countbaslaButton.visibility = View.INVISIBLE
        countdurButton.visibility = View.INVISIBLE
        countSaniye=0
        countDakika=0
        countSaat=0
        cFinish=false
        countFormatCheck()
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}