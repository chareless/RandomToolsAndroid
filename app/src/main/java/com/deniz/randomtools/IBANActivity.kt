package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.deniz.randomtools.databinding.ActivityIbanlarBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rastgele_sifre.*

class IBANActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIbanlarBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIbanlarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = Room.databaseBuilder(applicationContext,IBANDatabase::class.java,"IBAN").build()
        var ibanDao = db.IBANDao()

        compositeDisposable.add(
            ibanDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(ibanList : List<IBAN>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = IBANAdapter(ibanList)
        binding.recyclerView.adapter=adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_iban){
            val intent= Intent(this, IBANKaydetActivity::class.java)
            intent.putExtra("new","yes")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.iban_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun backClick(view : View){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}