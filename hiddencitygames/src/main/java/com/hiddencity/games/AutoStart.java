package com.hiddencity.games;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by arturskowronski on 27/06/15.
 */
public class AutoStart extends BroadcastReceiver
{

    public void onReceive(Context context, Intent intent)
    {
        Log.i("HiddenAutoStart", "ON");

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
        {
            Intent serviceIntent = new Intent(context, ContentService.class);
            context.startService(serviceIntent);
        }
    }
}