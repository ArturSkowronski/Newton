package com.hiddencity.games.audio;



public abstract class AudioHandler implements AudioInterface {

    protected AudioCallback callback;

    public AudioHandler(AudioCallback callback){
            this.callback = callback;
    }

}
