package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

     var name= mutableListOf<String>()
     var lng=  mutableListOf<Float>()
     var lat= mutableListOf<Float>()
     var id= mutableListOf<String>()
     var category = mutableListOf<String>()
    var formattedadress = mutableListOf<List<String>>()

    var range =0
    var nb=0
    var i=0

    var hours =0
    var days =0
    var period=0
    var duree=0
    var rhours=0
    var rmins=0


    lateinit var dest:String
    lateinit var inter:String
     var distance="0"
     var popularity="0"


    lateinit var location:LatLng
    lateinit var mylocation:LatLng

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
        }
        CoroutineScope(Dispatchers.Main).launch {
            setUpMap()
        }

    }

    suspend fun getdata() {

        dest = intent.getStringExtra("destination")
        inter = intent.getStringExtra("interest")
        hours =intent.getIntExtra("hours",0)
        days =intent.getIntExtra("days",0)

        period+=(24*days+hours)*60

        if (period>=360) {
            popularity="1"
        }
        else {
            distance="1"
        }

        val  places = FetchData(dest,inter,distance,popularity)
        lat = places.getlats()
        lng = places.getlangs()
        name = places.getNames()
        id = places.getcategoriesids()
        category=places.getcategoriesnames()
        formattedadress = places.getformattedadresses()


    }

    private suspend fun setUpMap() {

        val job = CoroutineScope(Dispatchers.IO).launch {

            getdata()
        }
        job.join()

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



        val lineoption = PolylineOptions().width(10f).color(Color.GREEN).geodesic(true)


     /******* wants to get latlng of my current location but null is returned *******************

       val lmanager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val critere = Criteria()
        critere.accuracy=Criteria.ACCURACY_FINE
        critere.isCostAllowed=true
        val best = lmanager.getBestProvider(critere,true)
       val locationa = lmanager.getLastKnownLocation(best)
        val mylocation = LatLng(locationa.latitude,locationa.longitude)
        lineoption.add(mylocation)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,15f))
       delay(2000)*
     ************************************************************************************/


        while (period>0) {

            duree = when (id[i]) {

                "4bf58dd8d48988d10a951735" -> 15 //Bank

                "4bf58dd8d48988d12d941735" -> 30   // landmark/Monument
                "4bf58dd8d48988d12a941735" -> 30  // Capitol Building
                "4bf58dd8d48988d129941735" -> 30  // City Hall
                "52e81612bcbc57f1066b7a38" -> 30 // Town Hall
                "4bf58dd8d48988d138941735" -> 30 //Mosque
                "4bf58dd8d48988d132941735" -> 30 // Church
                "52e81612bcbc57f1066b7a3e" -> 30 // Buddhist Temple
                "4bf58dd8d48988d13a941735" -> 30 // Temple
                "4bf58dd8d48988d1ed931735" -> 30 // Airport
                "4bf58dd8d48988d1eb931735" -> 30 // Airport Terminal
                "4bf58dd8d48988d12b951735" -> 30 // Bus line
                "4bf58dd8d48988d1fe931735" -> 30 //Bus Station
                "52f2ab2ebcbc57f1066b8b4f" -> 30 //Bus Stop
                "4bf58dd8d48988d1fd931735" -> 30 // Metro Station
                "56aa371be4b08b9a8d57353e" -> 30 //Port
                "53fca564498e1a175f32528b" -> 30 // Taxi stand
                "4f4531504b9074f6e4fb0102" -> 30 // Train station platform
                "4bf58dd8d48988d1df941735" -> 30 // Bridge
                "50aaa49e4b90af0d42d5de11" -> 30 // Castle

                "4bf58dd8d48988d196941735" -> 45 //Hospital
                "58daa1558bbb0b01f18ec1f7" -> 45 // Hospital ward
                "56aa371be4b08b9a8d5734ff" -> 45 // Maternity clinic
                "4bf58dd8d48988d194941735" -> 45 // Emergency room
                "52e81612bcbc57f1066b7a28" -> 45 // BAthing Area
                "52e81612bcbc57f1066b7a26" -> 45 // Recreation center
                "4bf58dd8d48988d165941735" -> 45 //Scenic lookout
                "56aa371be4b08b9a8d573560" -> 45 //Waterfall

                "52e81612bcbc57f1066b7a32" -> 120 //Cultural center
                "4bf58dd8d48988d171941735" -> 120 // Event space
                "56aa371be4b08b9a8d57356a" -> 120 //Outdoor Event Space
                "4bf58dd8d48988d12f941735" -> 120 // Library
                "56aa371be4b08b9a8d5734db" -> 120 // Amphitheater
                "56aa371be4b08b9a8d5734db" -> 120 //Gallery art
                "4bf58dd8d48988d17f941735" -> 120 // Movie Theater
                "4bf58dd8d48988d137941735" -> 120 //Theater
                "4bf58dd8d48988d17c941735" -> 120 // Casino
                "52e81612bcbc57f1066b79e7" -> 120 // Circus
                "52e81612bcbc57f1066b7a30" -> 120 // Beach
                "5109983191d435c0d71c2bb1" -> 120 // Theme Park Ride / Attraction

                "52e81612bcbc57f1066b7a2f" -> 150 //Bowling Green
                "4bf58dd8d48988d1e1941735" -> 150 // Basketball Court
                "56aa371be4b08b9a8d57351a" -> 150 // Curling Ice
                "4bf58dd8d48988d1e6941735" -> 150 // Golf Course
                "52f2ab2ebcbc57f1066b8b47" -> 150 //Boxing Gym
                "4bf58dd8d48988d105941735" -> 150 //Gym Pool
                "52f2ab2ebcbc57f1066b8b48" -> 150 //Gymnastics Gym
                "4bf58dd8d48988d176941735" -> 150  //Gym
                "590a0744340a5803fd8508c3" -> 150 //Weight Loss Center
                "4bf58dd8d48988d167941735" -> 150 //Skate Park
                "4cce455aebf7b749d5e191f5" -> 150 //Soccer Field
                "4e39a956bd410d7aed40cbc3" -> 150 //Tennis Court
                "4bf58dd8d48988d15e941735" -> 150 // Pool
                "52f2ab2ebcbc57f1066b8b46" -> 150 // Supermarket
                "50be8ee891d4fa8dcc7199a7" -> 150 // Market
                "52f2ab2ebcbc57f1066b8b42" -> 150 // Big Box Store
                "4bf58dd8d48988d18b941735" -> 150 //Basketball Stadium
                "4bf58dd8d48988d189941735" -> 150 //Football Stadium
                "4e39a891bd410d7aed40cbc2" -> 150 // Tennis Stadium

                "4bf58dd8d48988d188941735" -> 180 // Soccer Stadium
                "58daa1558bbb0b01f18ec1fd" -> 180 // Zoo
                "4bf58dd8d48988d11f941735" -> 180 // Nightclub

                "4eb1d4d54b900d56c88a45fc" -> 240 // Mountain
                "4bf58dd8d48988d18f941735" -> 240 //Art Museum
                "4bf58dd8d48988d190941735" -> 240 //History Museum
                "4bf58dd8d48988d191941735" -> 240 //Science Museum

                else -> 60
            }

            location = LatLng(lat[i].toDouble(), lng[i].toDouble())
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
            range++
            var markerOptions = MarkerOptions().position(location)
            lineoption.add(location)


             val info = InfoWindowData() //java


             val customInfoWindow = CustomInfoWindowGoogleMape(this)
             mMap.setInfoWindowAdapter(customInfoWindow)

            var ch = StringBuilder()
            ch.append("Address:")

            if ( (id.size - i) > 0 ) {                       // il y a encore des données à afficher
                if (period > duree) {
                    rhours = duree / 60
                    rmins = duree % 60

                   info.name= "(#$range) ${name[i]} : ${category[i]} "
                   info.setrp( "Stay there for : $rhours Hour(s) $rmins Min(s)")




                    for (j in 0..formattedadress[i].size-1) {

                        ch?.appendln(" ${formattedadress[i][j]} , ")

                    }

                    info.infos=ch.toString()

                     val m = mMap.addMarker(markerOptions)
                     m?.tag =info
                     m?.showInfoWindow()

                } else {

                    rhours = period / 60
                    rmins = (period % 60) * 60

                    info.name= "(#$range) ${name[i]} : ${category[i]} "
                    info.setrp( "Stay there for : $rhours Hour(s) $rmins Min(s)")

                    for (j in 0..formattedadress[i].size-1) {

                        ch?.appendln(" ${formattedadress[i][j]} , ")

                    }
                    info.infos=ch.toString()
                    val m = mMap.addMarker(markerOptions)
                    m?.tag =info
                    m?.showInfoWindow()

                }

                period -= duree
                i++
                mMap.addPolyline(lineoption)
                delay(3000)
            }
            else break
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))

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

}
