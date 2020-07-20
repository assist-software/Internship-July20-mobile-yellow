package com.example.sportsclubmanagement.models;

public class ParticipantAdapterModel {
    String fullName;
    boolean status;

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
