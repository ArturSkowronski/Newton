package com.hiddencity.games.screens.interceptors;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.hiddencity.games.rest.calls.AnswerCall;
import com.hiddencity.games.screens.NavigationActivity;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class AchievementInterceptor {

    private String TAG = "ContentInterceptor";


    Context mContext;
    AnswerCall answerCall;

    /**
     * Instantiate the interface and set the context
     */
    public AchievementInterceptor(Context c) {

        mContext = c;
    }

    @JavascriptInterface
    public void gameAction(String action) {
        NavigationActivity.goThere(mContext);
    }

}
