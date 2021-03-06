package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WorkoutsDetails {
    @SerializedName("event")
    private Date date_workout;
    @SerializedName("duration")
    private int time_workout;
    @SerializedName("distance")
    private double distance_travelled;
    @SerializedName("calories_burned")
    private int calories;
    @SerializedName("heart_rate")
    private int bpm;

    public WorkoutsDetails(Date date_workout, int time_workout, double distance_travelled, int calories, int bpm) {
        this.date_workout = date_workout;
        this.time_workout = time_workout;
        this.distance_travelled = distance_travelled;
        this.calories = calories;
        this.bpm = bpm;
    }

    public Date getDate_workout() {
        return date_workout;
    }

    public void setDate_workout(Date date_workout) {
        this.date_workout = date_workout;
    }

    public int getTime_workout() {
        return time_workout;
    }

    public void setTime_workout(int time_workout) {
        this.time_workout = time_workout;
    }

    public double getDistance_travelled() {
        return distance_travelled;
    }

    public void setDistance_travelled(double distance_travelled) {
        this.distance_travelled = distance_travelled;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
