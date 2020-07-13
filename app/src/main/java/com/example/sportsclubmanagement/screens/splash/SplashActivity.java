package com.example.sportsclubmanagement.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.guest.GuestActivity;

public class SplashActivity extends AppCompatActivity {
    private static int Splash_Time_Out = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent guestActivity = new Intent(SplashActivity.this, GuestActivity.class);
                startActivity(guestActivity);
                finish();
            }
        }, Splash_Time_Out);
    }
}