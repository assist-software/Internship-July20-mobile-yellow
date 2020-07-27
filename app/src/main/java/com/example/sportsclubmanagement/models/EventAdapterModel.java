package com.example.sportsclubmanagement.models;

import com.example.sportsclubmanagement.R;

import java.util.Date;

public class EventAdapterModel {
    private int id;
    private String title;
    private String location;
    private Date dataEvent;
    private static int imgId = R.drawable.img_event;
    private boolean visibleBtn ;
    private boolean upPositionBtn;
    private boolean big;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(Date dataEvent) {
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

    public boolean isVisibleBtn() {
        return visibleBtn;
    }

    public void setVisibleBtn(boolean visibleBtn) {
        this.visibleBtn = visibleBtn;
    }

    public boolean isUpPositionBtn() {
        return upPositionBtn;
    }

    public void setUpPositionBtn(boolean upPositionBtn) {
        this.upPositionBtn = upPositionBtn;
    }

    public EventAdapterModel(int id, String title, String location, Date dataEvent, boolean visibleBtn, boolean upPositionBtn, boolean big) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.dataEvent = dataEvent;
        this.visibleBtn = visibleBtn;
        this.upPositionBtn = upPositionBtn;
        this.big = big;
    }

    public boolean isBig() {
        return big;
    }

    public void setBig(boolean big) {
        this.big = big;
    }
}
