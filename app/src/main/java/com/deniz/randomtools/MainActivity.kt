package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun yazituraClick(view : View){
        val alert = AlertDialog.Builder(this)
        val result = (0..1).random()
        val text:String
        if(result == 0){
            text="YAZI"
        }
        else{
            text="TURA"
        }
        alert.setTitle("YAZI TURA")
        alert.setMessage(text)
        alert.setPositiveButton("OK"){dialog,which -> }
        alert.show()
    }

    fun tkmClick(view : View){
        val alert = AlertDialog.Builder(this)
        val result = (0..2).random()
        val text:String
        if(result == 0){
            text="TAŞ"
        }
        else if(result == 1){
            text="KAĞIT"
        }
        else{
            text="MAKAS"
        }
        alert.setTitle("TAŞ KAĞIT MAKAS")
        alert.setMessage(text)
        alert.setPositiveButton("OK"){dialog,which -> }
        alert.show()
    }

    fun zaratClick(view : View){
        val alert = AlertDialog.Builder(this)
        var text:String? = null
        alert.setTitle("ZAR AT")
        alert.setMessage("Kaç adet zar atılacak?")
        alert.setPositiveButton("TEK"){dialog,which ->
            val tekZar = (1..6).random()
            text = tekZar.toString()
            val alertNew = AlertDialog.Builder(this)
            alertNew.setTitle("ZAR AT")
            alertNew.setMessage(text)
            alertNew.setPositiveButton("OK"){dialog,which -> }
            alertNew.show()
        }
        alert.setNegativeButton("ÇİFT"){dialog,which ->
            val ilkZar = (1..6).random()
            val ikinciZar = (1..6).random()
            text = "$ilkZar ve $ikinciZar"
            val alertNew = AlertDialog.Builder(this)
            alertNew.setTitle("ZAR AT")
            alertNew.setMessage(text)
            alertNew.setPositiveButton("OK"){dialog,which -> }
            alertNew.show()
        }
        alert.show()
    }

    fun kuracekClick(view : View){
        val intent = Intent(this,KuraActivity::class.java)
        startActivity(intent)
    }

    fun rastgelesecClick(view:View){
        val intent = Intent(this,RastgeleNumaraActivity::class.java)
        startActivity(intent)
    }

    fun rastgelesifreClick(view:View){
        val intent = Intent(this,RastgeleSifreActivity::class.java)
        startActivity(intent)
    }

    fun notClick(view : View){
        val intent = Intent(this,NotActivity::class.java)
        startActivity(intent)
    }

    fun hesapmakineClick(view : View){
        val intent = Intent(this,HesapMakinesiActivity::class.java)
        startActivity(intent)
    }

    fun yuzdelikClick(view : View){
        val intent = Intent(this,YuzdelikActivity::class.java)
        startActivity(intent)
    }

    fun listeClick(view : View){
        val intent = Intent(this,ListeActivity::class.java)
        startActivity(intent)
    }

    fun parasayarClick(view : View){
        val intent = Intent(this,ParaSayActivity::class.java)
        startActivity(intent)
    }

    fun kiloClick(view : View){
        val intent = Intent(this,KiloActivity::class.java)
        startActivity(intent)
    }

    fun zamansayarClick(view : View){
        val intent = Intent(this,ZamanSayarActivity::class.java)
        startActivity(intent)
    }

    fun favoriyerClick(view : View){
        val intent = Intent(this,FavoriYerActivity::class.java)
        startActivity(intent)
    }
}