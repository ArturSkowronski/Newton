package com.hiddencity.games.map;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.hiddencity.games.rest.BeaconizedMarker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class HiddenMarkersModel {
    List<BeaconizedMarker> markersModel = new ArrayList<>();

    public List<BeaconizedMarker> getMarkers() {
        return markersModel;
    }

    public String contentIdByBeaconId(String beaconId){
        for (BeaconizedMarker beaconizedMarker : markersModel) {
            if(beaconizedMarker.getBeacon().equals(beaconId)){
                return beaconizedMarker.getContent();
            }
        }
        return null;
    }

    public BitmapDescriptor activeBeacon(){
        BitmapDescriptor bitmapDescriptor
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED);
        return  bitmapDescriptor;
    }

    public BitmapDescriptor showedBeacon(){
        BitmapDescriptor bitmapDescriptor
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_BLUE);
        return  bitmapDescriptor;
    }

    public int size() {
        return markersModel.size();
    }

    public void margeMarkers(List<BeaconizedMarker> placeList) {
        for (BeaconizedMarker beaconizedMarker : placeList) {
            mergeOrUpdateMarker(beaconizedMarker);
        }
    }

    private void mergeOrUpdateMarker(BeaconizedMarker beaconizedMarker) {
        if(contains(beaconizedMarker)){
            BeaconizedMarker current = find(beaconizedMarker);
            if(beaconizedMarker.isActive()) {
                deactivateAll();
                current.setActive(true);
            }
        } else {
            markersModel.add(beaconizedMarker);
        }

    }

    private void deactivateAll() {
        for (BeaconizedMarker req : markersModel) {
            req.setActive(false);
            req.setShowed(true);
        }
    }

    private boolean contains(BeaconizedMarker beaconizedMarker) {
        return find(beaconizedMarker)!=null;
    }

    private BeaconizedMarker find(BeaconizedMarker beaconizedMarker){
        for (BeaconizedMarker req : markersModel) {
            if(beaconizedMarker.get_id().equals(req.get_id())) return req;
        }
        return null;
    }
}
