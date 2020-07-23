package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.models.apiModels.Response.Events;
import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Request.UserLogin;
import com.example.sportsclubmanagement.models.apiModels.Request.UserRegister;
import com.example.sportsclubmanagement.models.apiModels.Response.Token;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @POST("api/signin/")
    Call<Token> userLogin(@Body UserLogin userLogin);

    @POST("api/athlete/register/")
    Call<Void> user_register(@Field("first_name") String first, @Field("last_name") String last,
                             @Field("email") String email, @Field("password") String password);

    @GET("club")
    Call<List<Clubs>> getAllClubs();

    @PUT("/api/athlete/{id}/")
    Call<Void> userAccountSetup(@Header("token") String token, @Body UserAccountSetup userAccountSetup, @Path("id") int user_id);

    @GET("/api/athlete/{id}/")
    Call<UserDetails> userDetails(@Header("token") String token, @Path("id") int user_id);
}
