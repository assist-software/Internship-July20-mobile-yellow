package com.example.sportsclubmanagement.models;

import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutForEventParticipation;

public class ParticipantAdapterModel {
    private String fullName;
    private String img;
    private WorkoutForEventParticipation workout;
    private boolean status = false;

    public ParticipantAdapterModel(String fullName, String img) {
        this.fullName = fullName;
        this.img = img;
    }


    public WorkoutForEventParticipation getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutForEventParticipation workout) {
        this.workout = workout;
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


    public ParticipantAdapterModel(String fullName,WorkoutForEventParticipation workout) {
        this.fullName = fullName;
        this.workout = workout;
    }

    public ParticipantAdapterModel(String fullName){
        this.fullName = fullName;
    }
}
