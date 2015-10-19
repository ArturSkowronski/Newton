package com.hiddencity.games.gcm;

/**
 * Created by arturskowronski on 05/09/15.
 */

import android.os.Bundle;
import com.hiddencity.games.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.hiddencity.games.gcm.listeners.NotifMessage;
import com.hiddencity.games.gcm.listeners.ResultMessage;
import com.hiddencity.games.gcm.listeners.GCMProcessor;
import com.hiddencity.games.gcm.listeners.AchievmentsMessage;

import java.util.ArrayList;
import java.util.List;

public class HiddenGcmListenerService extends GcmListenerService {

    private static final String TAG = "HiddenGcmListener";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.i(TAG, "Message Received " + from);
        List<GCMProcessor> gcmProcessors = new ArrayList<>();

//        gcmProcessors.add(new ResyncMapMessage(this));
        gcmProcessors.add(new AchievmentsMessage(this));
        gcmProcessors.add(new ResultMessage(this));
        gcmProcessors.add(new NotifMessage(this));

        for (GCMProcessor gcmProcessor : gcmProcessors) {
            gcmProcessor.process(from, data);
        }


    }
}