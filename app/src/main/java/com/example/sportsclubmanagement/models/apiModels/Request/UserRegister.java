package com.example.sportsclubmanagement.models.apiModels.Request;

import com.google.gson.annotations.SerializedName;

public class UserRegister {
    @SerializedName("first_name")
    public String first;
    @SerializedName("last_name")
    public String last;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    public UserRegister(String first, String last, String email, String password) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
    }
}
