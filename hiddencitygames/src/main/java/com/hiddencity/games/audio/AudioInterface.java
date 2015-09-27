package com.hiddencity.games.audio;

public interface AudioInterface {


    public void stop();

    public void seekTo(int to);

    public void start(String aud, int seek);

    public void play();

    public void pause();

    public long getCurrentPosition();

    public long getDuration(String file);

}
