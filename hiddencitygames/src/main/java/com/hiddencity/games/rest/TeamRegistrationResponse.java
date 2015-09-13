package com.hiddencity.games.rest;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class TeamRegistrationResponse {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getRegistrationCode() {
        return registration_code;
    }

    public void setRegistrationCode(String registration_code) {
        this.registration_code = registration_code;
    }

    String registration_code;

    public String getPlayerId() {
        return player_id;
    }

    public void setPlayerId(String player_id) {
        this.player_id = player_id;
    }

    String player_id;
}
