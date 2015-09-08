package com.hiddencity.games.rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hiddencity.games.NavigationActivity;
import com.hiddencity.games.PlayerScreen;
import com.hiddencity.games.WebViewActivity;
import com.hiddencity.games.WebViewButtonedActivity;
import com.hiddencity.games.gcm.Preferences;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arturskowronski on 27/06/15.
 */
public class Onboarding {
    Context context;

    public Onboarding(Context context) {
        this.context = context;
    }

    public void startOnboarding(TeamJoinResponse teamJoinResponse) {
        startOnboarding(teamJoinResponse.getPlayer_id());
    }

    public void startOnboarding(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String player_id = sharedPreferences.getString(Preferences.PLAYER_ID, "");
        startOnboarding(player_id);
    }

    public void startOnboarding(String player_id) {
        WebViewButtonedActivity.goThere(context, "/onboarding?player_id=" + player_id, NavigationActivity.class.getName());
    }
}
