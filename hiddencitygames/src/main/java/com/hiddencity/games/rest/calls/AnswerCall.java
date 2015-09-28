package com.hiddencity.games.rest.calls;

/**
 * Created by arturskowronski on 27/09/15.
 */

import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.AnswerResponse;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

public interface AnswerCall {

    @POST("/answer/{playerId}/{answerId}")
    void postAnswer(@Path("playerId") String playerId, @Path("answerId") String answerId, Callback<AnswerResponse> callback);

}
