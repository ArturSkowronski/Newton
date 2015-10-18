package com.hiddencity.games.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.os.Handler;
import com.hiddencity.games.Log;

public class MediaPlayerAudioHandler extends AudioHandler implements OnCompletionListener, OnPreparedListener, OnErrorListener {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private final Handler handler = new Handler();

    public MediaPlayerAudioHandler(AudioCallback iAudio){
        super(iAudio);
    }

    public void start(String file , int seek) {
        if (isPlaying==false) {
            try {
                mPlayer = new MediaPlayer();
                isPlaying=true;
                Log.d("Audio startPlaying", "audio: " + file);
                if (isStreaming(file))
                {
                    Log.d("AudioStartPlaying", "Streaming");
                    mPlayer.setDataSource(file);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.prepareAsync();
                } else {
                    Log.d("AudioStartPlaying", "File");
                    // Not streaming prepare synchronous, abstract base directory
                    mPlayer.setDataSource("/sdcard/" + file);
                    mPlayer.prepare();
                }
                mPlayer.setOnPreparedListener(this);
                mPlayer.seekTo(seek);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void play() {
        if(! isPlaying ){

         this.mPlayer.start();
            isPlaying = true;
            startPlayProgressUpdater();
        }

    }
    @Override
    public void pause() {
        if(isPlaying ){

         this.mPlayer.pause();
            isPlaying= false;
        }

    }

    public void stop() {
        if (isPlaying) {
            mPlayer.stop();
            mPlayer.release();
            isPlaying=false;
        }
    }

    public void onCompletion(MediaPlayer mPlayer) {
        mPlayer.stop();
        mPlayer.release();
        callback.onFinished();
        isPlaying=false;
    }

    public long getCurrentPosition() {
        if (isPlaying)
        {   try {
                return (mPlayer.getCurrentPosition());
            }catch (Exception e ){
                return (-1);
            }
        } else { return(-1); }
    }

    private boolean isStreaming(String file)
    {
        if (file.contains("http://") || file.contains("https://")) {
            return true;
        } else {
            return false;
        }
    }

    public long getDuration(String file) {
        long duration = -2;
        if (!isPlaying & !isStreaming(file)) {
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource("/sdcard/" + file);
                mPlayer.prepare();
                duration = mPlayer.getDuration();
                mPlayer.release();
            } catch (Exception e) { e.printStackTrace(); return(-3); }
        } else
        if (isPlaying & !isStreaming(file)) {
            duration = mPlayer.getDuration();
        } else
        if (isPlaying & isStreaming(file)) {
            try {
                duration = mPlayer.getDuration();
            } catch (Exception e) { e.printStackTrace(); return(-4); }
        }else { return -1; }
        return duration;
    }

    public void onPrepared(MediaPlayer mPlayer) {
        if (isPlaying) {
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mPlayer, int percent) {
                    Log.d("AudioOnBufferingUpdate", "percent: " + percent);
                    callback.onBufferChange(percent);
                }
            });
            callback.onStart(mPlayer.getDuration());
            mPlayer.start();
            startPlayProgressUpdater();
        }
    }

    public void startPlayProgressUpdater() {
        if (isPlaying) {
            Runnable notification = new Runnable() {
                public void run() {
                    if(isPlaying) {
                        callback.onProgress(getCurrentPosition());
                        startPlayProgressUpdater();
                    }
                }
            };
            handler.postDelayed(notification, 100);
        }
    }

    public boolean onError(MediaPlayer mPlayer, int arg1, int arg2) {
        Log.e("AUDIO onError", "error " + arg1 + " " + arg2);
        return false;
    }

    public void seekTo(int mill){

        mPlayer.seekTo(mill);
    }

}
