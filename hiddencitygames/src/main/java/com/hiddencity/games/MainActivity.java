package com.hiddencity.games;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hiddencity.games.gcm.Preferences;
import com.hiddencity.games.gcm.RegistrationIntentService;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import io.fabric.sdk.android.Fabric;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivityToken";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    GoogleServicesAccessor googleServicesAccessor = new GoogleServicesAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = initializeBroadcastReceiver();
        HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(this);

        if (googleServicesAccessor.checkPlayServices(this)) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        if(!hiddenSharedPreferences.getPlayerId().equals("")){
            Intent intent = new Intent(this, NavigationActivity.class);
            this.startActivity(intent);
        } else {
            Intent intent = new Intent(this, OnBoardActivity.class);
            this.startActivity(intent);
        }
    }

    private BroadcastReceiver initializeBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(Preferences.SENT_TOKEN_TO_SERVER, false);

                if (sentToken) {
                    Log.i(TAG, "TokenSend.");
                } else {
                    Log.i(TAG, "TokenNotSend.");
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Preferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
