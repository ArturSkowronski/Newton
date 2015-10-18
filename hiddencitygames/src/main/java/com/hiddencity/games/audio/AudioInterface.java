package com.hiddencity.games.audio;

public interface AudioInterface {


    public void stop();

    public void seekTo(int to);

    public void start(String aud, int seek);

    public void play();

    public void resume();

    public void pause();

    public void pauseByDevice();

    public long getCurrentPosition();

    public long getDuration(String file);

}
