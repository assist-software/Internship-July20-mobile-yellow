package com.example.sportsclubmanagement.screens.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagement.screens.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    TextView loginTextView;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(registerButtonListener);
        loginTextView = findViewById(R.id.login_textView_link);
        loginTextView.setOnClickListener(textLoginListener);

    }

    View.OnClickListener textLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    };

    View.OnClickListener registerButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent registerIntent = new Intent(getApplicationContext(), AccountSetupActivity.class);
            startActivity(registerIntent);
            finish();
        }
    };
}