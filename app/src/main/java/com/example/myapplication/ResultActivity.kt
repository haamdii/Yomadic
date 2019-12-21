package com.example.myapplication

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

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val name = findViewById<EditText>(R.id.name)
        val category = findViewById<TextView>(R.id.category)
        val lat = findViewById<TextView>(R.id.lat)
        val lng = findViewById<TextView>(R.id.lng)
        val city = findViewById<TextView>(R.id.city)

        val dest = intent?.getStringExtra("destination")

        val places = FetchData(dest)



        //all of the work in MAP will be inside this

        CoroutineScope(IO).launch {

            /*val names = places.getNames()            // we can intercept lists of informations in variables
            val lats = places.getlats()
            val categories = places.getcategories()
            val cities = places.getcities()
            val langs = places.getlangs()*/


            withContext(Main) {                        // to work in the main thread


                                                       // example of showing informations of the 8th venue found

                name.setText(places.getNames()[8])
                city.setText(places.getcities()[8])
                category.setText(places.getcategories()[8])
                lat.setText(places.getlats()[8].toString())
                lng.setText(places.getlangs()[8].toString())


            }


        }

  }


}

