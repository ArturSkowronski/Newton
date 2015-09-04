package com.hiddencity.newton.eddystone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import com.hiddencity.newton.rx.ObservableBeacon;
import com.kontakt.sdk.android.BuildConfig;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.service.ProximityService;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.interfaces.SDKSupplier;
import com.kontakt.sdk.android.common.log.LogLevel;
import com.kontakt.sdk.android.common.log.Logger;

/**
 * Created by arturskowronski on 04/09/15.
 */
public class EddystoneBeaconManager {
    String TAG = "Newton";

    private ProximityManager beaconManger;

    public EddystoneBeaconManager(final Context context) {
        Log.i(TAG, "Beacon Initialized");

        KontaktSDK.initialize(context)
                .setDebugLoggingEnabled(BuildConfig.DEBUG)
                .setLogLevelEnabled(LogLevel.VERBOSE, true)
                .setCrashlyticsLoggingEnabled(false);

        beaconManger = new ProximityManager(context);

    }

    public void startMonitoring(final ObservableBeacon observableBeacon) {
        ScanContext scanContext = new ScanContext.Builder()
                .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                .setActivityCheckConfiguration(ActivityCheckConfiguration.DEFAULT)
                .setForceScanConfiguration(ForceScanConfiguration.DEFAULT)
                .setEddystoneScanContext(new EddystoneScanContext.Builder().build())
                .build();

        Log.i(TAG, "startMonitoring");

        beaconManger.initializeScan(scanContext, new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                Log.i(TAG, "onServiceReady");

                EddystoneProximityListener eddystoneProximityListener = new EddystoneProximityListener();
                beaconManger.attachListener(eddystoneProximityListener);
                observableBeacon.onBeaconInitialized(eddystoneProximityListener.getRxBus().toObserverable());
            }

            @Override
            public void onConnectionFailure() {
                Log.e(TAG, "onConnectionFailure");
            }
        });
    }

}
