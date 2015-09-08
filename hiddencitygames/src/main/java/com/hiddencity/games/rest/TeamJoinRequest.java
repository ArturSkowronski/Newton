package com.hiddencity.games.rest;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class TeamJoinRequest {
    public String getGcm() {
        return gcm;
    }

    public void setGcm(String gcm) {
        this.gcm = gcm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String gcm;
    String code;
}
