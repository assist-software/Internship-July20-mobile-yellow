package com.example.sportsclubmanagement.models.apiModels;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    public String token;

    public Token(String token) {
        this.token = token;
    }
}
