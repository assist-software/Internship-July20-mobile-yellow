package com.example.sportsclubmanagement.screens.clubdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ClubDetailsActivity extends AppCompatActivity implements EventAdapterListener {
    RecyclerView pendingRecycler, membersRecyclerView;
    EventAdapter pendingAdapter, membersAdapter;
    private List<ClubAdapterModel> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        initComponents();
    }

    private void initComponents() {
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("ID");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        membersRecyclerView = findViewById(R.id.newClub_recyclerView);
//        membersAdapter = new ClubAdapter(memberList, ClubAdapterListener.class, false);
//        membersRecyclerView.setLayoutManager(new LinearLayoutManager();
//        membersRecyclerView.setAdapter(membersAdapter);
//
        pendingRecycler = findViewById(R.id.events_recyclerView);
        pendingAdapter = new EventAdapter(getMockedList(), this, this);
        pendingRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        pendingRecycler.setAdapter(pendingAdapter);
    }

    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new EventAdapterModel("title test", "loc test", "12.06.1998"));
        }
        return mocks;
    }

    private void populateNewClubList() {
        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Join");
        memberList.add(club);
        club = new ClubAdapterModel("Running", "Join");
        memberList.add(club);
        club = new ClubAdapterModel("Hiking", "Join");
        memberList.add(club);
        club = new ClubAdapterModel("Swimming", "Join");
        memberList.add(club);
    }

    @Override
    public void onEventClick(EventAdapterModel event) {

    }
}