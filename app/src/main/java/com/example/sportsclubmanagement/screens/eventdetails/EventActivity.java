package com.example.sportsclubmanagement.screens.eventdetails;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ParticipantAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.EventDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapter;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity implements ParticipantAdapterListener {
    private Toolbar event_toolbar;
    private TextView title_event,dateEvent,timeEvent,locationEvent,descriptionEvent,titleTextEvent;
    private ImageView imgEvent;
    private RecyclerView participantRecycle;
    private ParticipantAdapter participantAdapter;
    private EventDetails eventDetails;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int eventID;
    private List<ParticipantAdapterModel> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initComp();
        getEventDetails();
    }

    private void getEventDetails() {
        Call<EventDetails> call = apiInterface.getEventDetails(pref.getString("token", null),eventID);
        call.enqueue(new Callback<EventDetails>() {
            @Override
            public void onResponse(Call<EventDetails> call, Response<EventDetails> response) {
                if (response.isSuccessful()) {
                    eventDetails = response.body();
                    fillEventDetailsOnPage();
                    Log.d("EventDetails", "Got event details successful");
                } else {
                    Log.d("EventDetails", "Error getting event details");
                }
            }

            @Override
            public void onFailure(Call<EventDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void fillEventDetailsOnPage() {
        title_event.setText(eventDetails.getTitle());
        locationEvent.setText(eventDetails.getLocation());
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        timeEvent.setText(formatterTime.format(eventDetails.getTimeEvent()));
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        dateEvent.setText(formatter.format(eventDetails.getDataEvent()));
        //cand am imaginea de pe back
        //Glide.with(this.getApplicationContext()).load().centerCrop().into(imgEvent);
        descriptionEvent.setText(eventDetails.getDescription());
        titleTextEvent.setText(eventDetails.getDescription().split(".")[0]);
        participants.addAll(eventDetails.getParticipants());
        //for recycler with participants
        participantAdapter = new ParticipantAdapter(participants, getApplicationContext(), this, true);
        participantRecycle.setAdapter(participantAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initComp() {
        event_toolbar = findViewById(R.id.event_toolbar);
        title_event = findViewById(R.id.title_event);
        //change title
        //iau de pe server (pentru test preiau id din intent)
        title_event.setText(getIntent().getStringExtra("event_id"));
        //customize toolbar
        event_toolbar.setTitleTextColor(ContextCompat.getColor(EventActivity.this, R.color.colorWhite));
        event_toolbar.setTitle("Events");
        setSupportActionBar(event_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //fill recycle
        participantRecycle = findViewById(R.id.participantsRecycler);
        participantRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //API
        apiInterface = APIClient.getClient().create(APIInterface.class);
        //shared pref
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = pref.edit();
        //event info component
        eventID = pref.getInt("eventId",-1);
        dateEvent = findViewById(R.id.text_calendar);
        timeEvent = findViewById(R.id.text_ora);
        locationEvent = findViewById(R.id.text_loc);
        imgEvent = findViewById(R.id.img_event);
        participants = new ArrayList<>();
    }

    @Override
    public void onEventClick(ParticipantAdapterModel participant) {
    }
}