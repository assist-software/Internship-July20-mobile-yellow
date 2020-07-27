package com.example.sportsclubmanagement.screens.clubdetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.ParticipantAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.ClubDetailsObj;
import com.example.sportsclubmanagement.models.apiModels.Response.EventMainInfo;
import com.example.sportsclubmanagement.models.apiModels.Response.MembersListItem;
import com.example.sportsclubmanagement.models.apiModels.Response.OwnerInfo;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapter;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubDetailsActivity extends AppCompatActivity implements ParticipantAdapterListener, EventAdapterListener {
    private RecyclerView eventsRecyclerView, membersRecyclerView;
    private EventAdapter eventsAdapter;
    private ParticipantAdapter membersAdapter;
    private Toolbar toolbar;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private int clubId;
    private TextView coachName, coachAge, ownedClubs, noInformation1, noInformation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        initComponents();
        getClubDetails(clubId);
    }

    private void initComponents() {
        Bundle bundle = getIntent().getExtras();
        String clubName = bundle.getString(Constants.CLUB_NAME);
        clubId = bundle.getInt(Constants.CLUB_ID);
        toolbar = findViewById(R.id.home_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(clubName);
        toolbar.setTitleTextColor(ContextCompat.getColor(ClubDetailsActivity.this, R.color.colorWhite));
        pref = this.getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, 0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        noInformation1 = findViewById(R.id.noInformationTextView1);
        noInformation2 = findViewById(R.id.noInformationTextView2);

        //coach info
        coachName = findViewById(R.id.coach_name);
        coachAge = findViewById(R.id.coach_age);
        ownedClubs = findViewById(R.id.ownedClubs);

        membersRecyclerView = findViewById(R.id.members_recyclerView);
        eventsRecyclerView = findViewById(R.id.events_recyclerView);

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
        Intent i = new Intent(getApplicationContext(), EventActivity.class);
        i.putExtra("eventId", event.getId());
        startActivity(i);
    }

    @Override
    public void onEventJoinClick(EventAdapterModel event) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getClubDetails(int clubId) {
        Call<ClubDetailsObj> call = apiInterface.getClubDetails(pref.getString(Constants.TOKEN, null), clubId);
        call.enqueue(new Callback<ClubDetailsObj>() {
            @Override
            public void onResponse(Call<ClubDetailsObj> call, Response<ClubDetailsObj> response) {
                ClubDetailsObj clubDetailsObj = response.body();
                initCoachDetails(clubDetailsObj);
                initMembersAdapter(clubDetailsObj);
                initEventsAdapter(clubDetailsObj);
            }

            @Override
            public void onFailure(Call<ClubDetailsObj> call, Throwable t) {
                Log.e("Response Error", t.getMessage());
            }
        });
    }

    private void initMembersAdapter(ClubDetailsObj clubDetailsObj) {
        List<MembersListItem> memberList = clubDetailsObj.getMembers();
        List<ParticipantAdapterModel> localList = new ArrayList<>();
        for (MembersListItem element : memberList) {
            if (element.isIs_member()) {
                localList.add(new ParticipantAdapterModel(element.getIdUser().getFirst_name() + " " + element.getIdUser().getLast_name()));
            }
        }
        if (localList.size() != 0) {
            membersAdapter = new ParticipantAdapter(localList, getApplicationContext(), ClubDetailsActivity.this, false);
            membersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            membersRecyclerView.setAdapter(membersAdapter);
        } else {
            membersRecyclerView.setVisibility(View.GONE);
            noInformation1.setVisibility(View.VISIBLE);
        }
    }

    private void initEventsAdapter(ClubDetailsObj clubDetailsObj) {
        List<EventMainInfo> eventsList = clubDetailsObj.getEvents();
        List<EventAdapterModel> localEventsList = new ArrayList<>();
        for (EventMainInfo event : eventsList) {
            localEventsList.add(new EventAdapterModel(event.getId(), event.getTitle(), event.getLocatia(), event.getDate(), false, false,false));
        }
        if (localEventsList.size() != 0) {
            eventsAdapter = new EventAdapter(localEventsList, getApplicationContext(), ClubDetailsActivity.this);
            eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            eventsRecyclerView.setAdapter(eventsAdapter);
        } else {
            eventsRecyclerView.setVisibility(View.GONE);
            noInformation2.setVisibility(View.VISIBLE);
        }

    }

    private void initCoachDetails(ClubDetailsObj clubDetailsObj) {
        String coachFirstName = clubDetailsObj.getOwnerInfo().getIdOwner().getCoachFirstName();
        String coachLastName = clubDetailsObj.getOwnerInfo().getIdOwner().getCoachLastName();
        String coachAgeString = String.valueOf(clubDetailsObj.getOwnerInfo().getIdOwner().getAge());
        coachName.setText(coachFirstName + " " + coachLastName);
        coachAge.setText(coachAgeString + " " + getResources().getString(R.string.years));
        List<OwnerInfo> coachOwnedClubs = clubDetailsObj.getOwnedClubs();
        StringBuilder ownedString = new StringBuilder();
        for (OwnerInfo ownedItem : coachOwnedClubs) {
            ownedString.append(ownedItem.getClubName() + " ");
        }
        ownedClubs.setText(ownedString);
    }
}