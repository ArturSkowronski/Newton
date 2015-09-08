package com.hiddencity.games;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hiddencity.games.gcm.Preferences;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class HiddenSharedPreferences {

    public static final String TAG = "HiddenCityGames";
    Context context;
    SharedPreferences sharedPreferences;
    private String GCMToken;

    public HiddenSharedPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setPlayerId(String player_id){
        sharedPreferences.edit().putString(Preferences.PLAYER_ID, player_id).apply();
    }


    public String getPlayerId(){
        String string = sharedPreferences.getString(Preferences.GCM_TOKEN, "");
        return string;
    }


    public String getGCMToken() {
        String string = sharedPreferences.getString(Preferences.GCM_TOKEN, "");
        return string;
    }

    public void setGCMToken(String GCMToken) {
        sharedPreferences.edit().putString(Preferences.GCM_TOKEN, GCMToken).apply();

    }
}
