package com.example.myapplication

import com.google.gson.GsonBuilder
import okhttp3.*
import ru.gildor.coroutines.okhttp.await
import java.io.IOException

class FetchData (val dest:String?) {

    lateinit var link:String

    constructor(dest: String?, inter: String?,distance:String?,popularity:String?) : this(dest) {

        link="https://api.foursquare.com/v2/venues/explore?client_id=5ZPHRKEVEL0Q40GUC5XPQSED0VC0CR1BSM5G2RXKFDX2OKKA&client_secret=WYAXSUPXAHMLUCN4KMK30OJTMFGRLMRHLPHLVMR1QBV1CYUB&v=20200113&near=$dest&section=$inter&sortByDistance=$distance&sortByPopularity=$popularity"

    }

        var homeFeed: HomeFeed? = null

         private suspend fun execute(): HomeFeed? {

            var ch: String? = null

            val client = OkHttpClient()
            val url = link
            val request = Request.Builder().url(url).build()
            val res = client.newCall(request).await()

            if (!res.isSuccessful) throw IOException("Exception")
            else {

                ch = res?.body?.string()
                val gson = GsonBuilder().create()

                homeFeed = gson.fromJson(ch, HomeFeed::class.java)

            }

            return homeFeed
        }


        suspend fun getNames(): MutableList<String> {  // list of places names

            val feed = this.execute()
            val n = feed?.response!!.groups[0].items.size
            var namelist = mutableListOf<String>()

            for (i in 0..n - 1) {

                namelist.add(feed?.response!!.groups[0].items[i].venue.name)

            }
            return namelist
        }

        suspend fun getlats(): MutableList<Float> {  // list of places latitudes

            val feed = this.execute()
            val n = feed?.response!!.groups[0].items.size

            var latslist = mutableListOf<Float>()

            for (i in 0..n - 1) {

                latslist.add(feed?.response!!.groups[0].items[i].venue.location.lat)

            }
            return latslist
        }

        suspend fun getlangs(): MutableList<Float> {  // list of places langitudes

            val feed = this.execute()
            val n = feed?.response!!.groups[0].items.size
            var langlist = mutableListOf<Float>()

            for (i in 0..n - 1) {

                langlist.add(feed?.response!!.groups[0].items[i].venue.location.lng)

            }
            return langlist
        }

        suspend fun getcities(): MutableList<String> {  // list of places cities

            val feed = this.execute()
            val n = feed?.response!!.groups[0].items.size
            var citieslist = mutableListOf<String>()

            for (i in 0..n - 1) {

                citieslist.add(feed?.response!!.groups[0].items[i].venue.location.city)

            }
            return citieslist
        }

        suspend fun getcategoriesnames(): MutableList<String> {  // list of places categories names

            val feed = this.execute()
            val n = feed?.response!!.groups[0].items.size
            var categorieslist = mutableListOf<String>()

            for (i in 0..n - 1) {
                categorieslist.add(feed?.response!!.groups[0].items[i].venue.categories[0].name)

            }
            return categorieslist
        }


        suspend fun getcategoriesids():MutableList<String> { // list of places categories ids

            val feed =this.execute()
            val n = feed?.response!!.groups[0].items.size
            var idslist = mutableListOf<String>()


            for ( i in 0..n-1) {
                idslist.add(feed?.response!!.groups[0].items[i].venue.categories[0].id)
            }

            return idslist

        }

    suspend fun getformattedadresses():MutableList<List<String>> { // list of places formattedadresses

        val feed =this.execute()
        val n = feed?.response!!.groups[0].items.size
        var formlist = mutableListOf<List<String>>()


        for ( i in 0..n-1) {
            formlist.add(feed?.response!!.groups[0].items[i].venue.location.formattedAddress)
        }

        return formlist

    }

}




class HomeFeed(val response: Resp)
class Resp(val groups: List<Group> )
class Group( val items:List<Item>)
class Item(val venue:Venue)
class Venue(val name :String , val location:Location,val categories:List<Category> )
class Location(val city:String ,val state:String,val country:String,val lat:Float ,val lng :Float,val formattedAddress:List<String>)
class Category(val name:String,val id:String,val icon : Icon)
class Icon(val prefix:String,val suffix:String)