package com.hiddencity.games.rest.calls;

import com.hiddencity.games.rest.AnswerResponse;
import com.hiddencity.games.rest.BeaconizedMarker;
import com.hiddencity.games.rest.PlaceBeaconResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public interface NotificationShowedCall {

    @GET("/notification/{playerId}/{contentId}")
    void showed(@Path("playerId") String playerId, @Path("contentId") String answerId, Callback<AnswerResponse> callback);

}
