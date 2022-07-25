package com.deniz.randomtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.deniz.randomtools.databinding.ActivityFavoriYerBinding
import com.deniz.randomtools.databinding.ActivitySifrelerBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SifrelerActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySifrelerBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySifrelerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = Room.databaseBuilder(applicationContext,SifreDatabase::class.java,"Sifreler").build()
        var sifreDao = db.sifreDao()

        compositeDisposable.add(
            sifreDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(sifreList : List<Sifre>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SifreAdapter(sifreList)
        binding.recyclerView.adapter=adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_sifre){
            val intent= Intent(this, SifreKaydetActivity::class.java)
            intent.putExtra("new","yes")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.sifre_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}