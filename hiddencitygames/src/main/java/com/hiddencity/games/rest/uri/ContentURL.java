package com.hiddencity.games.rest.uri;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class ContentURL implements HiddenURL {

    public ContentURL(String contentId) {
        this.contentId = contentId;
    }

    String contentId;

    @Override
    public String getUrl() {
        return "/content/" + contentId;
    }

}
