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

    public List<PlacesEntity> findAll() {
        List<PlacesEntity> placesEntities = new ArrayList<>();
        Realm realm = Realm.getInstance(context);

        RealmQuery<PlacesEntity> query = realm.where(PlacesEntity.class);

        RealmResults<PlacesEntity> result = query.findAll();

        for (PlacesEntity placesEntity : result) {
            placesEntities.add(placesEntity);
        }

        return placesEntities;
    }

    public void clearDB() {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        realm.where(PlacesEntity.class).findAll().clear();
        realm.where(BeaconEntity.class).findAll().clear();

        realm.commitTransaction();

        realm.close();

    }

    public void activateBeacon(ActiveBeaconResponse activeBeaconResponse) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        List<PlacesEntity> all = findAll();
        for (PlacesEntity placesEntity : all) {
            if (activeBeaconResponse != null && activeBeaconResponse.get_id() != null && placesEntity.getId().equals(activeBeaconResponse.get_id())) {
                placesEntity.setActive(true);
            } else {
                if (placesEntity.isActive()) {
                    placesEntity.setShowed(true);
                }
                placesEntity.setActive(false);

            }
        }
        realm.commitTransaction();
    }

    public PlacesEntity findByName(String id) {
        Realm realm = Realm.getInstance(context);

        RealmQuery<PlacesEntity> query = realm.where(PlacesEntity.class).equalTo("id", id);

        PlacesEntity placesEntity = query.findFirst();

        return placesEntity;
    }

    public List<PlacesEntity> persistAll(List<BeaconizedMarker> beaconizedMarkers) {
        for (BeaconizedMarker beaconizedMarker : beaconizedMarkers) {
            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();
            PlacesEntity placeEntity = realm.createObject(PlacesEntity.class); // Create a new object
            placeEntity.setId(beaconizedMarker.get_id());
            placeEntity.setActive(false);
            placeEntity.setLat(beaconizedMarker.getLocation().getLat());
            placeEntity.setLng(beaconizedMarker.getLocation().getLong());
            String image_url = "";
            if (beaconizedMarker.getInfo() != null && beaconizedMarker.getInfo().getIcon() != null)
                image_url = beaconizedMarker.getInfo().getIcon();

            placeEntity.setImageUrl(image_url);
            List<String> beacon = beaconizedMarker.getBeacon();
            for (String beaconName : beacon) {
                BeaconEntity beaconEntity = realm.createObject(BeaconEntity.class); // Create a new object
                beaconEntity.setTitle(beaconizedMarker.getTitle());

                beaconEntity.setImageUrl(image_url);
                beaconEntity.setPlaceId(beaconizedMarker.get_id());
                beaconEntity.setContent(beaconizedMarker.get_id());
                beaconEntity.setId(beaconName);
            }

            realm.commitTransaction();
        }
        return findAll();
    }
}
