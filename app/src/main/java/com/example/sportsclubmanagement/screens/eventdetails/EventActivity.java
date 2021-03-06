package com.example.sportsclubmanagement.screens.eventdetails;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.example.sportsclubmanagement.models.apiModels.Response.EventParticipant;
import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutForEventParticipation;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapter;
import com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant.ParticipantAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity implements ParticipantAdapterListener {
    private Toolbar event_toolbar;
    private TextView title_event, dateEvent, timeEvent, locationEvent, descriptionEvent, titleTextEvent,
            textHintParticipations, noInformationTextView, eventState,metric;
    private ImageView imgEvent;
    private RecyclerView participantRecycle;
    private ParticipantAdapter participantAdapter;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Map<ParticipantAdapterModel, WorkoutForEventParticipation> participantResult;
    private int eventID;
    private boolean isParticipant = false;
    private BarChart barChart;
    private RadioButton heartR, caloriesR, speedR, distR;
    private List<String> listPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initComp();
        getEventDetails();
        initListener();
        initChart();
    }

    private void initChart() {
        BarDataSet barDataSet = new BarDataSet(getData(), "");
        listPart = new ArrayList<>();
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(new int[]{Color.BLACK});
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(listPart);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(barData);
        barChart.setFitBars(false);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    private ArrayList getData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        if (heartR.isChecked()) {
            for (Map.Entry<ParticipantAdapterModel, WorkoutForEventParticipation> entry : participantResult.entrySet()) {
                if (entry.getKey().isStatus()) {
                    entries.add(new BarEntry(entries.size(), entry.getValue().getBpm()));
                    listPart.add(entry.getKey().getFullName());
                }

            }
        }
        if (caloriesR.isChecked()) {
            for (Map.Entry<ParticipantAdapterModel, WorkoutForEventParticipation> entry : participantResult.entrySet()) {
                if (entry.getKey().isStatus()) {
                    entries.add(new BarEntry(entries.size(), entry.getValue().getCalories()));
                    listPart.add(entry.getKey().getFullName());
                }
            }
        }
        if (speedR.isChecked()) {
            for (Map.Entry<ParticipantAdapterModel, WorkoutForEventParticipation> entry : participantResult.entrySet()) {
                if (entry.getKey().isStatus()) {
                    entries.add(new BarEntry(entries.size(), (float) (entry.getValue().getAvgSpeed())));
                    listPart.add(entry.getKey().getFullName());
                }
            }
        }
        if (distR.isChecked()) {
            for (Map.Entry<ParticipantAdapterModel, WorkoutForEventParticipation> entry : participantResult.entrySet()) {
                if (entry.getKey().isStatus()) {
                    entries.add(new BarEntry(entries.size(), (float) entry.getValue().getDistance_travelled()));
                    listPart.add(entry.getKey().getFullName());
                }
            }
        }
        return entries;
    }

    private void initListener() {
        eventState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinUnjoin();
            }
        });
    }

    private void joinUnjoin() {
        Call<Void> call = apiInterface.joinEvent(pref.getString(Constants.TOKEN, null), eventID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isParticipant = !isParticipant;
                    setState(isParticipant);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getEventDetails() {
        Call<EventDetails> call = apiInterface.getEventDetails(pref.getString("token", null), eventID);
        call.enqueue(new Callback<EventDetails>() {
            @Override
            public void onResponse(Call<EventDetails> call, Response<EventDetails> response) {
                if (response.isSuccessful()) {
                    EventDetails eventDetails = response.body();
                    fillEventDetailsOnPage(eventDetails);
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

    private void fillEventDetailsOnPage(EventDetails eventDetails) {
        title_event.setText(eventDetails.getTitle());
        locationEvent.setText(eventDetails.getLocation());
        String[] timesOfEvent = eventDetails.getTimeEvent().split(":");
        timeEvent.setText(timesOfEvent[0] + ":" + timesOfEvent[1]);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        dateEvent.setText(formatter.format(eventDetails.getDataEvent()));
        descriptionEvent.setText(eventDetails.getDescription());
        if (eventDetails.getDescription().split(".").length >= 1)
            titleTextEvent.setText(eventDetails.getDescription().split(".")[0]);
        else {
            titleTextEvent.setText(eventDetails.getDescription());
        }
        //for recycler with participants
        if (eventDetails.getParticipants().size() != 0) {
            participantResult = new HashMap<>();
            List<EventParticipant> participantsFromBackend = eventDetails.getParticipants();
            for (EventParticipant participant : participantsFromBackend) {
                if (participant.getId() == pref.getInt("id", 0)) {
                    isParticipant = true;
                }
                participantResult.put(new ParticipantAdapterModel(participant.getFirstName() + " " + participant.getLastName()), participant.getWorkout());

            }
            setState(isParticipant);
            participantAdapter = new ParticipantAdapter(new ArrayList<>(participantResult.keySet()), getApplicationContext(), this, true);
            participantRecycle.setAdapter(participantAdapter);
        } else {
            participantRecycle.setVisibility(View.GONE);
            textHintParticipations.setVisibility(View.GONE);
            noInformationTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setState(boolean isParticipant) {
        if (isParticipant) {
            eventState.setText("Joined");
            eventState.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
            eventState.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkGray));
        } else {
            eventState.setText("Join");
            eventState.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkGray));
            eventState.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initComp() {
        event_toolbar = findViewById(R.id.event_toolbar);
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
        eventID = getIntent().getIntExtra("eventId", -1);
        dateEvent = findViewById(R.id.text_calendar);
        timeEvent = findViewById(R.id.text_ora);
        locationEvent = findViewById(R.id.text_loc);
        imgEvent = findViewById(R.id.img_event);
        title_event = findViewById(R.id.title_event);
        textHintParticipations = findViewById(R.id.text_hint_participants);
        descriptionEvent = findViewById(R.id.text_body);
        titleTextEvent = findViewById(R.id.text_head);
        eventState = findViewById(R.id.state_event);
        barChart = findViewById(R.id.barchart);
        heartR = findViewById(R.id.heartRadio);
        caloriesR = findViewById(R.id.caloriesRadio);
        speedR = findViewById(R.id.speedRadio);
        distR = findViewById(R.id.distanceRadio);
        metric = findViewById(R.id.metric);
        initRadioList();
    }

    private void initRadioList() {
        heartR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                heartR.setChecked(true);
                caloriesR.setChecked(false);
                speedR.setChecked(false);
                distR.setChecked(false);
                metric.setText("Heart rate");
                initChart();
            }
        });
        caloriesR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                heartR.setChecked(false);
                caloriesR.setChecked(true);
                speedR.setChecked(false);
                distR.setChecked(false);
                metric.setText("Calories");
                initChart();
            }
        });
        speedR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                heartR.setChecked(false);
                caloriesR.setChecked(false);
                speedR.setChecked(true);
                distR.setChecked(false);
                metric.setText("Av.speed");
                initChart();
            }
        });
        distR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                heartR.setChecked(false);
                caloriesR.setChecked(false);
                speedR.setChecked(false);
                distR.setChecked(true);
                metric.setText("Distance");
                initChart();
            }
        });
    }

    @Override
    public void onEventClick(ParticipantAdapterModel participant) {
    }
}