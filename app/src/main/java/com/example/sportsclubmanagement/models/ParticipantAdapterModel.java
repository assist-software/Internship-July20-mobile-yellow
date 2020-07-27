package com.example.sportsclubmanagement.models;

import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;

public class ParticipantAdapterModel {
    private String fullName;
    private String img;
    private WorkoutsDetails workout;
    private boolean status = false;

    public ParticipantAdapterModel(String fullName, String img) {
        this.fullName = fullName;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
