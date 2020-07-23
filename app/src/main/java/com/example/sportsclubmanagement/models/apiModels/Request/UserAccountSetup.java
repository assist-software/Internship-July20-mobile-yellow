package com.example.sportsclubmanagement.models.apiModels.Request;

import com.google.gson.annotations.SerializedName;


public class UserAccountSetup {
    @SerializedName("name")
    private String name = "";
    @SerializedName("gender")
    public String gender;
    @SerializedName("age")
    public int age;
    @SerializedName("primary_sport")
    public String primarySport;
    @SerializedName("secondary_sport")
    public String secondarySport;
    @SerializedName("height")
    public int height;
    @SerializedName("weight")
    public double weight;
    @SerializedName("avatar")
    public String avatar;

    public UserAccountSetup(int age, String primarySport, String secondarySport, int height, double weight) {
        this.age = age;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
    }

    public UserAccountSetup(String gender, int age, String primarySport, String secondarySport, int height, double weight) {
        this.gender = gender;
        this.age = age;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
