package com.hiddencity.games;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.eddystone.EddystoneBeaconManager;
import com.hiddencity.newton.rx.ObservableBeacon;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by arturskowronski on 27/06/15.
 */

public class ContentService extends Service
{
    private static final String TAG = "HiddenCity Beacon Service";
    Action1<BeaconEvent> onNext;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void initializeService() {
        EddystoneBeaconManager eddystoneBeaconManager = new EddystoneBeaconManager(this);
        eddystoneBeaconManager.startMonitoring(new ObservableBeacon() {
            @Override
            public void onBeaconInitialized(Observable<BeaconEvent> observable) {
                observable.subscribe(onNext);
            }
        });
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "HiddenCity Beacon Service", Toast.LENGTH_LONG).show();
        onNext = new BeaconAction(this).get();

        initializeService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initializeService();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {

    }


}