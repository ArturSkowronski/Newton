package com.hiddencity.games.rest.uri;


/**
 * Created by arturskowronski on 14/09/15.
 */
public class ContentURL implements HiddenURL {

    private final String playerId;
    private final String contentId;

    public ContentURL(String contentId, String playerId) {
        this.contentId = contentId;
        this.playerId = playerId;
    }


    @Override
    public String getUrl() {
        return "/task/" + contentId + "/"+ playerId;
    }

}
