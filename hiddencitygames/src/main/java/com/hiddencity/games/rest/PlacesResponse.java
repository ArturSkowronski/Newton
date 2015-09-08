package com.hiddencity.games.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class PlacesResponse {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public List<Place> getPlacesList(){
        return new ArrayList<>();
    }
}
