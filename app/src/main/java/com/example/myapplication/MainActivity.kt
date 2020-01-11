package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.*
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val dest = findViewById<EditText>(R.id.d)
        val btn = findViewById<Button>(R.id.btn)

       // val ch =""
       btn.setOnClickListener {

           val  destination = dest.text.toString()

           val i = Intent ( this , MapsActivity::class.java)

           i.putExtra("destination" , destination)

           startActivity(i)

       }
    }

}

