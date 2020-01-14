package com.example.myapplication;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMape implements GoogleMap.InfoWindowAdapter {

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


        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        name.setText(infoWindowData.getName());
        infos.setText(infoWindowData.getInfos());
        rp.setText(infoWindowData.getrp());

        return view;


}}

