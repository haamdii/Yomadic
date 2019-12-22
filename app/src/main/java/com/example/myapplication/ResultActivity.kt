package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.FontsContract
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_result.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException
import androidx.core.app.ComponentActivity

import androidx.core.app.ComponentActivity.ExtraData

import androidx.core.content.ContextCompat.getSystemService

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class ResultActivity : AppCompatActivity() {


        lateinit var mapFragment: SupportMapFragment
        lateinit var googleMap: GoogleMap

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_result)




             CoroutineScope(IO).launch {

                 withContext(Main) {


                     val dest = intent?.getStringExtra("destination")
                     val places = FetchData(dest)
                     val lat = places.getlats()[0]
                     val lng = places.getlangs()[0]
                     val name = places.getNames()[0]

                     val lat1 = places.getlats()[1]
                     val lng1 = places.getlangs()[1]
                     val name1 = places.getNames()[1]

                     val lat2 = places.getlats()[2]
                     val lng2 = places.getlangs()[2]
                     val name2 = places.getNames()[2]

                     mapFragment =
                         supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                     mapFragment.getMapAsync(OnMapReadyCallback {
                         googleMap = it

                         googleMap.isMyLocationEnabled = true         // show your current location ( add location permission in the app )

                  val location1 = LatLng(lat.toDouble(),lng.toDouble())
                  googleMap.addMarker(MarkerOptions().position(location1).title(name))
                  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,12f))

                  val location2=LatLng(lat1.toDouble(),lng1.toDouble())
                   googleMap.addMarker(MarkerOptions().position(location2).title(name1))
                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2,12f))


                val location3 = LatLng(lat2.toDouble(),lng2.toDouble())
                         googleMap.addMarker(MarkerOptions().position(location3).title(name2))
                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location3,12f))


                        val lineoption =  PolylineOptions()
                         lineoption.add(location1)
                         lineoption.add(location2)
                         lineoption.add(location3)
                         lineoption.width(10f)
                         lineoption.color(Color.GREEN)
                         lineoption.geodesic(true)

                         googleMap.addPolyline(lineoption)


                     })

                 }
             }
        }
    }



