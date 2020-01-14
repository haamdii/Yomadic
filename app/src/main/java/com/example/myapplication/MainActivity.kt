package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val dest = findViewById<EditText>(R.id.des)
        val inter =findViewById<EditText>(R.id.i)
        val d =findViewById<EditText>(R.id.d)
        val h =findViewById<EditText>(R.id.h)

        val btn = findViewById<Button>(R.id.btn)


       btn.setOnClickListener {


           val days = d.text.toString().toInt()
           val hours = h.text.toString().toInt()
           val destination = dest.text.toString()
           val interest = inter.text.toString()


           val i = Intent ( this , MapsActivity::class.java)

           i.putExtra("destination",destination)
           i.putExtra("interest",interest)
           i.putExtra("hours",hours)
           i.putExtra("days",days)

           startActivity(i)

       }
    }
}

