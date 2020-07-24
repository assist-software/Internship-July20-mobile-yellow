package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EventsAvailable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String title;
    @SerializedName("location")
    private String locatia;
    @SerializedName("date")
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocatia() {
        return locatia;
    }

    public void setLocatia(String locatia) {
        this.locatia = locatia;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventsAvailable(int id, String title, String locatia, Date date) {
        this.id = id;
        this.title = title;
        this.locatia = locatia;
        this.date = date;
    }
}
