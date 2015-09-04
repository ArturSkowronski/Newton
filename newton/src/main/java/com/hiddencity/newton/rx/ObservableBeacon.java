package com.hiddencity.newton.rx;

import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.domain.ContentID;

import rx.Observable;

/**
 * Created by arturskowronski on 04/09/15.
 */
public interface ObservableBeacon {

    public void onBeaconInitialized(Observable<BeaconEvent> observable);


}
