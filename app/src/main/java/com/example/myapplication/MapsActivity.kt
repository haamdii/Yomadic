package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    lateinit var name: String
    lateinit var name1: String
    lateinit var name2: String
    var lat1: Float = 0.0f
    var lng1: Float = 0.0f
    var lng2: Float = 0.0f
    var lat2: Float = 0.0f
    var lng: Float = 0.0f
    var lat: Float = 0.0f

    private lateinit var lastLocation: Location
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        mMap.getUiSettings().setMyLocationButtonEnabled(true)
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            setUpMap()
        }

    }


    private suspend fun setUpMap() {

        val job = CoroutineScope(Dispatchers.IO).launch {

            getdata()
        }
        job.join()

        val lineoption = PolylineOptions().width(10f).color(Color.GREEN).geodesic(true)
        var location = LatLng(lat.toDouble(), lng.toDouble())
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))
        var markerOptions = MarkerOptions().position(location)
        lineoption.add(location)
        mMap.addMarker(markerOptions.title(name))


        location = LatLng(lat1.toDouble(), lng1.toDouble())
        markerOptions = MarkerOptions().position(location)
        lineoption.add(location)
        mMap.addMarker(markerOptions.title(name1))

        location = LatLng(lat2.toDouble(), lng2.toDouble())
        markerOptions = MarkerOptions().position(location)
        lineoption.add(location)
        mMap.addMarker(markerOptions.title(name2))

        mMap.addPolyline(lineoption)


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3

            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item1 -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.item2 -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.item3 -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.item4 -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        return true
    }
    suspend fun getdata() {

        val dest = intent?.getStringExtra("destination")
        val  places = FetchData(dest)
        lat = places.getlats()[0]
        lng = places.getlangs()[0]
        name = places.getNames()[0]

        lat1 = places.getlats()[1]
        lng1 = places.getlangs()[1]
        name1 = places.getNames()[1]

        lat2 = places.getlats()[2]
        lng2 = places.getlangs()[2]
        name2 = places.getNames()[2]

    }
}
