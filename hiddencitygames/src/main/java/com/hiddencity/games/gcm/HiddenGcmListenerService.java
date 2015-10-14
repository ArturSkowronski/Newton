package com.hiddencity.games.gcm;

/**
 * Created by arturskowronski on 05/09/15.
 */

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.hiddencity.games.gcm.listeners.ResultMessage;
import com.hiddencity.games.gcm.listeners.GCMProcessor;
import com.hiddencity.games.gcm.listeners.AchievmentsMessage;
import com.hiddencity.games.gcm.listeners.ResyncMapMessage;

import java.util.ArrayList;
import java.util.List;

public class HiddenGcmListenerService extends GcmListenerService {

    private static final String TAG = "HiddenGcmListenerService";
    List<GCMProcessor> gcmProcessors = new ArrayList<>();

    @Override
    public void onMessageReceived(String from, Bundle data) {

        gcmProcessors.add(new ResyncMapMessage(this));
        gcmProcessors.add(new AchievmentsMessage(this));
        gcmProcessors.add(new ResultMessage(this));

        for (GCMProcessor gcmProcessor : gcmProcessors) {
            gcmProcessor.process(from, data);
        }


    }
}