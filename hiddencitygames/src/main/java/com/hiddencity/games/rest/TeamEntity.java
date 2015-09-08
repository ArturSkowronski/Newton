package com.hiddencity.games.rest;

/**
 * Created by arturskowronski on 27/06/15.
 */
import com.google.gson.annotations.Expose;

public class TeamEntity {

    @Expose
    private String title;

    @Expose
    private String _id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "title='" + title + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}