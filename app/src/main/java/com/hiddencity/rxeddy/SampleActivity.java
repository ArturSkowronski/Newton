package com.hiddencity.rxeddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.domain.ContentID;
import com.hiddencity.newton.eddystone.EddystoneBeaconManager;
import com.hiddencity.newton.rx.ObservableBeacon;

import rx.Observable;
import rx.functions.Action1;


public class SampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_sample);

        EddystoneBeaconManager eddystoneBeaconManager = new EddystoneBeaconManager(this);

        eddystoneBeaconManager.startMonitoring(new ObservableBeacon() {
            @Override
            public void onBeaconInitialized(Observable<BeaconEvent> observable) {
                observable.subscribe(onNext);
            }
        });

    }

    Action1<BeaconEvent> onNext = new Action1<BeaconEvent>() {
        @Override
        public void call(BeaconEvent beaconEvent) {
            Log.d("Newton", beaconEvent.getContentID().getBeaconName());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
