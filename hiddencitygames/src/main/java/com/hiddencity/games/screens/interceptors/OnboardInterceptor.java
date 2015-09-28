package com.hiddencity.games.screens.interceptors;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.hiddencity.games.screens.NavigationActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class OnboardInterceptor {

        Context mContext;

        /** Instantiate the interface and set the context */
        public OnboardInterceptor(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void gameAction(String toast) {
            Intent intent = new Intent(mContext, NavigationActivity.class);
            mContext.startActivity(intent);
        }

}
