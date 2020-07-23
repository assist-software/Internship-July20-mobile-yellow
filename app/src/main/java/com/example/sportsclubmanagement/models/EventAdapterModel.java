package com.example.sportsclubmanagement.models;

import com.example.sportsclubmanagement.R;

public class EventAdapterModel {
    private String title;
    private String location;
    private String dataEvent;
    private static int imgId = R.drawable.img_event;

    public String getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(String dataEvent) {
        this.dataEvent = dataEvent;
    }

    public static int getImgId() {
        return imgId;
    }

    public static void setImgId(int imgId) {
        EventAdapterModel.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventAdapterModel(String title, String location, String dataEvent) {
        this.title = title;
        this.location = location;
        this.dataEvent = dataEvent;
    }
}
