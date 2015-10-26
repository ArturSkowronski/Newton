package com.hiddencity.games.map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hiddencity.games.R;
import com.hiddencity.games.db.table.PlacesEntity;
import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.BeaconizedMarker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.setPadding(10, 70, 10, 10);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.hiddenMarkersModel = new HiddenMarkersModel();
    }

    public void addMarkers(List<PlacesEntity> placesEntities) {
        drawMarkers(placesEntities);
    }

    private void drawMarkers(List<PlacesEntity> placesEntities) {
        googleMap.clear();
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        final LatLngBounds.Builder allBuilder = new LatLngBounds.Builder();
        final List<LatLng> positions = new ArrayList<>();
        final List<LatLng> allPositions = new ArrayList<>();
        for (PlacesEntity place : placesEntities) {

            if (place.isActive()) {
                MarkerOptions markerOption = new MarkerOptions()
                    .position(new LatLng(place.getLat(), place.getLng()))
                    .icon(hiddenMarkersModel.activeBeacon());
                googleMap.addMarker(markerOption);
                positions.add(markerOption.getPosition());

            }

            if (place.isShowed()) {

                MarkerOptions markerOption = new MarkerOptions()
                        .position(new LatLng(place.getLat(), place.getLng()))
//                        .title(place.getBeacon().getTitle())
//                        .snippet(place.getBeacon().getContent())
//                        .icon(hiddenMarkersModel.showedBeacon());
                        .icon(markerIcon(place.getImageUrl()));
                googleMap.addMarker(markerOption);
                positions.add(markerOption.getPosition());
            }
            allPositions.add(new LatLng(place.getLat(), place.getLng()));
        }

        if (positions.size() > 1) {
            for (LatLng latLng : positions) {
                builder.include(latLng);
            }
            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    LatLngBounds bounds = builder.build();
                    int boundsAdd = 0;
                    CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 100 + boundsAdd);
                    googleMap.moveCamera(update);

                    // Remove listener to prevent position reset on camera move.
                    googleMap.setOnCameraChangeListener(null);
                }
            });

        } else {
            if (allPositions.size() > 0) {

                for (LatLng latLng : allPositions) {
                    allBuilder.include(latLng);
                }

                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    @Override
                    public void onCameraChange(CameraPosition arg0) {
                        LatLngBounds bounds = allBuilder.build();
                        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 10);
                        googleMap.moveCamera(update);

                        // Remove listener to prevent position reset on camera move.
                        googleMap.setOnCameraChangeListener(null);
                    }
                });

            }
        }
    }


    private BitmapDescriptor markerIcon(String image_url) {
        String backend = context.getResources().getString(R.string.backend_endpoint);
        if (image_url != null && !"".equals(image_url)) {
            ImageView imageView = new ImageView(context);
            Picasso.with(context).load(backend + image_url).into(imageView);
            if ((imageView.getDrawable()) != null)
                return BitmapDescriptorFactory.fromBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap());
        }
        return hiddenMarkersModel.showedBeacon();
    }

    public void setInfoWindowAdapter(HiddenInfoAdapter infoWindowAdapter) {
        googleMap.setInfoWindowAdapter(infoWindowAdapter);
    }
}
