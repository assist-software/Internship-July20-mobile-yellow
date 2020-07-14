package com.example.sportsclubmanagement.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.home.HomeActivity;
import com.example.sportsclubmanagement.screens.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    TextView regTxt ;
    Button logBtn ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeAllElement();
        goToNextScreen();
    }
    void initializeAllElement(){
        regTxt = findViewById(R.id.registerTxt);
        logBtn = findViewById(R.id.loginBtn);
    }
    void goToNextScreen(){
        regTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_reg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_reg);
                finish();
            }
        });
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_go_home = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent_go_home);
                finish();
            }
        });
    }
}