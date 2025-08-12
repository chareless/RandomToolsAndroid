package com.deniz.randomtools

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.deniz.randomtools.databinding.ActivityListeBinding
import java.lang.Exception

class ListeActivity : AppCompatActivity() {

    private lateinit var urunList: ArrayList<Listem>
    private lateinit var binding: ActivityListeBinding
    private lateinit var urunAdapter: UrunAdapter
    private lateinit var database: SQLiteDatabase
    private var toplam: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = this.openOrCreateDatabase("Uruns", MODE_PRIVATE, null)
        urunList = ArrayList()
        urunAdapter = UrunAdapter(urunList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = urunAdapter

        try {
            val cursor = database.rawQuery("SELECT * FROM uruns", null)
            val isimIx = cursor.getColumnIndex("ad")
            val fiyatIx = cursor.getColumnIndex("fiyat")
            val adetIx = cursor.getColumnIndex("adet")
            toplam = 0.0
            binding.toplamText.text = "Toplam Fiyat $toplam"
            while (cursor.moveToNext()) {
                val isim = cursor.getString(isimIx)
                val fiyat = cursor.getString(fiyatIx)
                val adet = cursor.getString(adetIx)

                val urun = Listem(isim, fiyat, adet)
                urunList.add(urun)
                toplam += fiyat.toDouble() * adet.toDouble()
                binding.toplamText.text = "Toplam Fiyat $toplam"
            }
            urunAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refresh() {
        try {
            val cursor = database.rawQuery("SELECT * FROM uruns", null)
            val isimIx = cursor.getColumnIndex("ad")
            val fiyatIx = cursor.getColumnIndex("fiyat")
            val adetIx = cursor.getColumnIndex("adet")
            toplam = 0.0
            binding.toplamText.text = "Toplam Fiyat $toplam"
            urunList.clear()
            while (cursor.moveToNext()) {
                val isim = cursor.getString(isimIx)
                val fiyat = cursor.getString(fiyatIx)
                val adet = cursor.getString(adetIx)

                val urun = Listem(isim, fiyat, adet)
                urunList.add(urun)
                toplam += fiyat.toDouble() * adet.toDouble()
                binding.toplamText.text = "Toplam Fiyat $toplam"
            }
            urunAdapter.notifyDataSetChanged()
            binding.urunadText.text.clear()
            binding.urunfiyatText.text.clear()
            binding.urunadetText.text.clear()
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ekleClick(view: View) {
        if (binding.urunadText.text.isNotEmpty() &&
            binding.urunfiyatText.text.isNotEmpty() &&
            binding.urunadetText.text.isNotEmpty()
        ) {
            val urunIsim = binding.urunadText.text.toString()
            val urunFiyat = binding.urunfiyatText.text.toString()
            val urunAdet = binding.urunadetText.text.toString()

            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS uruns (id INTEGER PRIMARY KEY, ad VARCHAR, fiyat VARCHAR, adet VARCHAR)")
                val sqlString = "INSERT INTO uruns (ad, fiyat, adet) VALUES (?, ?, ?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, urunIsim)
                statement.bindString(2, urunFiyat)
                statement.bindString(3, urunAdet)
                statement.execute()
                refresh()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun temizleClick(view: View) {
        database.execSQL("DELETE FROM uruns")
        refresh()
    }

    fun backClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
