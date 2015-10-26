package com.hiddencity.newton.eddystone;


import android.util.Log;

import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.domain.ContentID;
import com.hiddencity.newton.rx.RxBus;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.eddystone.EddystoneDeviceEvent;
import com.kontakt.sdk.android.ble.manager.ProximityManager;

/**
 * Created by arturskowronski on 04/09/15.
 */
public class EddystoneProximityListener implements ProximityManager.ProximityListener {

    public static final String TAG = "Newton";

    public RxBus getRxBus() {
        return _rxBus;
    }

    private RxBus _rxBus;

    public EddystoneProximityListener() {
        _rxBus = new RxBus();
    }

    @Override
    public void onScanStart() {
        Log.d(TAG, "onScanStart");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "onScanStop");
    }

    @Override
    public void onEvent(BluetoothDeviceEvent bluetoothDeviceEvent) {
        Log.d(TAG, "onEvent");
        EddystoneDeviceEvent eddystoneEvent = (EddystoneDeviceEvent) bluetoothDeviceEvent;

        switch (eddystoneEvent.getEventType()) {

            case SPACE_ENTERED:
                Log.i(TAG, "SPACE_ENTERED");
                break;

            case DEVICE_DISCOVERED:

                String name = eddystoneEvent.getDeviceList().get(0).getInstanceId();
                Log.i(TAG, "DEVICE_DISCOVERED: " + name);

                if(name!=null){
                    ContentID contentID1 = new ContentID();
                    contentID1.setBeaconName(name);
                    _rxBus.send(new BeaconEvent(contentID1));
                }
                break;

            case SPACE_ABANDONED:
                Log.i(TAG, "SPACE_ABANDONED");
                break;

            case DEVICES_UPDATE:
                Log.i(TAG, "DEVICES_UPDATE");
                break;

            case DEVICE_LOST:
                Log.i(TAG, "DEVICE_LOST");
                break;


            default:
                throw new IllegalStateException("This event should never occur because it is not specified in ScanContext: " + eddystoneEvent.getEventType().name());
        }
    }


}
