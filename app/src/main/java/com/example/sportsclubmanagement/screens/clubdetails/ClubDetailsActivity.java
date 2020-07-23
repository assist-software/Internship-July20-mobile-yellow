package com.example.sportsclubmanagement.screens.clubdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.ParticipantAdapterModel;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapter;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;
import com.example.sportsclubmanagement.screens.myprofile.MyProfileActivity;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ClubDetailsActivity extends AppCompatActivity implements ParticipantAdapterListener, EventAdapterListener {
    //TODO: make private fields
    RecyclerView eventsRecycler, membersRecyclerView;
    EventAdapter eventsAdapter;
    ParticipantAdapter membersAdapter;
    private List<ParticipantAdapterModel> memberList = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        initComponents();
    }

    private void initComponents() {
        Bundle bundle = getIntent().getExtras();
        String clubName = bundle.getString(Constants.CLUB_NAME);
        toolbar = findViewById(R.id.home_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(clubName);
        toolbar.setTitleTextColor(ContextCompat.getColor(ClubDetailsActivity.this, R.color.colorWhite));
        memberList = getMembersList();
        membersRecyclerView = findViewById(R.id.members_recyclerView);
        membersAdapter = new ParticipantAdapter(memberList, this, this, false);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        membersRecyclerView.setAdapter(membersAdapter);

        eventsRecycler = findViewById(R.id.events_recyclerView);
        eventsAdapter = new EventAdapter(getMockedList(), this, this);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        eventsRecycler.setAdapter(eventsAdapter);

        //add back button to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //change color for toolbar back icon programmatically
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ImageView profilePicture = findViewById(R.id.profile_picture);
        Glide.with(ClubDetailsActivity.this).load(R.drawable.avatar_picture).apply(RequestOptions.circleCropTransform()).into(profilePicture);
    }

    @Override
    public void onEventClick(ParticipantAdapterModel participant) {

    }

    @Override
    public void onEventClick(EventAdapterModel event) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private List<ParticipantAdapterModel> getMembersList() {
        List<ParticipantAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new ParticipantAdapterModel("Hehe boy"));
        }
        return mocks;
    }


    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new EventAdapterModel("title test", "loc test", "12.06.1998"));
        }
        return mocks;
    }
}