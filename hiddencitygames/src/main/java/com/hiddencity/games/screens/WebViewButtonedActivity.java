package com.hiddencity.games.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.hiddencity.games.R;
import com.hiddencity.games.rest.uri.HiddenURL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewButtonedActivity extends Activity {

    String nextAction;
    public static final String ACTION = "action";
    public static final String URL = "url";

    @Bind(R.id.content)
    WebView mWebView;

    public static void goThere(Context context, HiddenURL url, String clazz){
        Intent intent = new Intent(context, WebViewButtonedActivity.class);
        intent.putExtra(URL, url.getUrl());
        intent.putExtra(ACTION, clazz);
        context.startActivity(intent);
    }

    @OnClick(R.id.nextAction)
    public void next(View view){
        try {
            Class<?> aClass = Class.forName(nextAction);
            Intent intent = new Intent(WebViewButtonedActivity.this, aClass);
            WebViewButtonedActivity.this.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_web_view_buttoned);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        String url = getString(R.string.backend_endpoint) + b.getString(URL);
        nextAction = b.getString(ACTION);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);

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
