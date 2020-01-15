package com.example.myapplication

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import java.io.IOException
import java.lang.StringBuilder

class FetchImages ( val venueid:String?){

    var imageFeed: ImageFeed? = null

    val client_id1 = "5ZPHRKEVEL0Q40GUC5XPQSED0VC0CR1BSM5G2RXKFDX2OKKA"
    val client_secret1="WYAXSUPXAHMLUCN4KMK30OJTMFGRLMRHLPHLVMR1QBV1CYUB"

    val client_id2="ARGQTCHGNX3QBGTX1FIVSQOWSGAJDPWIIRRX3Q1FXQLZ1KQI"
    val client_secret2="G1R1ER45UM5ZNH5O2OM24L14MUQVPSNF1H1WPJIQQHPR1UNV"

    val client_id3="23ATDTXBIG5RTNVYL4J4YVGPL4XI010L5TXBOUOFOPCVVVJD"
    val client_secret3="YQG5DOR20ZKDLTEVWIQZQWZUDIW3XBIWSM4AVT5T0NT42Y2J"

    val client_id4="FRG44DRD4VCUUPXYEUC2GCOG4FUPSBQA05USHRJFE1JKFF5O"
    val client_secret4="OR0HX4CIEARKAUXAOPQ2U5BN54U3H5IXDLCWLIOGBQK0T2VR"

    suspend fun execute(): Itemm{

        var ch: String? = null


        val url = "https://api.foursquare.com/v2/venues/$venueid/photos?client_id=$client_id4&client_secret=$client_secret4&v=20200114"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val res = client.newCall(request).await()

        if (!res.isSuccessful) throw IOException("Exception")
        else {

            ch = res?.body?.string()
            val gson = GsonBuilder().create()

           imageFeed = gson.fromJson(ch, ImageFeed::class.java)

        }

        return imageFeed?.response!!.photos.items[0]
    }


    suspend fun getSuffix(): String {  // list of images suffixes

        val feed = this.execute()
        var suffix = StringBuilder()

        suffix.append(feed.suffix)

        return suffix.toString()
    }

    suspend fun getPrefix(): String {  // list of images prefixes

        val feed = this.execute()
        var prefix = StringBuilder()

        prefix.append(feed.prefix)

        return prefix.toString()

    }


}





// DTO

class ImageFeed(@SerializedName("response")val response:Res)
class Res(@SerializedName("photos")val photos:Photo)
class Photo(@SerializedName("items")val items:List<Itemm>)
class Itemm(@SerializedName("prefix")val prefix:String,@SerializedName("suffix")val suffix:String)