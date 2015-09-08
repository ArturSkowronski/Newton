package com.hiddencity.games.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface Places {

    @GET("/places")
    void places(@Query("player_id") String playerId, Callback<PlacesResponse> callback);

    @GET("/place/{beacon}")
    void placeByBeacon(@Path("beacon") String beacon, @Query("player_id") String playerId, Callback<PlaceResponse> callback);
}
