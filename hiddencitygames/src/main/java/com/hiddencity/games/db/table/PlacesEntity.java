package com.hiddencity.games.db.table;

import io.realm.RealmObject;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class PlacesEntity extends RealmObject {
    private double lat;
    private double lng;
    private boolean active;
    private boolean showed;
    private BeaconEntity beacon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    public BeaconEntity getBeacon() {
        return beacon;
    }

    public void setBeacon(BeaconEntity beacon) {
        this.beacon = beacon;
    }
}
