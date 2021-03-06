package com.example.sportsclubmanagement.screens.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagement.screens.guest.GuestActivity;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashDelay();
    }

    private void splashDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
                if (pref.contains(Constants.TOKEN)) {
                    if (pref.getBoolean(Constants.isSetup, false)) {
                        Intent guestActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(guestActivity);
                    } else {
                        Intent guestActivity = new Intent(SplashActivity.this, AccountSetupActivity.class);
                        startActivity(guestActivity);
                    }
                    finish();
                } else {
                    Intent guestActivity = new Intent(SplashActivity.this, GuestActivity.class);
                    startActivity(guestActivity);
                    finish();
                }
            }
        }, Constants.SPLASH_TIME_OUT);
    }
}