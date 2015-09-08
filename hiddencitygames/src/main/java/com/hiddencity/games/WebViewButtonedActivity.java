package com.hiddencity.games;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewButtonedActivity extends Activity {

    String nextAction;

    @Bind(R.id.content)
    WebView mWebView;

    public static final void goThere(Context context, String url, String clazz){
        Intent intent = new Intent(context, WebViewButtonedActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("action", clazz);
        context.startActivity(intent);
    };

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

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_web_view_buttoned);

        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        String url = "http://hidden-city.herokuapp.com" + b.getString("id");
        nextAction = b.getString("action");

        Log.i("url", url);
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

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
}
