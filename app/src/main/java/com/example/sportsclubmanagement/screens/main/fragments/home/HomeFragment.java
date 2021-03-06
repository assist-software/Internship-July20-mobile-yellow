package com.example.sportsclubmanagement.screens.main.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.WorkoutAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.models.apiModels.Response.EventMainInfo;
import com.example.sportsclubmanagement.models.apiModels.Response.HasEvent;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.clubdetails.ClubDetailsActivity;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.workouts.workoutadapter.workoutAdapter;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ClubAdapterListener, EventAdapterListener {
    private RecyclerView firstClubsRecyclerView, firstEventsRecyclerView, clubsRecyclerView, futureEventsRecyclerView, workoutRecyclerView;
    private ClubAdapter firstClubAdapter, myNewClubsAdapter;
    private EventAdapter futureEventsAdapter, firstEventAdapter;
    private workoutAdapter adapterWorkout;
    private TextView name, joinFirstClubtxt, joinFirstEventTxt, newClubTxt, futureEvetnsTxt, noInformation1,
            noInformation2, noInformation3, noInformation4, noInformation5, workoutTxt;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private RecyclerView newClubRecyclerView;
    private List<Clubs> allClubs, myNewClubs;
    private List<EventAdapterModel> futureEvents, allEventsAvailable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initComponents(View view) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pref = getContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        name = view.findViewById(R.id.name_user);
        ImageView profilePicture = view.findViewById(R.id.home_profile_picture);
        Glide.with(HomeFragment.this).load(R.drawable.avatar_picture).apply(RequestOptions.circleCropTransform()).into(profilePicture);

        firstClubsRecyclerView = view.findViewById(R.id.home_joinClub_recyclerView);
        clubsRecyclerView = view.findViewById(R.id.home_club_recyclerView);
        firstEventsRecyclerView = view.findViewById(R.id.home_joinEvent_recyclerView);
        futureEventsRecyclerView = view.findViewById(R.id.home_futureEvents_recyclerView);
        newClubRecyclerView = view.findViewById(R.id.home_club_recyclerView);
        workoutRecyclerView = view.findViewById(R.id.home_workout_recycler);

        joinFirstClubtxt = view.findViewById(R.id.home_joinClub_textView);
        joinFirstEventTxt = view.findViewById(R.id.home_joinEvent_textView);
        newClubTxt = view.findViewById(R.id.home_club_textView);
        futureEvetnsTxt = view.findViewById(R.id.home_futureEvents_textView);
        workoutTxt = view.findViewById(R.id.home_workout_text);

        noInformation1 = view.findViewById(R.id.noInformationTextView1);
        noInformation2 = view.findViewById(R.id.noInformationTextView2);
        noInformation3 = view.findViewById(R.id.noInformationTextView3);
        noInformation4 = view.findViewById(R.id.noInformationTextView4);
        noInformation5 = view.findViewById(R.id.noInformationTextView5);

        showUserInfo();
        getAllEvents();
        hasEvents();
        requestClubs();
        getUserWorkouts();
    }

    private void getUserWorkouts() {
        Call<List<WorkoutsDetails>> call = apiInterface.getAllWorkout(pref.getString("token", null));
        call.enqueue(new Callback<List<WorkoutsDetails>>() {
            @Override
            public void onResponse(Call<List<WorkoutsDetails>> call, Response<List<WorkoutsDetails>> response) {
                if (response.isSuccessful()) {
                    Log.d("Hom", "The information about all workout was accepted");
                    List<WorkoutAdapterModel> workouts = new ArrayList<>();
                    for (WorkoutsDetails element : response.body()) {
                        workouts.add(new WorkoutAdapterModel(element.getDate_workout(), element.getTime_workout(), element.getDistance_travelled(), element.getCalories(), element.getBpm()));
                    }
                    if (workouts.size() != 0) {
                        initWorkoutAdapter(workouts,adapterWorkout,workoutRecyclerView);
                    }
                    else {
                        hideWorkoutRecycler();
                    }
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), "Fail gave all workout", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WorkoutsDetails>> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void hideWorkoutRecycler() {
        workoutRecyclerView.setVisibility(View.GONE);
        noInformation5.setVisibility(View.VISIBLE);
    }

    private void showUserInfo() {
        Call<UserDetails> call = apiInterface.userDetails(pref.getString("token", null), pref.getInt("id", 0));
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    Log.d("Hom", "The information was accepted");
                    UserDetails resp = response.body();
                    name.setText(resp.getFirstName() + " " + resp.getLastName());
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), "Fail gave info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void hideFirstJoinClub() {
        firstClubsRecyclerView.setVisibility(View.GONE);
        joinFirstClubtxt.setVisibility(View.GONE);
        noInformation1.setVisibility(View.GONE);
    }

    private void hideMyNewClubRecycler() {
        clubsRecyclerView.setVisibility(View.GONE);
        noInformation3.setVisibility(View.VISIBLE);
    }

    private void requestClubs() {
        Call<List<Clubs>> call = apiInterface.getAllClubs(pref.getString(Constants.TOKEN, null));
        call.enqueue(new Callback<List<Clubs>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Clubs>> call, Response<List<Clubs>> response) {
                if (response.isSuccessful()) {
                    allClubs = response.body();
                    myNewClubs = allClubs.stream().filter(club -> (!club.isMember() && !club.isRequested())).collect(Collectors.toList());
                    //if user is member on one or more clubs
                    if (allClubs.stream().anyMatch(Clubs::isMember)) {
                        hideFirstJoinClub();
                    } else {
                        initClubsAdapter(myNewClubs, firstClubAdapter, firstClubsRecyclerView, true);
                    }
                    if (myNewClubs.size() == 0) {
                        hideMyNewClubRecycler();
                        if(joinFirstClubtxt.getVisibility()!=View.GONE)
                            noInformation1.setVisibility(View.VISIBLE);
                        noInformation3.setVisibility(View.VISIBLE);
                    } else {
                        initClubsAdapter(myNewClubs, myNewClubsAdapter, newClubRecyclerView, true);
                    }
                } else {
                    Log.d("Tag", "Failed request get all clubs");
                }
            }

            @Override
            public void onFailure(Call<List<Clubs>> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void hasEvents() {
        Call<HasEvent> call = apiInterface.hasEvent(pref.getString("token", null));
        call.enqueue(new Callback<HasEvent>() {
            @Override
            public void onResponse(Call<HasEvent> call, Response<HasEvent> response) {
                if (response.isSuccessful()) {
                    Log.d("Hom", "The information was accepted");
                    HasEvent has = response.body();
                    if (has.isHas()) {
                        hideFirstJoinEvent();
                    }
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), "Fail gave info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HasEvent> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void hideFirstJoinEvent() {
        firstEventsRecyclerView.setVisibility(View.GONE);
        joinFirstEventTxt.setVisibility(View.GONE);
        noInformation2.setVisibility(View.GONE);
    }

    private void getAllEvents() {
        Call<List<EventMainInfo>> call = apiInterface.getEvents(pref.getString("token", null));
        call.enqueue(new Callback<List<EventMainInfo>>() {
            @Override
            public void onResponse(Call<List<EventMainInfo>> call, Response<List<EventMainInfo>> response) {
                if (response.isSuccessful()) {
                    futureEvents = new ArrayList<>();
                    allEventsAvailable = new ArrayList<>();
                    for (EventMainInfo event : response.body()) {
                        if (checkIfEventIsInFutureOrToday(event.getDate())) {
                            futureEvents.add(new EventAdapterModel(event.getId(), event.getTitle(), event.getLocatia(), event.getDate(), true, false, true));
                            if (firstEventsRecyclerView.getVisibility() != View.GONE) {
                                allEventsAvailable.add(new EventAdapterModel(event.getId(), event.getTitle(), event.getLocatia(), event.getDate(), true, true, false));
                            }
                        }
                    }
                    if (futureEvents.size() == 0) {
                        hideFutureEvent();
                    } else {
                        initEventsAdapter(futureEvents, futureEventsAdapter, futureEventsRecyclerView);
                    }
                    if(allEventsAvailable.size()==0){
                        noInformation2.setVisibility(View.VISIBLE);
                    }
                    if(futureEvents.size()==1){
                        futureEventsRecyclerView.getLayoutParams().height=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getContext().getResources().getDisplayMetrics());
                    }
                    //if user has been wasnt join into events
                    if (firstEventsRecyclerView.getVisibility() != View.GONE) {
                        initEventsAdapter(allEventsAvailable, firstEventAdapter, firstEventsRecyclerView);
                    }
                    Log.d("Hom", Constants.Home_get_list_accept);
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), Constants.Home_get_list_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventMainInfo>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private boolean checkIfEventIsInFutureOrToday(Date eventDate) {
        Date now = new Date();
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        return eventDate.after(calendar.getTime());
    }

    private void hideFutureEvent() {
        futureEventsRecyclerView.setVisibility(View.GONE);
        noInformation4.setVisibility(View.VISIBLE);
    }

    @Override
    public void onJoinClick(String clubName, int clubId) {
        joinClubFunction(clubId);
    }

    private void joinClubFunction(int clubId) {
        Call<Void> call = apiInterface.joinClub(pref.getString(Constants.TOKEN, null), clubId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Clubs club = myNewClubs.stream().filter(c -> c.getClubInfo().getId() == clubId).findFirst().orElse(null);
                    if (club != null) {
                        club.setRequested(true);
                        myNewClubs.remove(club);
                        allClubs.remove(club);
                        if (!allClubs.stream().filter(p -> p.isMember() || p.isRequested()).collect(Collectors.toList()).isEmpty()) {
                            hideFirstJoinClub();
                        }
                        if (firstClubsRecyclerView.getVisibility() != View.GONE) {
                            initClubsAdapter(myNewClubs, firstClubAdapter, firstClubsRecyclerView, true);
                        }
                        if(myNewClubs.size()!=0){
                            initClubsAdapter(myNewClubs, myNewClubsAdapter, newClubRecyclerView, true);
                        }else{
                            noInformation1.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(HomeFragment.this.getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    @Override
    public void onClubClick(String clubName, int clubId) {
        Intent clubIntent = new Intent(getActivity(), ClubDetailsActivity.class);
        clubIntent.putExtra(Constants.CLUB_NAME, clubName);
        startActivity(clubIntent);
    }

    @Override
    public void onEventClick(EventAdapterModel event) {
        Intent i = new Intent(getContext(), EventActivity.class);
        i.putExtra("event_id", event.getId());
        startActivity(i);
    }

    @Override
    public void onEventJoinClick(EventAdapterModel event) {
        joinEvent(event);
    }


    private void joinEvent(EventAdapterModel event) {
        Call<Void> call = apiInterface.joinEvent(pref.getString("token", null), event.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    hideFirstJoinEvent();
                    futureEvents.remove(event);
                    if (futureEvents.size() == 0) {
                        hideFutureEvent();
                    } else {
                        initEventsAdapter(futureEvents, futureEventsAdapter, futureEventsRecyclerView);
                    }
                    Log.d("Hom", "Join successful");
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), Constants.Home_join_event_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void initEventsAdapter(List<EventAdapterModel> list, EventAdapter adapter, RecyclerView recyclerView) {
        adapter = new EventAdapter(list, HomeFragment.this.getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initClubsAdapter(List<Clubs> list, ClubAdapter adapter, RecyclerView recyclerView, boolean hasBorder) {
        adapter = new ClubAdapter(list, HomeFragment.this, hasBorder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initWorkoutAdapter(List<WorkoutAdapterModel> list, workoutAdapter adapter, RecyclerView recyclerView) {
        adapter = new workoutAdapter(list, HomeFragment.this.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}