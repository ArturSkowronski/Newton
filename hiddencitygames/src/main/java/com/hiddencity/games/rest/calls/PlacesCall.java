package com.hiddencity.games.rest.calls;

import com.hiddencity.games.rest.BeaconizedMarker;
import com.hiddencity.games.rest.PlaceBeaconResponse;
import com.hiddencity.games.rest.PlaceResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface PlacesCall {

    @GET("/places")
    void places(Callback<List<BeaconizedMarker>> callback);

    @GET("/place/{beacon}")
    void placeByBeacon(@Path("beacon") String beacon, @Query("player_id") String playerId, Callback<PlaceBeaconResponse> callback);
}
