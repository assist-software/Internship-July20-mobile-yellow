package com.example.sportsclubmanagement.models.apiModels.Request;

import com.google.gson.annotations.SerializedName;

public class Workout {
    @SerializedName("event")
    private int eventId;
    @SerializedName("duration")
    private int duration;
    @SerializedName("distance")
    private double distance;
    @SerializedName("heart_rate")
    private int heart_rate;
    @SerializedName("average_speed")
    private double average_speed;
    @SerializedName("calories_burned")
    private int calories;
    @SerializedName("workout_effectiveness")
    private int effectiveness;

    public Workout(int eventId, int duration, double distance, int heart_rate, int calories, int effectiveness, double average_speed) {
        this.eventId = eventId;
        this.duration = duration;
        this.distance = distance;
        this.heart_rate = heart_rate;
        this.calories = calories;
        this.effectiveness = effectiveness;
        this.average_speed = average_speed;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }
}
