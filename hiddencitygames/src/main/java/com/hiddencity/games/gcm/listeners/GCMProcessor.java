package com.hiddencity.games.gcm.listeners;

import android.os.Bundle;

/**
 * Created by arturskowronski on 20/09/15.
 */
public interface GCMProcessor {
    void process(String from, Bundle bundle);
}
