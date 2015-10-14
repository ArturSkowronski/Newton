package com.hiddencity.games.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hiddencity.games.R;
import com.hiddencity.games.screens.interceptors.ResultInterceptor;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultWebViewActivity extends Activity {

    @Bind(R.id.content)
    WebView mWebView;

    ResultInterceptor resultInterceptor;

    public static final void goThere(Context context, String url){
        Intent intent = new Intent(context, ResultWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_web_view);

        String backendEndpoint = getResources().getString(R.string.backend_endpoint);

        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        String url = backendEndpoint + b.getString("id");

        Log.i("url", url);
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        resultInterceptor = new ResultInterceptor(this);
        mWebView.addJavascriptInterface(resultInterceptor, "Android");
        mWebView.setHorizontalScrollBarEnabled(false);
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
