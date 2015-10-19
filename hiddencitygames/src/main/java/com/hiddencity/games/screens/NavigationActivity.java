package com.hiddencity.games.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.hiddencity.games.BeaconAction;
import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.R;
import com.hiddencity.games.Log;
import com.hiddencity.games.adapters.PlaceEntityAdapter;
import com.hiddencity.games.db.table.PlacesEntity;
import com.hiddencity.games.map.HiddenGoogleMap;
import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.BeaconizedMarker;
import com.hiddencity.games.rest.calls.ActiveBeaconCall;
import com.hiddencity.games.rest.calls.PlacesCall;
import com.hiddencity.newton.domain.BeaconEvent;
import com.hiddencity.newton.domain.ContentID;
import com.hiddencity.newton.eddystone.EddystoneBeaconManager;
import com.hiddencity.newton.rx.ObservableBeacon;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import rx.Observable;
import rx.functions.Action1;


public class NavigationActivity extends AppCompatActivity {

    private String TAG = "NavigationActivity";
    Action1<BeaconEvent> onNext;


    private void simulateBeacon(String p1Beacon) {
        ContentID contentID = new ContentID();
        contentID.setBeaconName(p1Beacon);
        BeaconEvent beaconEvent = new BeaconEvent(contentID);
        onNext.call(beaconEvent);
    }

    @Bind(R.id.menu_item)
    FloatingActionButton menuItem;

    @OnClick(R.id.menu_item)
    public void logout(View v) {
        hiddenSharedPreferences.clearAllProperties();
        final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(NavigationActivity.this);
        placeEntityAdapter.clearDB();
        Intent intent = new Intent(NavigationActivity.this, MainMenuActivity.class);
        NavigationActivity.this.startActivity(intent);
        Toast.makeText(NavigationActivity.this, "Zostałeś pomyślnie wylogowany", Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.simulate_beacon_1)
    public void sim1(View v) {
        simulateBeacon("434c776a544a");
    }

    @OnClick(R.id.simulate_beacon_2)
    public void sim2(View v) {
        simulateBeacon("423067396a55");
    }

    @OnClick(R.id.simulate_beacon_3)
    public void sim3(View v) {
        simulateBeacon("69506c446155");
    }

    @OnClick(R.id.simulate_beacon_4)
    public void sim4(View v) {
        simulateBeacon("46343159444d");
    }

    @OnClick(R.id.simulate_beacon_5)
    public void sim5(View v) {
        simulateBeacon("6d6c6c6d5343");
    }

    @OnClick(R.id.simulate_beacon_6)
    public void sim6(View v) {
        simulateBeacon("4f33447a5962");
    }

    @OnClick(R.id.simulate_beacon_7)
    public void sim7(View v) {
        simulateBeacon("376c7166774d");
    }

    @OnClick(R.id.simulate_beacon_8)
    public void sim8(View v) {
        simulateBeacon("4d646948466f");
    }

    @OnClick(R.id.simulate_beacon_9)
    public void sim9(View v) {
        simulateBeacon("484b334d7374");
    }

    @OnClick(R.id.simulate_beacon_10)
    public void sim10(View v) {
        simulateBeacon("7258764e7837");
    }

    @OnClick(R.id.simulate_beacon_11)
    public void sim11(View v) {
        simulateBeacon("6b4b444e6d74");
    }

    @OnClick(R.id.simulate_beacon_12)
    public void sim12(View v) {
        simulateBeacon("4a3872777255");
    }

    @OnClick(R.id.simulate_beacon_13)
    public void sim13(View v) {
        simulateBeacon("6f6947723331");
    }

    @OnClick(R.id.simulate_beacon_14)
    public void sim14(View v) {
        simulateBeacon("37724f654f37");
    }

    @OnClick(R.id.simulate_beacon_15)
    public void sim15(View v) {
        simulateBeacon("41566e447366");
    }

    HiddenSharedPreferences hiddenSharedPreferences;
    HiddenGoogleMap hiddenGoogleMap;

    PlacesCall places;
    ActiveBeaconCall activeBeacon;

    public static void goThere(Context context) {
        Intent intent = new Intent(context, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goThere(Context context, boolean resync) {
        Intent intent = new Intent(context, NavigationActivity.class);
        intent.putExtra("resync", resync);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());

        menuItem.setColorNormal(Color.parseColor("#4f000000"));
        menuItem.setColorPressed(Color.parseColor("#4f000000"));
        menuItem.setImageDrawable(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));

        EddystoneBeaconManager eddystoneBeaconManager = new EddystoneBeaconManager(this);

        eddystoneBeaconManager.startMonitoring(new ObservableBeacon() {
            @Override
            public void onBeaconInitialized(Observable<BeaconEvent> observable) {
            observable.subscribe(onNext);
            }
        });

        String backendEndpoint = getResources().getString(R.string.backend_endpoint);
        onNext = new BeaconAction(this).get();

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

        final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(this);

        if (hiddenSharedPreferences.arePlacesDownloaded()) {
            List<PlacesEntity> placesEntities = placeEntityAdapter.findAll();
            hiddenGoogleMap.addMarkers(placesEntities);
            callForActiveBeacon(placeEntityAdapter);

        } else {
            places.places(new Callback<List<BeaconizedMarker>>() {

                @Override
                public void success(List<BeaconizedMarker> beaconizedMarkers, Response response) {
                    try {
                        final List<PlacesEntity> placesEntities = placeEntityAdapter.persistAll(beaconizedMarkers);
                        hiddenSharedPreferences.setPlacesDownloaded(true);
                        callForActiveBeacon(placeEntityAdapter);
                    } catch (Exception e) {
                        Realm realm = Realm.getInstance(NavigationActivity.this);
                        realm.commitTransaction();

                        hiddenSharedPreferences.clearDataProperties();

                        Intent intent = new Intent(NavigationActivity.this, MainMenuActivity.class);
                        NavigationActivity.this.startActivity(intent);
                        Log.d(TAG, "EXCEPTION: " + e.getMessage());
                        Toast.makeText(NavigationActivity.this, "Wystąpił problem. Dołącz jeszcze raz: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
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


    private void GoogleMap() {
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        hiddenGoogleMap = new HiddenGoogleMap(this, map);
    }


    private void TranslucantStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
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
