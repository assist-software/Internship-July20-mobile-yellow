package com.example.sportsclubmanagement.screens.addworkouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.utils.Validations;

public class AddWorkoutsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Spinner event, workoutEffectiveness;
    ImageView notificationIcon;
    AdapterView.OnItemSelectedListener spinnerListener;
    Button saveWorkout;
    EditText workoutDuration, heartRate, calories, avSpeed, distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workouts);

        initListener();
        initComponents();
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
        event.setAdapter(populateSpinner(AddWorkoutsActivity.this, getResources().getStringArray(R.array.spinnerEvents)));
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
            } else {
                Toast.makeText(AddWorkoutsActivity.this, R.string.invalid_data, Toast.LENGTH_SHORT).show();
            }
        }
    };
}