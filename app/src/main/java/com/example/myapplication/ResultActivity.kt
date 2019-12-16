package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.FontsContract
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_result.view.*
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

         val dest = intent?.getStringExtra("destination")

         fetchJson(dest)

    }


    fun fetchJson(destination: String?) {


        val name = findViewById<TextView>(R.id.name)
        val category = findViewById<TextView>(R.id.category)
        val lat = findViewById<TextView>(R.id.lat)
        val lng = findViewById<TextView>(R.id.lng)
        val city = findViewById<TextView>(R.id.city)


        var cha = ""


        val client = OkHttpClient()
        val url =
            "https://api.foursquare.com/v2/venues/explore?client_id=5ZPHRKEVEL0Q40GUC5XPQSED0VC0CR1BSM5G2RXKFDX2OKKA&client_secret=WYAXSUPXAHMLUCN4KMK30OJTMFGRLMRHLPHLVMR1QBV1CYUB&v=20191110&near=$destination"
        val request = Request.Builder().url(url).build()




        val res = client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()

            }

            override fun onResponse(call: Call, response: Response) {

                val ch = response?.body?.string()

                val gson = GsonBuilder().create()
                val homefeed = gson.fromJson(ch, HomeFeed::class.java)


                cha = homefeed.response.groups[0].items[0].venue.name
                name.text = cha
             //   test.setText( "sayeb" )

                cha = homefeed.response.groups[0].items[0].venue.categories[0].name
                category.text = cha

                cha = homefeed.response.groups[0].items[0].venue.location.city
                city.text = cha

                cha = homefeed.response.groups[0].items[0].venue.location.lat.toString()
                lat.text = cha

                cha = homefeed.response.groups[0].items[0].venue.location.lng.toString()
                lng.text = cha


/*
                val ch2 = homefeed.response.groups[0].items[0].venue.name
                 Log.d("name" , ch2 )*/
                /*  val test = findViewById<TextView>(R.id.t1)
                  test.text = "$ch" */
            }
        })


    }
}

class HomeFeed(val response:Resp)
class Resp(val groups: List<Group> )
class Group( val items:List<Item>)
class Item(val venue:Venue)
class Venue(val name :String , val location:Location,val categories:List<Category> )
class Location(val city:String ,val state:String,val country:String,val lat:Float ,val lng :Float)
class Category(val name:String,val icon : Icon)
class Icon(val prefix:String,val suffix:String)