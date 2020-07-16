package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.apiModels.Token;
import com.example.sportsclubmanagement.models.apiModels.UserLogin;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/json/get/bTyulPybma?indent=2")
    Call<Token> user_login();
    /*
    @GET("/api/users?")
    abstract public Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
