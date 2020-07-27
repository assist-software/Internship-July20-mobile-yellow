package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventDetails {
    @SerializedName("name")
    private String title;
    @SerializedName("location")
    private String location;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private Date dataEvent;
    @SerializedName("time")
    private Date timeEvent;
    @SerializedName("participants_details")
    private List<EventParticipant> participants;

    public EventDetails(String title, String location, String description, Date dataEvent, Date timeEvent, List<EventParticipant> participants) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.dataEvent = dataEvent;
        this.timeEvent = timeEvent;
        this.participants = participants;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(Date dataEvent) {
        this.dataEvent = dataEvent;
    }

    public Date getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(Date timeEvent) {
        this.timeEvent = timeEvent;
    }

    public List<EventParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EventParticipant> participants) {
        this.participants = participants;
    }
}
