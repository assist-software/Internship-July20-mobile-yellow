package com.example.sportsclubmanagement.screens.eventdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ParticipantAdapterModel;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapter;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity implements ParticipantAdapterListener {
    private Toolbar event_toolbar;
    private TextView title_event;

    private RecyclerView participantRecycle;
    private ParticipantAdapter participantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initComp();
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
        participantAdapter = new ParticipantAdapter(getMockedList(),getApplicationContext(), this, true);
        participantRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        participantRecycle.setAdapter(participantAdapter);
    }

    private List<ParticipantAdapterModel> getMockedList() {
        List<ParticipantAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new ParticipantAdapterModel("Hehe boy"));
        }
        return mocks;
    }

    @Override
    public void onEventClick(ParticipantAdapterModel participant) {
    }
}