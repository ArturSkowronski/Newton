package com.hiddencity.games.rest;

import android.app.Activity;
import android.widget.Toast;

import com.hiddencity.games.HiddenSharedPreferences;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class TeamJoinResponse {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getPlayer_id() {
        return player_id;
    }

    public static Callback<TeamJoinResponse> callbackResponse(final Activity context){
        Callback<TeamJoinResponse> callback = new Callback<TeamJoinResponse>() {
        final HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(context);

            @Override
            public void success(TeamJoinResponse teamJoinResponse, Response response) {
                Onboarding onboarding = new Onboarding(context);
                hiddenSharedPreferences.setPlayerId(teamJoinResponse.getPlayer_id());
                onboarding.startOnboarding();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "Nie udało się dołączyć", Toast.LENGTH_SHORT);
                context.finish();
            }
        };
        return callback;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    String player_id;


}
