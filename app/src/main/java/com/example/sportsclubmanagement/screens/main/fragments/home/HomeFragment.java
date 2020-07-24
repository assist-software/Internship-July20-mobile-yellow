package com.example.sportsclubmanagement.screens.main.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.ClubInfo;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.models.apiModels.Response.EventsAvailable;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.clubdetails.ClubDetailsActivity;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.ClubsFragment;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ClubAdapterListener, EventAdapterListener {
    private Toolbar toolbar;
    private RecyclerView firstClubsRecyclerView, firstEventsRecyclerView, clubsRecyclerView, futureEventsRecyclerView;
    private EventAdapter firstEventAdapter, futureEventsAdapter;
    private ClubAdapter firstClubAdapter, clubAdapter;
    private TextView name;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<EventAdapterModel> events;
    private List<EventAdapterModel> future;

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
        toolbar = getActivity().findViewById(R.id.home_tool);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pref = getContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        name = view.findViewById(R.id.name_user);
        events = new ArrayList<>();
        future = new ArrayList<>();
        ImageView profilePicture = view.findViewById(R.id.home_profile_picture);
        Glide.with(HomeFragment.this).load(R.drawable.avatar_picture).apply(RequestOptions.circleCropTransform()).into(profilePicture);

        firstClubsRecyclerView = view.findViewById(R.id.home_joinClub_recyclerView);
        firstClubAdapter = new ClubAdapter(getClubList(), this, true);
        firstClubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firstClubsRecyclerView.setAdapter(firstClubAdapter);

        clubsRecyclerView = view.findViewById(R.id.home_club_recyclerView);
        clubAdapter = new ClubAdapter(getClubList(), this, true);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clubsRecyclerView.setAdapter(clubAdapter);

        firstEventsRecyclerView = view.findViewById(R.id.home_joinEvent_recyclerView);
        futureEventsRecyclerView = view.findViewById(R.id.home_futureEvents_recyclerView);
        showUserInfo();
        getAllEvents();
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
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void getAllEvents() {
        Call<List<EventsAvailable>> call = apiInterface.getEvents(pref.getString("token", null));
        call.enqueue(new Callback<List<EventsAvailable>>() {
            @Override
            public void onResponse(Call<List<EventsAvailable>> call, Response<List<EventsAvailable>> response) {
                if (response.isSuccessful()) {
                    for (EventsAvailable event : response.body()) {
                        events.add(new EventAdapterModel(event.getId(), event.getTitle(), event.getLocatia(), event.getDate(), true, false));
                        future.add(new EventAdapterModel(event.getId(), event.getTitle(), event.getLocatia(), event.getDate(), true, true));
                    }
                    populateRecyclerEvents();
                    Log.d("Hom", Constants.Home_get_list_accpet);
                } else {
                    Log.d("Home", "Error");
                    Toast.makeText(getContext(), Constants.Home_get_list_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventsAvailable>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void populateRecyclerEvents() {
        //first evetns
        initAdapter(events,firstEventAdapter,firstEventsRecyclerView);
        //future events
        initAdapter(future,futureEventsAdapter,futureEventsRecyclerView);
    }

    @Override
    public void onJoinClick(String clubName, int clubId) {
        Toast.makeText(this.getContext(), clubName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClubClick(String clubName) {
        Intent clubIntent = new Intent(getActivity(), ClubDetailsActivity.class);
        clubIntent.putExtra(Constants.CLUB_NAME, clubName);
        startActivity(clubIntent);
    }

    @Override
    public void onEventClick(EventAdapterModel event) {
        String event_id = "test id";
        Intent i = new Intent(getContext(), EventActivity.class);
        i.putExtra("event_id", event_id);
        startActivity(i);
    }

    private List<Clubs> getClubList() {
        List<Clubs> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new Clubs(new ClubInfo(1, "Running"), false, false, false));
        }
        return mocks;
    }

    private void initAdapter(List<EventAdapterModel> list, EventAdapter adapter, RecyclerView recyclerView) {
        adapter = new EventAdapter(list, HomeFragment.this.getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}