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

    public void activateBeacon(ActiveBeaconResponse activeBeaconResponse){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        List<PlacesEntity> all = findAll();
        for (PlacesEntity placesEntity : all) {
            if(activeBeaconResponse.get_id().equals(placesEntity.getId())){
                placesEntity.setActive(true);
            } else {
                placesEntity.setActive(false);
                if(placesEntity.isActive()){
                    placesEntity.setShowed(true);
                }
            }
        }
        realm.commitTransaction();
    }

    public PlacesEntity findByName(String id){
        Realm realm = Realm.getInstance(context);

        RealmQuery<PlacesEntity> query = realm.where(PlacesEntity.class).equalTo("id", id);

        PlacesEntity placesEntity = query.findFirst();

        return placesEntity;
    }

    public List<PlacesEntity> persistAll(List<BeaconizedMarker> beaconizedMarkers){
        for (BeaconizedMarker beaconizedMarker : beaconizedMarkers) {
            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();
            PlacesEntity placeEntity = realm.createObject(PlacesEntity.class); // Create a new object
            BeaconEntity beaconEntity = realm.createObject(BeaconEntity.class); // Create a new object
            placeEntity.setId(beaconizedMarker.get_id());
            placeEntity.setActive(false);
            placeEntity.setLat(beaconizedMarker.getLocation().getLat());
            placeEntity.setLng(beaconizedMarker.getLocation().getLong());
            beaconEntity.setTitle(beaconizedMarker.getTitle());
            String image_url = beaconizedMarker.getImage_url() == null ? "" : beaconizedMarker.getImage_url();
            beaconEntity.setImageUrl(image_url);
            beaconEntity.setPlaceId(beaconizedMarker.get_id());
            beaconEntity.setContent(beaconizedMarker.get_id());
            beaconEntity.setId(beaconizedMarker.getBeacon());
            placeEntity.setBeacon(beaconEntity);
            realm.commitTransaction();
        }
        return findAll();
    }
}
