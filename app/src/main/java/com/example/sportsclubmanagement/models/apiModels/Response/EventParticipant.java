package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class EventParticipant {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    //@SerializedName("avatar")
    //private String img;
    @SerializedName("workout-details")
    private WorkoutForEventParticipation workout;

    public EventParticipant(int id, String firstName, String lastName, WorkoutForEventParticipation workout) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.img = img;
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

//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }

    public WorkoutForEventParticipation getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutForEventParticipation workout) {
        this.workout = workout;
    }
}
