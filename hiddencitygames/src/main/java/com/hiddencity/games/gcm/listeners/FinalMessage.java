package com.hiddencity.games.gcm.listeners;

import android.content.Context;
import android.os.Bundle;

import com.hiddencity.games.HiddenNotification;
import com.hiddencity.games.screens.NavigationActivity;
import com.hiddencity.games.screens.WebViewActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class FinalMessage implements GCMProcessor {

    Context context;

    public FinalMessage(Context context) {
        this.context = context;
    }

    @Override
    public void process(String from, Bundle data) {
        String message = data.getString("status");

        HiddenNotification hiddenNotification = new HiddenNotification();

        if(message.equals("done")) {
            String url = data.getString("url");
            WebViewActivity.goThere(context, url);
        }

    }
}
