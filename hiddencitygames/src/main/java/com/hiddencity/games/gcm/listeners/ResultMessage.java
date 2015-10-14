package com.hiddencity.games.gcm.listeners;

import android.content.Context;
import android.os.Bundle;

import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.rest.uri.ResultURL;
import com.hiddencity.games.screens.ResultWebViewActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class ResultMessage implements GCMProcessor {

    Context context;

    public ResultMessage(Context context) {
        this.context = context;
    }

    @Override
    public void process(String from, Bundle data) {
        String message = data.getString("status");

        if(message.equals("done")) {
            HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(context);
            ResultWebViewActivity.goThere(context, new ResultURL(hiddenSharedPreferences.getPlayerId()).getUrl());
        }

    }
}
