package com.hiddencity.games.rest.uri;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class AchievementURL implements HiddenURL {

    public AchievementURL(String playerId) {
        this.playerId = playerId;
    }

    String playerId;

    @Override
    public String getUrl() {
        return "/achievements/" + playerId;
    }

}
