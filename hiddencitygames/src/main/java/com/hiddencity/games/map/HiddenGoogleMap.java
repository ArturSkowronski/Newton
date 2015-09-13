package com.hiddencity.games.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hiddencity.games.rest.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class HiddenGoogleMap {

    private Context context;
    private GoogleMap googleMap;

    public HiddenGoogleMap(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        this.googleMap.setPadding(10, 70, 10, 10);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    public void addMarkers(List<Place> placeList){
        for (Place place : placeList) {
            googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(place.getLat(), place.getLng()))
                .title(place.getTitle())
                .icon(BitmapDescriptorFactory.fromBitmap(markerIcon(place.getImage_url())))
                .snippet(place.getContent())
            );
        }
    }

    private Bitmap markerIcon(String image_url) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(image_url).into(imageView);
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    public void setInfoWindowAdapter(HiddenInfoAdapter infoWindowAdapter) {
        googleMap.setInfoWindowAdapter(infoWindowAdapter);
    }
}
