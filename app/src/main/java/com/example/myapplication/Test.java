package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView img = (ImageView) findViewById(R.id.img) ;

        String s = "https://fastly.4sqi.net/img/general/36x36/U08a9nzvbjWKEsHOM-kvWsRgw75aUgVnQWCJlVHf08I.jpg" ;
        Picasso.get().load(s).into(img);


    }
}
