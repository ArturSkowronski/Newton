package com.hiddencity.games.gcm.listeners;

import android.content.Context;
import android.os.Bundle;

import com.hiddencity.games.HiddenNotification;
import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.Log;

/**
 * Created by arturskowronski on 19/10/15.
 */
public class NotifMessage implements GCMProcessor {
    Context context;

    public NotifMessage(Context context) {
        this.context = context;
    }

    @Override
    public void process(String from, Bundle data) {

        String message = data.getString("status");

        Log.i("GCM MSG", "Message Received " + message);

        if(message.equals("notif")) {
            String contentId = data.getString("contentId");
            String title = data.getString("title");
            HiddenNotification hiddenNotification = new HiddenNotification();
            hiddenNotification.sendBeaconNotification(context, contentId, new HiddenSharedPreferences(context).getPlayerId(), title);
        }
    }
}
