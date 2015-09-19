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

    public HiddenSharedPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setPlayerId(String player_id){
        sharedPreferences.edit().putString(Preferences.PLAYER_ID, player_id).apply();
    }


    public String getPlayerId(){
        return sharedPreferences.getString(Preferences.PLAYER_ID, "");
    }


    public String getGCMToken() {
        return sharedPreferences.getString(Preferences.GCM_TOKEN, "");
    }


    public boolean isPlayerLogged() {
        return !"".equals(getPlayerId());
    }

    public void setGCMToken(String GCMToken) {
        sharedPreferences.edit().putString(Preferences.GCM_TOKEN, GCMToken).apply();
    }
}
