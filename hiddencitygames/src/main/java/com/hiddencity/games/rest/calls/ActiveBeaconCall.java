package com.hiddencity.games.rest.calls;

/**
 * Created by arturskowronski on 27/09/15.
 */

import com.hiddencity.games.rest.ActiveBeaconResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ActiveBeaconCall {
    @GET("/active/{playerId}")
    void getActiveBeacon(@Path("playerId") String playerId, Callback<ActiveBeaconResponse> callback);
}
