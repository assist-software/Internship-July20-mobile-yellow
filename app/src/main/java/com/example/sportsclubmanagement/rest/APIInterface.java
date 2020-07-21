package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.apiModels.Token;
import com.example.sportsclubmanagement.models.apiModels.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("api/signin/")
    Call<Token> user_login(@Field("email")String email, @Field("password")String pass);
    /*
    @GET("/api/users?")
    abstract public Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
