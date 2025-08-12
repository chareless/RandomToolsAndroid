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
import com.deniz.randomtools.databinding.ActivityLinkKaydetBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LinkKaydetActivity : AppCompatActivity(){

    private lateinit var binding: ActivityLinkKaydetBinding
    private lateinit var db:LinkDatabase
    private lateinit var linkDao : LinkDao
    private val compositeDisposable = CompositeDisposable()
    private var linkFromMain : Link?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkKaydetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext,LinkDatabase::class.java,"Link").build()
        linkDao = db.LinkDao()

        linkFromMain = intent.getSerializableExtra("new") as? Link
        linkFromMain?.let{
            binding.baslikText.setText(it.baslik)
            binding.linkText.setText(it.link)
            binding.aciklamaText.setText(it.aciklama)
            binding.saveButton.visibility = View.GONE
            binding.updateButton.visibility = View.VISIBLE
            binding.linkText.isClickable= false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.linkText.isClickable= true
                binding.linkText.setOnClickListener{
                    val clipboard = getSystemService(ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("randomtools",binding.linkText.text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"'${binding.baslikText.text}' linki kopyalandı.",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val intent = intent
        val info = intent.getStringExtra("new")
        if(info == "yes") {
            binding.deleteButton.visibility= View.GONE
        }
    }

    fun update(view: View) {
        linkFromMain?.let { linkToUpdate -> // Use a more descriptive name
            // Update the properties of ibanToUpdate from the EditText fields
            linkToUpdate.baslik = binding.baslikText.text.toString()
            linkToUpdate.link = binding.linkText.text.toString()
            linkToUpdate.aciklama = binding.aciklamaText.text.toString()

            compositeDisposable.add(
                linkDao.update(linkToUpdate) // Pass the modified object
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { this.handleResponse() }, // onComplete
                        { throwable ->
                            Toast.makeText(this, "Error updating Link", Toast.LENGTH_SHORT).show()
                        }
                    )
            )
        }
    }

    fun save(view : View){
        val link= Link(binding.baslikText.text.toString(),binding.linkText.text.toString()
            ,binding.aciklamaText.text.toString())
        compositeDisposable.add(
            linkDao.insert(link).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )

    }

    fun delete(view : View){
        linkFromMain?.let{
            compositeDisposable.add(
                linkDao.delete(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }
    }

    fun backClick(view : View){
        val intent = Intent(this,LinklerimActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun handleResponse(){
        val intent = Intent(this,LinklerimActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}