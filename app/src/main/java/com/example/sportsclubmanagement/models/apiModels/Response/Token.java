package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    public String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
