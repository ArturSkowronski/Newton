package com.hiddencity.games.rest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface JoinTeam {

    @POST("/joinTeam")
    void joinTeam(@Body TeamJoinRequest teamJoinRequest, Callback<TeamJoinResponse> callback);


}
