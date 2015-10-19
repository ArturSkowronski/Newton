package com.hiddencity.games.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.hiddencity.games.R;
import com.hiddencity.games.audio.AudioJsInterface;
import com.hiddencity.games.rest.ActiveBeaconResponse;
import com.hiddencity.games.rest.calls.ActiveBeaconCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class ContentWebViewActivity extends Activity {

    @Bind(R.id.content)
    WebView mWebView;

    private  AudioJsInterface js;
    String TAG = "";

    public static void goThere(Context context, String url){
        Intent intent = new Intent(context, ContentWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    private void callForActiveBeacon(final String url, final String contentId) {
        ActiveBeaconCall activeBeacon;
        String backendEndpoint = getResources().getString(R.string.backend_endpoint);

        HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(ContentWebViewActivity.this);

        activeBeacon = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .setLog(new AndroidLog(TAG))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ActiveBeaconCall.class);
        js = new AudioJsInterface(ContentWebViewActivity.this, mWebView);

        activeBeacon.getActiveBeacon(hiddenSharedPreferences.getPlayerId(), new Callback<ActiveBeaconResponse>() {
            @Override
            public void success(ActiveBeaconResponse activeBeaconResponse, Response response) {
                if(contentId.equals(activeBeaconResponse.get_id())) {
                    mWebView.loadUrl(url);
                    WebSettings webSettings = mWebView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    mWebView.setVerticalScrollBarEnabled(false);
                    mWebView.setHorizontalScrollBarEnabled(false);
                    mWebView.addJavascriptInterface(js, "AndroidAudio");
                } else {
                    Toast.makeText(ContentWebViewActivity.this, "To zadanie już zostało wykonane", Toast.LENGTH_SHORT).show();
                    NavigationActivity.goThere(ContentWebViewActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ContentWebViewActivity.this, "To zadanie już zostało wykonane", Toast.LENGTH_SHORT).show();
                NavigationActivity.goThere(ContentWebViewActivity.this);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_web_view);

        String backendEndpoint = getResources().getString(R.string.backend_endpoint);

        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        String url = backendEndpoint + b.getString("url");
        String contentId = b.getString("contentId");
        Log.i("url", url);

        callForActiveBeacon(url, contentId);
    }

    @Override
    protected void onPause() {
        js.pauseByDevice();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        js.stop();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        js.resume();
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }
    }


    @Override
    public void onBackPressed() {
    }

}
