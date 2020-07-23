package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    public String token;
    @SerializedName("id")
    public int user_id;

    public Token(String token, int user_id) {
        this.token = token;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
