package com.deniz.randomtools

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.room.Room
import com.deniz.randomtools.databinding.ActivityMapsBinding
import com.deniz.randomtools.databinding.ActivitySifreKaydetBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SifreKaydetActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySifreKaydetBinding
    private lateinit var db:SifreDatabase
    private lateinit var sifreDao : SifreDao
    private val compositeDisposable = CompositeDisposable()
    private var sifreFromMain : Sifre?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySifreKaydetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext,SifreDatabase::class.java,"Sifreler").build()
        sifreDao = db.sifreDao()

        sifreFromMain = intent.getSerializableExtra("new") as? Sifre
        sifreFromMain?.let{
            binding.baslikText.setText(it.baslik)
            binding.kadiText.setText(it.kadi)
            binding.sifreText.setText(it.sifre)
            binding.aciklamaText.setText(it.aciklama)
            binding.saveButton.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.baslikText.focusable = View.NOT_FOCUSABLE
                binding.kadiText.focusable = View.NOT_FOCUSABLE
                binding.sifreText.focusable = View.NOT_FOCUSABLE
                binding.aciklamaText.focusable = View.NOT_FOCUSABLE
            }
        }

        val intent = intent
        val info = intent.getStringExtra("new")
        if(info == "yes") {
            binding.deleteButton.visibility=View.GONE
        }
    }



    fun save(view : View){
                val sifre= Sifre(binding.baslikText.text.toString(),binding.kadiText.text.toString()
                    ,binding.sifreText.text.toString(),binding.aciklamaText.text.toString())
                compositeDisposable.add(
                    sifreDao.insert(sifre).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse)
                )

    }

    fun delete(view : View){
        sifreFromMain?.let{
            compositeDisposable.add(
                sifreDao.delete(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }
    }

    fun backClick(view : View){
        val intent = Intent(this,SifrelerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun handleResponse(){
        val intent = Intent(this,SifrelerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}