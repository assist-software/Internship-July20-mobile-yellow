package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Request.UserLogin;
import com.example.sportsclubmanagement.models.apiModels.Request.UserRegister;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.models.apiModels.Response.EventDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.EventsAvailable;
import com.example.sportsclubmanagement.models.apiModels.Response.Sports;
import com.example.sportsclubmanagement.models.apiModels.Response.Token;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.List;

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

    @GET("api/club/")
    Call<List<Clubs>> getAllClubs(@Header(Constants.AUTHORIZATION) String token);

    @POST("api/athlete/register/")
    Call<Void> userRegister(@Body UserRegister userRegister);

    @PUT("/api/athlete/{id}/")
    Call<Void> userAccountSetup(@Header(Constants.AUTHORIZATION) String token, @Body UserAccountSetup userAccountSetup, @Path("id") int user_id);

    @GET("/api/athlete/{id}/")
    Call<UserDetails> userDetails(@Header(Constants.AUTHORIZATION) String token, @Path("id") int user_id);

    @GET("/api/sports/")
    Call<List<Sports>> getSports();

    @GET("/api/evetdetails{id}/")
    Call<EventDetails> getEventDetails(@Header(Constants.AUTHORIZATION) String token, @Path("id") int eventId);

    @GET("/api/event/view/")
    Call<List<EventsAvailable>> getEvents(@Header(Constants.AUTHORIZATION) String token);

    @GET("/api/workouts{id}/")
    Call<List<WorkoutsDetails>> getAllWorkout(@Header(Constants.AUTHORIZATION) String token);
}
