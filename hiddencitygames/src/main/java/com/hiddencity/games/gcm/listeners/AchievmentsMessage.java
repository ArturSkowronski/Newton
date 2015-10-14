package com.hiddencity.games.gcm.listeners;

import android.content.Context;
import android.os.Bundle;

import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.rest.uri.AchievementURL;
import com.hiddencity.games.screens.AchievmentWebViewActivity;
import com.hiddencity.games.screens.NavigationActivity;
import com.hiddencity.games.screens.WebViewButtonedActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class AchievmentsMessage implements GCMProcessor {

    Context context;

    public AchievmentsMessage(Context context) {
        this.context = context;
    }

    @Override
    public void process(String from, Bundle data) {
        String message = data.getString("status");
        if(message.equals("next")) {
            HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(context);
            AchievmentWebViewActivity.goThere(context, new AchievementURL(hiddenSharedPreferences.getPlayerId()).getUrl());
        }
    }
}
