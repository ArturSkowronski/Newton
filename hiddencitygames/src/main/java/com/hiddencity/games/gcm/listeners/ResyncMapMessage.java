package com.hiddencity.games.gcm.listeners;

import android.content.Context;
import android.os.Bundle;

import com.hiddencity.games.HiddenNotification;
import com.hiddencity.games.screens.NavigationActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class ResyncMapMessage implements GCMProcessor {

    public ResyncMapMessage(Context context) {
        this.context = context;
    }

    Context context;

    @Override
    public void process(String from, Bundle data) {
        String message = data.getString("status");

        HiddenNotification hiddenNotification = new HiddenNotification();

        if(message.equals("next")) {
            String nextContent = data.getString("nextContent");
            NavigationActivity.goThere(context, true);
        }
    }
}
