package com.example.sportsclubmanagement.models;

import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;

public class ParticipantAdapterModel {
    private int participantId;
    private String fullName;
    private String img;
    private WorkoutsDetails workout;
    private boolean status = false;

    public ParticipantAdapterModel(int participantId, String fullName, String img) {
        this.participantId = participantId;
        this.fullName = fullName;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public ParticipantAdapterModel(String fullName) {
        this.fullName = fullName;
    }
}
