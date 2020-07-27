package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class WorkoutForEventParticipation {
    @SerializedName("distance")
    private double distance_travelled;
    @SerializedName("calories_burned")
    private int calories;
    @SerializedName("heart_rate")
    private int bpm;
    @SerializedName("average_speed")
    private double avgSpeed;

    public WorkoutForEventParticipation(double distance_travelled, int calories, int bpm, double avgSpeed) {
        this.distance_travelled = distance_travelled;
        this.calories = calories;
        this.bpm = bpm;
        this.avgSpeed = avgSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
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
