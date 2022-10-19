package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.deniz.randomtools.databinding.ActivityIbanKaydetBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rastgele_sifre.*

class IBANKaydetActivity : AppCompatActivity(){

    private lateinit var binding: ActivityIbanKaydetBinding
    private lateinit var db:IBANDatabase
    private lateinit var ibanDao : IBANDao
    private val compositeDisposable = CompositeDisposable()
    private var ibanFromMain : IBAN?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIbanKaydetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext,IBANDatabase::class.java,"IBAN").build()
        ibanDao = db.IBANDao()

        ibanFromMain = intent.getSerializableExtra("new") as? IBAN
        ibanFromMain?.let{
            binding.baslikText.setText(it.baslik)
            binding.bankaText.setText(it.banka)
            binding.ibanText.setText(it.iban)
            binding.aciklamaText.setText(it.aciklama)
            binding.saveButton.visibility = View.GONE
            binding.ibanText.isClickable= false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.baslikText.focusable = View.NOT_FOCUSABLE
                binding.bankaText.focusable = View.NOT_FOCUSABLE
                binding.ibanText.focusable = View.NOT_FOCUSABLE
                binding.aciklamaText.focusable = View.NOT_FOCUSABLE
                binding.ibanText.isClickable= true
                binding.ibanText.setOnClickListener{
                    val clipboard = getSystemService(ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("randomtools",binding.ibanText.text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"IBAN '${binding.baslikText.text}' kopyalandı.",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val intent = intent
        val info = intent.getStringExtra("new")
        if(info == "yes") {
            binding.deleteButton.visibility= View.GONE
        }
    }

    fun save(view : View){
        val iban= IBAN(binding.baslikText.text.toString(),binding.bankaText.text.toString()
            ,binding.ibanText.text.toString(),binding.aciklamaText.text.toString())
        compositeDisposable.add(
            ibanDao.insert(iban).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )

    }

    fun delete(view : View){
        ibanFromMain?.let{
            compositeDisposable.add(
                ibanDao.delete(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }
    }

    fun backClick(view : View){
        val intent = Intent(this,IBANActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun handleResponse(){
        val intent = Intent(this,IBANActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}