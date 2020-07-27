package com.example.sportsclubmanagement.screens.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.myprofile.MyProfileActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initComp();
    }

    private void initComp() {
        calendar = findViewById(R.id.calendar);
        customizeCalendar();
        
        toolbar = findViewById(R.id.calendar_toolbar);
        //customize toolbar
        toolbar.setTitleTextColor(ContextCompat.getColor(CalendarActivity.this, R.color.colorWhite));
        toolbar.setTitle("Calendar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void customizeCalendar() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        calendar.setShownWeekCount(6);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setDate(today.getTime());
        Calendar minDayOfCalendar = Calendar.getInstance();
        Calendar maxDayOfCalendar = Calendar.getInstance();
        minDayOfCalendar.add(Calendar.DAY_OF_YEAR,-17);
        maxDayOfCalendar.add(Calendar.DAY_OF_YEAR,17);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    //TODO: pun toate evenimentele care is in data selectata
            }
        });
    }
}