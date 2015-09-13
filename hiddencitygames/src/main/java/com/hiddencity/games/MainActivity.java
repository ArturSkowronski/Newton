package com.hiddencity.games;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.hiddencity.games.gcm.Preferences;
import com.hiddencity.games.gcm.RegistrationIntentService;
import com.hiddencity.games.screens.NavigationActivity;
import com.hiddencity.games.screens.OnBoardActivity;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

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

        if (hiddenSharedPreferences.isPlayerLogged()) {
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
