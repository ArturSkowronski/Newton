package com.hiddencity.games;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class HiddenSharedPreferences {

    public static final String TAG = "HiddenCityGames";

    Context context;
    SharedPreferences sharedPreferences;

    public String getCode() {
        return sharedPreferences.getString(Preferences.GAME_CODE, "");
    }

    public void setCode(String code) {
        sharedPreferences.edit().putString(Preferences.GAME_CODE, code).apply();
    }

    public void setPlacesDownloaded(boolean flag) {
        sharedPreferences.edit().putBoolean(Preferences.PLACE_DOWNLOADED, flag).apply();
    }

    public boolean arePlacesDownloaded() {
        return sharedPreferences.getBoolean(Preferences.PLACE_DOWNLOADED, false);
    }


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

    public void clearAllProperties() {
        sharedPreferences.edit().remove(Preferences.PLACE_DOWNLOADED).apply();
        sharedPreferences.edit().remove(Preferences.GAME_CODE).apply();
        sharedPreferences.edit().remove(Preferences.PLAYER_ID).apply();


    }

    public void clearDataProperties() {
        sharedPreferences.edit().remove(Preferences.PLACE_DOWNLOADED).apply();
        sharedPreferences.edit().remove(Preferences.GAME_CODE).apply();
        sharedPreferences.edit().remove(Preferences.PLAYER_ID).apply();


    }
}
