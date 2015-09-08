package com.hiddencity.games.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface View {
    @GET("/notification/{device}")
    void registerTeam(@Path("device") String beacon, @Query("device") String deviceId, Callback<TeamEntity> callback);
}
