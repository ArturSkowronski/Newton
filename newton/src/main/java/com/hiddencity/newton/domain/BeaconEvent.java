package com.hiddencity.newton.domain;

/**
 * Created by arturskowronski on 04/09/15.
 */
public class BeaconEvent
{
    public ContentID getContentID() {
        return contentID;
    }

    private ContentID contentID;

    public BeaconEvent(ContentID contentID) {

        this.contentID = contentID;
    }
}
