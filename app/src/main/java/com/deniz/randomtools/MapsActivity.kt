package com.deniz.randomtools

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.deniz.randomtools.databinding.ActivityMapsBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager : LocationManager
    private lateinit var locationListener : LocationListener
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var trackBoolean : Boolean? = null
    private var selectedLatitude : Double?=null
    private var selectedLongitude : Double?=null
    private lateinit var db:PlaceDatabase
    private lateinit var placeDao : PlaceDao
    private val compositeDisposable = CompositeDisposable()
    private var placeFromMain : Place?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        registerLauncher()
        sharedPreferences = this.getSharedPreferences("com.deniz.kotlinmaps", MODE_PRIVATE)
        trackBoolean=false
        selectedLatitude=0.0
        selectedLongitude=0.0
        db = Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places").build()
        placeDao = db.placeDao()
        binding.saveButton.isEnabled=false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        val intent = intent
        val info = intent.getStringExtra("chosen")
        if(info == "new") {
            binding.saveButton.visibility= View.VISIBLE
            binding.deleteButton.visibility= View.GONE
            locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if(trackBoolean == false){
                        val userLocation = LatLng(location.latitude,location.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                        trackBoolean=true
                    }
                }
            }

            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Snackbar.make(binding.root,"Konum izni gerekli.", Snackbar.LENGTH_INDEFINITE).setAction("İZİN VER"){
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }.show()
                }
                else{
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0f,locationListener)
                val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(lastLocation!=null){
                    val lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))
                }
                mMap.isMyLocationEnabled = true
            }
        }
        else{
            mMap.clear()
            placeFromMain = intent.getSerializableExtra("chosen") as? Place
            placeFromMain?.let{
                val latlng = LatLng(it.latitude,it.longitude)
                mMap.addMarker(MarkerOptions().position(latlng).title(it.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,15f))
                binding.placeText.setText(it.name)
                binding.infoText.setText(it.info)
                binding.saveButton.visibility = View.GONE
                binding.updateButton.visibility = View.VISIBLE
                binding.deleteButton.visibility= View.VISIBLE
            }
        }
    }

    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,30000,20f,locationListener)
                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if(lastLocation!=null){
                        val lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))
                        mMap.isMyLocationEnabled = true
                    }
                }
            }
            else{
                Toast.makeText(this@MapsActivity,"İzin gerekli.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        selectedLatitude=p0.latitude
        selectedLongitude=p0.longitude
        binding.saveButton.isEnabled=true
    }

    fun save(view : View){
        if(selectedLatitude != null && selectedLongitude != null){
            if(Build.VERSION.SDK_INT >=26)
            {
                val date = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                val formatted = date.format(formatter)
                val place= Place(binding.placeText.text.toString(),binding.infoText.text.toString(),formatted,selectedLatitude!!,selectedLongitude!!)
                compositeDisposable.add(
                    placeDao.insert(place).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse)
                )
            }
            else
            {
                val formatted = ""
                val place= Place(binding.placeText.text.toString(),binding.infoText.text.toString(),formatted,selectedLatitude!!,selectedLongitude!!)
                compositeDisposable.add(
                    placeDao.insert(place).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse)
                )
            }
        }
    }

    fun update(view: View) {
        // Ensure placeFromMain is not null, as it holds the existing place with its ID
        placeFromMain?.let { existingPlace ->
            // Use the new location if the user long-clicked on the map
            // Otherwise, keep the existing location
            val latitudeToUpdate = selectedLatitude ?: existingPlace.latitude
            val longitudeToUpdate = selectedLongitude ?: existingPlace.longitude

            val placeName = binding.placeText.text.toString()
            val placeInfo = binding.infoText.text.toString()
            val formattedTime: String // Changed variable name from 'formatted' to 'formattedTime' for clarity

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val date = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                formattedTime = date.format(formatter)
            } else {
                // For older APIs, you might want to preserve the original time,
                // or get current time using older methods if needed.
                // Using existingPlace.time ensures the original time is kept if not on API 26+
                formattedTime = existingPlace.time
                // Alternatively, if you always want a new timestamp (even if less precise or empty on older APIs):
                // val currentTimeMillis = System.currentTimeMillis()
                // val sdf = java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                // formattedTime = sdf.format(java.util.Date(currentTimeMillis))
                // Or simply:
                // formattedTime = "" // as in your original save function's else block
            }

            // Create the Place object for the update.
            // It's CRUCIAL to set the 'id' from the 'existingPlace'.
            val updatedPlace = Place(
                name = placeName,
                info = placeInfo,
                time = formattedTime, // Use the 'time' field as per your Place entity
                latitude = latitudeToUpdate,
                longitude = longitudeToUpdate
            ).apply {
                // Set the id from the existingPlace to tell Room which record to update
                id = existingPlace.id
            }

            compositeDisposable.add(
                placeDao.update(updatedPlace) // Use the update DAO method
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            // Successfully updated
                            this.handleResponse() // Navigate back or give success feedback
                        },
                        { throwable ->
                            // Error during update
                            Toast.makeText(this, "Error updating place: ${throwable.message}", Toast.LENGTH_LONG).show()
                            // You might want to log the error as well
                            // Log.e("MapsActivity", "Failed to update place", throwable)
                        }
                    )
            )
        } ?: run {
            // This block executes if placeFromMain is null.
            // This shouldn't happen if the update button is only visible when editing an existing place.
            Toast.makeText(this, "Error: No place data found to update.", Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(view : View){
        placeFromMain?.let{
            compositeDisposable.add(
                placeDao.delete(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }
    }

    fun backClick(view : View){
        val intent = Intent(this,FavoriYerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun handleResponse(){
        val intent = Intent(this,FavoriYerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}