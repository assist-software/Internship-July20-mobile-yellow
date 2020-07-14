package com.example.sportsclubmanagement.screens.guest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.login.LoginActivity;
import com.example.sportsclubmanagement.screens.register.RegisterActivity;

public class GuestActivity extends AppCompatActivity {
    Button logBtn ;
    Button regBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        logBtn = findViewById(R.id.loginBt);
        regBtn = findViewById(R.id.registerBt);

        goToNextScreen();
    }

    private void goToNextScreen(){
        final Intent intent_login = new Intent(this, LoginActivity.class);
        final Intent intent_register = new Intent(this, RegisterActivity.class);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_login);
                finish();
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_register);
                finish();
            }
        });
    }
}