package com.hiddencity.games.adapters;

import android.content.Context;

import com.hiddencity.games.db.table.BeaconEntity;
import com.hiddencity.games.db.table.PlacesEntity;
import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.BeaconizedMarker;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class PlaceEntityAdapter {
    public PlaceEntityAdapter(Context context) {
        this.context = context;
    }

    Context context;

    public List<PlacesEntity> findAll(){
        List<PlacesEntity> placesEntities = new ArrayList<>();
        Realm realm = Realm.getInstance(context);

        RealmQuery<PlacesEntity> query = realm.where(PlacesEntity.class);

        RealmResults<PlacesEntity> result = query.findAll();

        for (PlacesEntity placesEntity : result) {
            placesEntities.add(placesEntity);
        }

        return placesEntities;
    }

    public List<PlacesEntity> findByBeaconName(){
        List<PlacesEntity> placesEntities = new ArrayList<>();
        Realm realm = Realm.getInstance(context);

        RealmQuery<PlacesEntity> query = realm.where(PlacesEntity.class);

        // Execute the query:
        RealmResults<PlacesEntity> result1 = query.findAll();
        for (PlacesEntity placesEntity : result1) {
            placesEntities.add(placesEntity);
        }
        return placesEntities;
    }

    public void activateBeacon(ActiveBeaconResponse activeBeaconResponse){

    }



    public List<PlacesEntity> persistAll(List<BeaconizedMarker> beaconizedMarkers){
        for (BeaconizedMarker beaconizedMarker : beaconizedMarkers) {
            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();
            PlacesEntity placeEntity = realm.createObject(PlacesEntity.class); // Create a new object
            BeaconEntity beaconEntity = realm.createObject(BeaconEntity.class); // Create a new object

            placeEntity.setActive(true);
            placeEntity.setLat(beaconizedMarker.getLocation().getLat());
            placeEntity.setLng(beaconizedMarker.getLocation().getLong());
            beaconEntity.setTitle(beaconizedMarker.getTitle());
            String image_url = beaconizedMarker.getImage_url() == null ? "" : beaconizedMarker.getImage_url();
            beaconEntity.setImageUrl(image_url);
            beaconEntity.setContent(beaconizedMarker.getContent());
            beaconEntity.setId(beaconizedMarker.get_id());
            placeEntity.setBeacon(beaconEntity);
            realm.commitTransaction();
        }
        return findAll();
    }
}
