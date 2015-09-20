package com.hiddencity.games.rest.uri;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class ResultURL implements HiddenURL {

    public ResultURL(String contentId) {
        this.contentId = contentId;
    }

    String contentId;

    @Override
    public String getUrl() {
        return "/content/" + contentId;
    }

}
