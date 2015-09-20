package com.hiddencity.games.rest;

import java.util.List;

/**
 * Created by arturskowronski on 14/09/15.
 */
public class BeaconizedMarker {

    /**
     * beacon : 1asdas
     * __v : 0
     * location : {"coordinates":["50.04","19.57"],"type":"Point"}
     * _id : 55f5e896d022d5b46575c298
     * title : asdsad,asd
     * tasks : [{"role":"55f5e778d022d5b46575c293","task":"55f5e87ed022d5b46575c297","_id":"55f5e896d022d5b46575c299"}]
     */
    private String beacon;
    private LocationEntity location;
    private String _id;
    private String title;
    private String content;
    private String image_url;
    private boolean active = false;
    private boolean showed = false;

    public void setBeacon(String beacon) {
        this.beacon = beacon;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeacon() {
        return beacon;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    public class LocationEntity {
        /**
         * coordinates : ["50.04","19.57"]
         * type : Point
         */
        private List<String> coordinates;
        private String type;

        public void setCoordinates(List<String> coordinates) {
            this.coordinates = coordinates;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getCoordinates() {
            return coordinates;
        }

        public Double getLat(){
            String s = coordinates.get(0);
            return Double.parseDouble(s);
        }

        public Double getLong(){
            String s = coordinates.get(1);
            return Double.parseDouble(s);
        }

        public String getType() {
            return type;
        }
    }

    public class TasksEntity {
        /**
         * role : 55f5e778d022d5b46575c293
         * task : 55f5e87ed022d5b46575c297
         * _id : 55f5e896d022d5b46575c299
         */
        private String role;
        private String task;
        private String _id;

        public void setRole(String role) {
            this.role = role;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getRole() {
            return role;
        }

        public String getTask() {
            return task;
        }

        public String get_id() {
            return _id;
        }
    }
}
