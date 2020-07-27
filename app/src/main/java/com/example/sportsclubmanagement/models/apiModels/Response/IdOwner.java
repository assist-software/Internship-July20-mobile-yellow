package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class IdOwner {
    @SerializedName("first_name")
    private String coachFirstName;
    @SerializedName("last_name")
    private String coachLastName;
    private int age;

    public IdOwner(String coachFirstName, String coachLastName, int age) {
        this.coachFirstName = coachFirstName;
        this.coachLastName = coachLastName;
        this.age = age;
    }

    public String getCoachFirstName() {
        return coachFirstName;
    }

    public void setCoachFirstName(String coachFirstName) {
        this.coachFirstName = coachFirstName;
    }

    public String getCoachLastName() {
        return coachLastName;
    }

    public void setCoachLastName(String coachLastName) {
        this.coachLastName = coachLastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
