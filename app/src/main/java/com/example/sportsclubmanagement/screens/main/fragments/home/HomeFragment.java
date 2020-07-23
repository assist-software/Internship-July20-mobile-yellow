package com.example.sportsclubmanagement.screens.main.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.clubdetails.ClubDetailsActivity;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;
import com.example.sportsclubmanagement.screens.login.LoginActivity;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ClubAdapterListener, EventAdapterListener {
    private Toolbar toolbar;
    private RecyclerView firstClubsRecyclerView, firstEventsRecyclerView, clubsRecyclerView, futureEventsRecyclerView;
    EventAdapter firstEventAdapter, futureEventsAdapter;
    ClubAdapter firstClubAdapter, clubAdapter;
    TextView name;
    APIInterface apiInterface;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    private void initComponents(View view) {
        toolbar = getActivity().findViewById(R.id.home_tool);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pref = getContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        name = view.findViewById(R.id.name_user);

        SharedPreferences pref = getContext().getSharedPreferences(Constants.TOKEN, 0);
        Toast.makeText(getContext(),pref.getString(Constants.TOKEN,null),Toast.LENGTH_SHORT).show();

        ImageView profilePicture = view.findViewById(R.id.home_profile_picture);
        Glide.with(HomeFragment.this).load(R.drawable.avatar_picture).apply(RequestOptions.circleCropTransform()).into(profilePicture);

        firstClubsRecyclerView = view.findViewById(R.id.home_joinClub_recyclerView);
        firstClubAdapter = new ClubAdapter(getClubList(), this, true);
        firstClubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firstClubsRecyclerView.setAdapter(firstClubAdapter);

        firstEventsRecyclerView = view.findViewById(R.id.home_joinEvent_recyclerView);
        firstEventAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        firstEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        firstEventsRecyclerView.setAdapter(firstEventAdapter);

        clubsRecyclerView = view.findViewById(R.id.home_club_recyclerView);
        clubAdapter = new ClubAdapter(getClubList(), this, true);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clubsRecyclerView.setAdapter(clubAdapter);

        futureEventsRecyclerView = view.findViewById(R.id.home_futureEvents_recyclerView);
        futureEventsAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        futureEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        futureEventsRecyclerView.setAdapter(futureEventsAdapter);

        showUserInfo();
    }

    private void showUserInfo() {
        Call<UserDetails> call = apiInterface.userDetails(pref.getString("token", null), pref.getInt("id", 0));
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    Log.d("Hom", "The information was accepted");
                    UserDetails resp = response.body();
                    name.setText(resp.getFirstName()+" "+resp.getLastName());
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

    @Override
    public void onJoinClick(String clubName) {
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
        i.putExtra("event_id",event_id);
        startActivity(i);
    }

    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new EventAdapterModel("title test", "loc test", "12.06.1998"));
        }
        return mocks;
    }

    private List<ClubAdapterModel> getClubList() {
        List<ClubAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new ClubAdapterModel("Running", "Join"));
        }
        return mocks;
    }
}