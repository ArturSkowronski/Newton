package com.hiddencity.games.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hiddencity.games.rest.uri.OnboardURL;
import com.hiddencity.games.screens.NavigationActivity;
import com.hiddencity.games.screens.OnboardWebViewActivity;
import com.hiddencity.games.screens.WebViewButtonedActivity;
import com.hiddencity.games.Preferences;

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
        OnboardWebViewActivity.goThere(context, new OnboardURL(player_id));
    }
}
