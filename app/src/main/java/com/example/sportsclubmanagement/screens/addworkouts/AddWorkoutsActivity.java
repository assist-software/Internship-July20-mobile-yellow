package com.example.sportsclubmanagement.screens.addworkouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.Workout;
import com.example.sportsclubmanagement.models.apiModels.Response.EventMainInfo;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.utils.Constants;
import com.example.sportsclubmanagement.utils.Validations;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkoutsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner event, workoutEffectiveness;
    private ImageView notificationIcon;
    private AdapterView.OnItemSelectedListener spinnerListener;
    private Button saveWorkout;
    private EditText workoutDuration, heartRate, calories, avSpeed, distance;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private Map<Integer, String> spinnerEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workouts);
        initListener();
        initComponents();
        getListOfEventsForSpinner();
    }

    private void getListOfEventsForSpinner() {
        Call<List<EventMainInfo>> call = apiInterface.getUsersEvents(pref.getString(Constants.TOKEN, null));
        call.enqueue(new Callback<List<EventMainInfo>>() {
            @Override
            public void onResponse(Call<List<EventMainInfo>> call, Response<List<EventMainInfo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        spinnerEventData = new HashMap<>();
                        for (EventMainInfo ev : response.body()) {
                            spinnerEventData.put(ev.getId(), ev.getTitle());
                        }
                        if (spinnerEventData.size() != 0) {
                            ArrayList<String> lst = new ArrayList<>(spinnerEventData.values());
                            lst.add(0, "Event");
                            event.setAdapter(populateSpinner(AddWorkoutsActivity.this, lst.toArray(new String[0])));
                        } else {
                            event.setAdapter(populateSpinner(AddWorkoutsActivity.this, new String[]{"No events available"}));
                        }
                    }
                } else {
                    Log.d("AddWorkoutScreen", "No events response from server");
                    Toast.makeText(getApplicationContext(), Constants.ADD_WORKOUT_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventMainInfo>> call, Throwable t) {

            }
        });
    }

    private void initComponents() {
        saveWorkout = findViewById(R.id.saveWorkout_btn);
        saveWorkout.setOnClickListener(saveWorkoutOnCLickListener);
        workoutDuration = findViewById(R.id.duration_editText);
        heartRate = findViewById(R.id.heartRate_editText);
        calories = findViewById(R.id.calories_editText);
        avSpeed = findViewById(R.id.avspeed_editText);
        distance = findViewById(R.id.distance_editText);
        toolbar = findViewById(R.id.home_tool_bar);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //remove the default title from the action bar
        toolbar.setTitle(R.string.workouts);
        toolbar.setTitleTextColor(ContextCompat.getColor(AddWorkoutsActivity.this, R.color.colorWhite));
        //add back button to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //change color for toolbar back icon programmatically
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        event = findViewById(R.id.spinnerEvent);
        workoutEffectiveness = findViewById(R.id.spinnerWorkoutEffectiveness);
        workoutEffectiveness.setAdapter(populateSpinner(AddWorkoutsActivity.this, getResources().getStringArray(R.array.spinnerWorkoutEfectiveness)));
        event.setOnItemSelectedListener(spinnerListener);
        workoutEffectiveness.setOnItemSelectedListener(spinnerListener);

        notificationIcon = findViewById(R.id.notification_icon);
        notificationIcon.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean checkInfo() {
        return event.getSelectedItemId() != 0 && workoutEffectiveness.getSelectedItemId() != 0
                && Validations.workoutDurationValidation(workoutDuration.getText().toString())
                && Validations.heartRateValidation(heartRate.getText().toString())
                && Validations.caloriesValidation(calories.getText().toString())
                && Validations.avSpeedValidation(avSpeed.getText().toString())
                && Validations.workoutDistanceValidation(distance.getText().toString());
    }

    private ArrayAdapter<String> populateSpinner(Context context, final String[] spinnerElements) {
        final ArrayAdapter<String> adapterPrimary = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerElements) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorLightGray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));
                }
                return view;
            }

        };
        return adapterPrimary;
    }

    private void initListener() {
        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    private View.OnClickListener saveWorkoutOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkInfo()) {
                Toast.makeText(AddWorkoutsActivity.this, R.string.saved_changes, Toast.LENGTH_SHORT).show();

                addWorkout(collectInfoWorkout());
                finish();
            } else {
                Toast.makeText(AddWorkoutsActivity.this, R.string.invalid_data, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Workout collectInfoWorkout() {
        for(Map.Entry<Integer,String> entry : spinnerEventData.entrySet()){
            if(entry.getValue().equals(event.getSelectedItem())){
                return new Workout(entry.getKey(), Integer.parseInt(workoutDuration.getText().toString()),
                        Integer.parseInt(distance.getText().toString()), Integer.parseInt(heartRate.getText().toString()),
                        Integer.parseInt(calories.getText().toString()), (int) workoutEffectiveness.getSelectedItemId(), Double.parseDouble(avSpeed.getText().toString()));
            }
        }
        return  null;
    }

    private void addWorkout(Workout workout) {
        Call<Void> call = apiInterface.addWorkout(pref.getString("token", null), workout);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("EventDetails", "Workout was added successful");
                } else {
                    Log.d("EventDetails", "Error added workout");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t != null)
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}