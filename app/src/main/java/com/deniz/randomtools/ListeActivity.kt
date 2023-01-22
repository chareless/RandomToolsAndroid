package com.deniz.randomtools

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.deniz.randomtools.databinding.ActivityListeBinding
import kotlinx.android.synthetic.main.activity_liste.*
import java.lang.Exception
import kotlin.collections.ArrayList

class ListeActivity : AppCompatActivity() {

    private lateinit var urunList : ArrayList<Listem>
    private lateinit var binding: ActivityListeBinding
    private lateinit var urunAdapter : UrunAdapter
    private lateinit var database : SQLiteDatabase
    private var toplam : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = this.openOrCreateDatabase("Uruns", MODE_PRIVATE,null)
        urunList = ArrayList<Listem>()
        urunAdapter= UrunAdapter(urunList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@ListeActivity)
        binding.recyclerView.adapter=urunAdapter

        try{
            val cursor = database.rawQuery("SELECT * FROM uruns",null)
            val isimIx = cursor.getColumnIndex("ad")
            val fiyatIx = cursor.getColumnIndex("fiyat")
            val adetIx = cursor.getColumnIndex("adet")
            toplam = 0.0
            toplamText.text="Toplam Fiyat ${toplam.toString()}"
            while (cursor.moveToNext()){
                val isim = cursor.getString(isimIx)
                val fiyat = cursor.getString(fiyatIx)
                val adet = cursor.getString(adetIx)

                val urun = Listem(isim,fiyat,adet)
                urunList.add(urun)
                toplam += fiyat.toDouble()*adet.toDouble()
                toplamText.text="Toplam Fiyat ${toplam.toString()}"
            }
            urunAdapter.notifyDataSetChanged()
            cursor.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun refresh(){
        try{
            val cursor = database.rawQuery("SELECT * FROM uruns",null)
            val isimIx = cursor.getColumnIndex("ad")
            val fiyatIx = cursor.getColumnIndex("fiyat")
            val adetIx = cursor.getColumnIndex("adet")
            toplam = 0.0
            toplamText.text="Toplam Fiyat ${toplam.toString()}"
            urunList.clear()
            while (cursor.moveToNext()){
                val isim = cursor.getString(isimIx)
                val fiyat = cursor.getString(fiyatIx)
                val adet = cursor.getString(adetIx)

                val urun = Listem(isim,fiyat,adet)
                urunList.add(urun)
                toplam += fiyat.toDouble()*adet.toDouble()
                toplamText.text="Toplam Fiyat ${toplam.toString()}"
            }
            urunAdapter.notifyDataSetChanged()
            urunadText.text.clear()
            urunfiyatText.text.clear()
            urunadetText.text.clear()
            cursor.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun ekleClick(view : View){
        if(urunadText.text.isNotEmpty() && urunfiyatText.text.isNotEmpty() && urunadetText.text.isNotEmpty()){
            val urunIsim = urunadText.text.toString()
            val urunFiyat = urunfiyatText.text.toString()
            val urunAdet = urunadetText.text.toString()

            try{
                database.execSQL("CREATE TABLE IF NOT EXISTS uruns (id INTEGER PRIMARY KEY,ad VARCHAR,fiyat VARCHAR,adet VARCHAR)")
                val sqlString = "INSERT INTO uruns (ad,fiyat,adet) VALUES (?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,urunIsim)
                statement.bindString(2,urunFiyat)
                statement.bindString(3,urunAdet)
                statement.execute()
                urunAdapter.notifyDataSetChanged()
                refresh()

            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun temizleClick(view : View){
        database.execSQL("DELETE FROM uruns");
        refresh()
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}