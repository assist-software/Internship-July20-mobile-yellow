package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class UserDetails {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private int age;
    @SerializedName("primary_sport")
    private String primarySport;
    @SerializedName("secondary_sport")
    private String secondarySport;
    @SerializedName("height")
    private int height;
    @SerializedName("weight")
    private double weight;
    @SerializedName("avatar")
    private String avatar;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPrimarySport() {
        return primarySport;
    }

    public void setPrimarySport(String primarySport) {
        this.primarySport = primarySport;
    }

    public String getSecondarySport() {
        return secondarySport;
    }

    public void setSecondarySport(String secondarySport) {
        this.secondarySport = secondarySport;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserDetails(String firstName, String lastName, String gender, int age, String primarySport, String secondarySport, int height, double weight, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
        this.avatar = avatar;
    }

    public UserDetails(String firstName, String lastName, String gender, int age, String primarySport, String secondarySport, int height, double weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
    }
}
