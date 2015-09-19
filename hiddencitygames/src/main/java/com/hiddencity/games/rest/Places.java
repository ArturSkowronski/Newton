package com.hiddencity.games.rest;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface Places {

    @GET("/places")
    void places(Callback<List<BeaconizedMarker>> callback);

    @GET("/place/{beacon}")
    void placeByBeacon(@Path("beacon") String beacon, @Query("player_id") String playerId, Callback<PlaceResponse> callback);
}
