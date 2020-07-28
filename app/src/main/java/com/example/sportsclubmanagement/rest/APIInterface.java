package com.example.sportsclubmanagement.rest;

import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Request.UserLogin;
import com.example.sportsclubmanagement.models.apiModels.Request.UserRegister;
import com.example.sportsclubmanagement.models.apiModels.Request.Workout;
import com.example.sportsclubmanagement.models.apiModels.Response.ClubDetailsObj;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.models.apiModels.Response.EventDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.EventMainInfo;
import com.example.sportsclubmanagement.models.apiModels.Response.HasEvent;
import com.example.sportsclubmanagement.models.apiModels.Response.Sports;
import com.example.sportsclubmanagement.models.apiModels.Response.Token;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @GET("api/club/")
    Call<String> getAllClubsTest(@Header(Constants.AUTHORIZATION) String token);

    @POST("api/club/{id}/join/")
    Call<Void> joinClub(@Header(Constants.AUTHORIZATION) String token, @Path("id") int club_id);

    @POST("api/athlete/register/")
    Call<Void> userRegister(@Body UserRegister userRegister);

    @PUT("/api/athlete/{id}/")
    Call<Void> userAccountSetup(@Header(Constants.AUTHORIZATION) String token, @Body UserAccountSetup userAccountSetup, @Path("id") int user_id);

    @GET("/api/athlete/{id}/")
    Call<UserDetails> userDetails(@Header(Constants.AUTHORIZATION) String token, @Path("id") int user_id);

    @GET("/api/sports/")
    Call<List<Sports>> getSports();

    @GET("/api/event/detail/{id}/")
    Call<EventDetails> getEventDetails(@Header(Constants.AUTHORIZATION) String token, @Path("id") int eventId);

    @GET("/api/event/is_member/")
    Call<List<EventMainInfo>> getUsersEvents(@Header(Constants.AUTHORIZATION) String token);

    @GET("/api/event/view/")
    Call<List<EventMainInfo>> getEvents(@Header(Constants.AUTHORIZATION) String token);

    @GET("/api/event/has_events")
    Call<HasEvent> hasEvent(@Header(Constants.AUTHORIZATION) String token);

    @GET("/api/workout/view/")
    Call<List<WorkoutsDetails>> getAllWorkout(@Header(Constants.AUTHORIZATION) String token);

    @POST("api/event/join/{id}/")
    Call<Void> joinEvent(@Header(Constants.AUTHORIZATION) String token,@Path("id") int event_id);

    @POST("api/workout/create/")
    Call<Void> addWorkout(@Header(Constants.AUTHORIZATION)String token,@Body Workout workout);

    @GET("/api/club/{id}/")
    Call<ClubDetailsObj> getClubDetails(@Header(Constants.AUTHORIZATION) String token, @Path("id") int clubId);

    @FormUrlEncoded
    @POST("/api/reset-password/")
    Call<Void> resetPassword(@Field("email") String email);
}
