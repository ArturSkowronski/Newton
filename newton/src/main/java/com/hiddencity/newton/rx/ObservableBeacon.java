package com.hiddencity.newton.rx;

import com.hiddencity.newton.domain.BeaconEvent;

import rx.Observable;

/**
 * Created by arturskowronski on 04/09/15.
 */
public interface ObservableBeacon {
    void onBeaconInitialized(Observable<BeaconEvent> observable);
}
