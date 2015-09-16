package com.hiddencity.games.map;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.hiddencity.games.rest.PlaceReq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class HiddenMarkersModel {
    List<PlaceReq> markersModel = new ArrayList<>();

    public List<PlaceReq> getMarkers() {
        return markersModel;
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

    public void margeMarkers(List<PlaceReq> placeList) {
        for (PlaceReq placeReq : placeList) {
            mergeOrUpdateMarker(placeReq);
        }
    }

    private void mergeOrUpdateMarker(PlaceReq placeReq) {
        if(contains(placeReq)){
            PlaceReq current = find(placeReq);
            if(placeReq.isActive()) {
                deactivateAll();
                current.setActive(true);
            }
        } else {
            markersModel.add(placeReq);
        }

    }

    private void deactivateAll() {
        for (PlaceReq req : markersModel) {
            req.setActive(false);
            req.setShowed(true);
        }
    }

    private boolean contains(PlaceReq placeReq) {
        return find(placeReq)!=null;
    }

    private PlaceReq find(PlaceReq placeReq){
        for (PlaceReq req : markersModel) {
            if(placeReq.get_id().equals(req.get_id())) return req;
        }
        return null;
    }
}
