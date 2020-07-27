package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class EventParticipant {
    @SerializedName("user_id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("avatar")
    private String img;
    @SerializedName("workout")
    private WorkoutsDetails workout;

    public EventParticipant(int id, String firstName, String lastName, String img, WorkoutsDetails workout) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.img = img;
        this.workout = workout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public WorkoutsDetails getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutsDetails workout) {
        this.workout = workout;
    }
}
