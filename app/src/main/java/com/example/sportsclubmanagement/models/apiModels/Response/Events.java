package com.example.sportsclubmanagement.models.apiModels.Response;

import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Events{
    @SerializedName("id")
    private int club_id;
    @SerializedName("name")
    private String title;
    private String location;
    @SerializedName("date")
    private Date dataEvent;

    public Events(int club_id, String title, String location, Date dataEvent) {
        this.club_id = club_id;
        this.title = title;
        this.location = location;
        this.dataEvent = dataEvent;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
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

    public Date getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(Date dataEvent) {
        this.dataEvent = dataEvent;
    }
}
