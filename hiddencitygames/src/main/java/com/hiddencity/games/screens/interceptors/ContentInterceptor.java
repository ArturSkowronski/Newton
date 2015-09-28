package com.hiddencity.games.screens.interceptors;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.R;
import com.hiddencity.games.rest.AnswerResponse;
import com.hiddencity.games.rest.calls.AnswerCall;
import com.hiddencity.games.rest.calls.PlacesCall;
import com.hiddencity.games.screens.NavigationActivity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class ContentInterceptor {

    private String TAG = "ContentInterceptor";


    Context mContext;
    AnswerCall answerCall;

    /**
     * Instantiate the interface and set the context
     */
    public ContentInterceptor(Context c) {

        mContext = c;

        String backendEndpoint = mContext.getResources().getString(R.string.backend_endpoint);

        answerCall = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .setLog(new AndroidLog(TAG))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(AnswerCall.class);
    }

    @JavascriptInterface
    public void answerQuestion(String answerId) {
        HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(mContext);
        answerCall.postAnswer(hiddenSharedPreferences.getPlayerId(), answerId, new Callback<AnswerResponse>() {
            @Override
            public void success(AnswerResponse answerResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
