package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;
import okhttp3.Dispatcher;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

public class CustomInfoWindowGoogleMape implements GoogleMap.InfoWindowAdapter {

    //private static final CoroutineContext IO = ;
    private Context context;

    public CustomInfoWindowGoogleMape(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.infowindow, null);

        TextView name  = view.findViewById(R.id.name);
        TextView infos = view.findViewById(R.id.infos);
        TextView rp = view.findViewById(R.id.rp);
        ImageView img = view.findViewById(R.id.pic);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        name.setText(infoWindowData.getName());
        infos.setText(infoWindowData.getInfos());
        rp.setText(infoWindowData.getrp());



        String url = infoWindowData.getUrl() ;

        Picasso.get().load(url).into(img);




        // Image link from internet
        /*DownloadImageFromInternet d =new DownloadImageFromInternet(img) ;
             Bitmap im =d.doInBackground(url) ;
             img.setImageBitmap(im);*/




















        /*
        Bitmap bmp = null;

        try {


            InputStream in = new java.net.URL(url).openStream();
            bmp= BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }

        img.setImageBitmap(bmp) ;*/








/*
        URL urle = null;
        try {
            urle = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;

        try {
                    bmp = BitmapFactory.decodeStream(urle.openConnection().getInputStream()); }


         catch (IOException e) {
            e.printStackTrace();
        }*/
       // img.setImageBitmap(bmp);*/















        return view;


}}

