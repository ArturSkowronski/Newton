package com.hiddencity.games.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.hiddencity.games.adapters.BeaconEntityAdapter;
import com.hiddencity.games.db.table.BeaconEntity;
import com.hiddencity.games.db.table.PlacesEntity;
import com.hiddencity.games.map.HiddenGoogleMap;
import com.hiddencity.games.map.HiddenInfoAdapter;
import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.R;
import com.hiddencity.games.adapters.PlaceEntityAdapter;
import com.hiddencity.games.rest.calls.ActiveBeaconCall;
import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.BeaconizedMarker;
import com.hiddencity.games.rest.calls.PlacesCall;
import com.hiddencity.games.rest.uri.ContentURL;
import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.domain.ContentID;
import com.hiddencity.newton.eddystone.EddystoneBeaconManager;
import com.hiddencity.newton.rx.ObservableBeacon;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import rx.Observable;
import rx.functions.Action1;


public class NavigationActivity extends AppCompatActivity {

    private String TAG = "NavigationActivity";

    @OnClick(R.id.simulate_beacon_1)
    public void sim1(View v){
        ContentID contentID = new ContentID();
        contentID.setBeaconName("1");
        BeaconEvent beaconEvent = new BeaconEvent(contentID);
        onNext.call(beaconEvent);
    }

    @OnClick(R.id.simulate_beacon_2)
    public void sim2(View v){
        ContentID contentID = new ContentID();
        contentID.setBeaconName("BEACON_2");
        BeaconEvent beaconEvent = new BeaconEvent(contentID);
        onNext.call(beaconEvent);
    }

    @OnClick(R.id.simulate_beacon_3)
    public void sim3(View v){
        ContentID contentID = new ContentID();
        contentID.setBeaconName("BEACON_3");
        BeaconEvent beaconEvent = new BeaconEvent(contentID);
        onNext.call(beaconEvent);
    }

    EddystoneBeaconManager eddystoneBeaconManager;
    HiddenSharedPreferences hiddenSharedPreferences;
    HiddenGoogleMap hiddenGoogleMap;

    PlacesCall places;
    ActiveBeaconCall activeBeacon;

    public static void goThere(Context context){
        Intent intent = new Intent(context, NavigationActivity.class);
        context.startActivity(intent);
    }

    public static void goThere(Context context, boolean resync){
        Intent intent = new Intent(context, NavigationActivity.class);
        intent.putExtra("resync", resync);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        String backendEndpoint = getResources().getString(R.string.backend_endpoint);

        places = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .setLog(new AndroidLog(TAG))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PlacesCall.class);

        activeBeacon = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .setLog(new AndroidLog(TAG))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ActiveBeaconCall.class);


        TranslucantStatusBar();
        GoogleMap();

        hiddenSharedPreferences = new HiddenSharedPreferences(NavigationActivity.this);
        eddystoneBeaconManager = new EddystoneBeaconManager(this);

        beaconNavigation();
        final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(this);

        if(hiddenSharedPreferences.arePlacesDownloaded()){
            List<PlacesEntity> placesEntities = placeEntityAdapter.findAll();
            hiddenGoogleMap.addMarkers(placesEntities);
            callForActiveBeacon(placeEntityAdapter);

        } else {
            places.places(new Callback<List<BeaconizedMarker>>() {

                @Override
                public void success(List<BeaconizedMarker> beaconizedMarkers, Response response) {
                    final List<PlacesEntity> placesEntities = placeEntityAdapter.persistAll(beaconizedMarkers);
                    hiddenSharedPreferences.setPlacesDownloaded(true);
                    callForActiveBeacon(placeEntityAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getResponse().getReason());
                }
            });
        }
    }

    private void callForActiveBeacon(final PlaceEntityAdapter placeEntityAdapter) {

        activeBeacon.getActiveBeacon(hiddenSharedPreferences.getPlayerId(), new Callback<ActiveBeaconResponse>() {
            @Override
            public void success(ActiveBeaconResponse activeBeaconResponse, Response response) {
                final List<PlacesEntity> placesEntities = placeEntityAdapter.findAll();
                placeEntityAdapter.activateBeacon(activeBeaconResponse);
                hiddenGoogleMap.addMarkers(placesEntities);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getResponse().getReason());
            }
        });
    }

    private void beaconNavigation() {

        eddystoneBeaconManager.startMonitoring(new ObservableBeacon() {
            @Override
            public void onBeaconInitialized(Observable<BeaconEvent> observable) {
            observable.subscribe(onNext);
            }
        });
    }


    Action1<BeaconEvent> onNext = new Action1<BeaconEvent>() {
        @Override
        public void call(final BeaconEvent beaconEvent) {
            final BeaconEntityAdapter beaconEntityAdapter = new BeaconEntityAdapter(NavigationActivity.this);
            final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(NavigationActivity.this);

            BeaconEntity beaconEntity = beaconEntityAdapter.findByName(beaconEvent.getContentID().getBeaconName());

            if(beaconEntity == null) {
                Log.e(HiddenSharedPreferences.TAG, "Beacon " + beaconEvent.getContentID().getBeaconName() + " not in backend");
                return;
            }

            PlacesEntity placesEntity = placeEntityAdapter.findByName(beaconEntity.getPlaceId());

            if(!placesEntity.isActive()) {
                Log.e(HiddenSharedPreferences.TAG, "Beacon " + beaconEvent.getContentID().getBeaconName() + " not active");
                return;
            }

            String contentId = beaconEntity.getContent();

            if(contentId == null) {
                Log.e(HiddenSharedPreferences.TAG, "Beacon Content" + beaconEvent.getContentID().getBeaconName() + " not in backend");
                return;
            }
            Log.e(HiddenSharedPreferences.TAG, "Beacon Content GO!");

            WebViewActivity.goThere(NavigationActivity.this, new ContentURL(contentId, hiddenSharedPreferences.getPlayerId()).getUrl());

        }
    };


    private void GoogleMap() {
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        hiddenGoogleMap = new HiddenGoogleMap(this, map);
        hiddenGoogleMap.setInfoWindowAdapter(new HiddenInfoAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
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

    private void TranslucantStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); w.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#10000000"));
    }


    @Override
    public void onBackPressed() {
    }
}
