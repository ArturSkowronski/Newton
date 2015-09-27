package com.hiddencity.games.audio;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class AudioJsInterface implements AudioCallback {
    Context context;
    WebView webView;
    AudioHandler audiHandler;



    public AudioJsInterface(Context c, WebView w) {
        this.context = c;
        this.webView = w;
        this.audiHandler = new MediaPlayerAudioHandler(this) ;
    }
    @JavascriptInterface
    public void stop(){
        audiHandler.stop();
    }


    @JavascriptInterface
    public void seekTo(int to){
        audiHandler.seekTo(to);
    }

    @JavascriptInterface
    public void start(String aud , int seek) {
        audiHandler.start(aud, seek);
    }

    @JavascriptInterface
    public void play() {
        audiHandler.play();
    }
    @JavascriptInterface
    public void pause() {
        audiHandler.pause();
    }

    @Override
    public void onStart(long duration) {
       callWebView("onStart('" + duration + "');");

    }

    @Override
    public void onProgress(long position) {
        callWebView("onProgress('" + position + "');");

    }

    @Override
    public void onBufferChange(int percent) {
        callWebView("onBufferChange('" + percent + "');");

    }

    @Override
    public void onFinished() {
        callWebView("onFinished();");

    }

    protected void callWebView(final String url) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:AndroidAudioCallback."+url);
            }
        });
    }
}
