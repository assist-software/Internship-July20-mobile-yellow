package com.example.sportsclubmanagement.models;

import java.util.Date;

public class WorkoutAdapterModel {
    private Date date_workout;
    private int time_workout;
    private double distance_travelled;
    private double calories;
    private int bpm;

    public WorkoutAdapterModel(Date date_workout, int time_workout, double distance_travelled, double calories, int bpm) {
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

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
