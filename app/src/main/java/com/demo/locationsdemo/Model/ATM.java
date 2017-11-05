package com.demo.locationsdemo.Model;

/**
 * Created by Natalija on 11/5/2017.
 */

public class ATM {

    private String id;
    private String longitude;
    private String latitude;
    private String name;

    public ATM(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
