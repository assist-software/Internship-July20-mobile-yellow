package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Request.UserLogin;
import com.example.sportsclubmanagement.models.apiModels.Request.UserRegister;
import com.example.sportsclubmanagement.models.apiModels.Response.Token;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @POST("api/signin/")
    Call<Token> userLogin(@Body UserLogin userLogin);

    @POST("api/athlete/register/")
    Call<Void> userRegister(@Body UserRegister userRegister);

    @PUT("/api/athlete/{id}/")
    Call<Void> userAccountSetup(@Header("token") String token, @Body UserAccountSetup userAccountSetup, @Path("id") int user_id);

    @GET("/api/athlete/get{id}")
    Call<UserDetails> userDetails(@Header("token")String token, @Path("id")int user_id);
}
