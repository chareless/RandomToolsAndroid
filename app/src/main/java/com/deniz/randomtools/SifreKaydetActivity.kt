package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.deniz.randomtools.databinding.ActivitySifreKaydetBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
            binding.updateButton.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.sifreText.isClickable= true
                binding.sifreText.setOnClickListener{
                    val clipboard = getSystemService(ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("randomtools",binding.sifreText.text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"Şifre kopyalandı.",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val intent = intent
        val info = intent.getStringExtra("new")
        if(info == "yes") {
            binding.deleteButton.visibility=View.GONE
        }
    }

    fun update(view: View) {
        sifreFromMain?.let { sifreFromMain -> // Use a more descriptive name
            // Update the properties of ibanToUpdate from the EditText fields
            sifreFromMain.baslik = binding.baslikText.text.toString()
            sifreFromMain.kadi = binding.kadiText.text.toString()
            sifreFromMain.sifre = binding.sifreText.text.toString()
            sifreFromMain.aciklama = binding.aciklamaText.text.toString()

            compositeDisposable.add(
                sifreDao.update(sifreFromMain) // Pass the modified object
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { this.handleResponse() }, // onComplete
                        { throwable ->
                            Toast.makeText(this, "Error updating IBAN", Toast.LENGTH_SHORT).show()
                        }
                    )
            )
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