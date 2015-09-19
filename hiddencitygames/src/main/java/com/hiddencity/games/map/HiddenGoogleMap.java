package com.hiddencity.games.map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hiddencity.games.R;
import com.hiddencity.games.rest.BeaconizedMarker;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class HiddenGoogleMap {

    private Context context;
    private GoogleMap googleMap;
    private HiddenMarkersModel hiddenMarkersModel;

    public HiddenGoogleMap(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        this.googleMap.setPadding(10, 70, 10, 10);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.hiddenMarkersModel = new HiddenMarkersModel();
    }

    public String contentIdByBeaconId(String beaconId){
        return hiddenMarkersModel.contentIdByBeaconId(beaconId);
    }


    public void addMarkers(List<BeaconizedMarker> placeList){
        hiddenMarkersModel.margeMarkers(placeList);
        drawMarkers();
    }

    private void drawMarkers() {
        googleMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (BeaconizedMarker place : hiddenMarkersModel.getMarkers()) {

            if(place.isActive()) {

                MarkerOptions markerOption = new MarkerOptions()
                        .position(new LatLng(place.getLocation().getLat(), place.getLocation().getLong()))
                        .title("Następne zadanie")
                        .icon(hiddenMarkersModel.activeBeacon())
                        .snippet("Poznaj przeznaczenie");

                googleMap.addMarker(markerOption);
                builder.include(markerOption.getPosition());
            }

            if(place.isShowed()) {

                MarkerOptions markerOption = new MarkerOptions()
                        .position(new LatLng(place.getLocation().getLat(), place.getLocation().getLong()))
                        .title(place.getTitle())
                        .icon(hiddenMarkersModel.showedBeacon())
                        .snippet(place.getContent());

                googleMap.addMarker(markerOption);
                builder.include(markerOption.getPosition());
            }

        }

        if(hiddenMarkersModel.size()>0) {
            LatLngBounds bounds = builder.build();
            int padding = 100; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cu);
        }
    }


    private BitmapDescriptor markerIcon(String image_url) {
        if(image_url!=null && !"".equals(image_url)) {
            ImageView imageView = new ImageView(context);
            Picasso.with(context).load(image_url).into(imageView);
            return BitmapDescriptorFactory.fromBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap());
        }
        return BitmapDescriptorFactory.fromResource(R.drawable.placeholder);
    }

    public void setInfoWindowAdapter(HiddenInfoAdapter infoWindowAdapter) {
        googleMap.setInfoWindowAdapter(infoWindowAdapter);
    }
}
