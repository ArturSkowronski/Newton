package com.hiddencity.games.rest.uri;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class OnboardURL implements HiddenURL {

    public OnboardURL(String playerId) {
        this.playerId = playerId;
    }

    String playerId;

    @Override
    public String getUrl() {
        return "/onboarding?player_id=" + playerId;
    }

}
