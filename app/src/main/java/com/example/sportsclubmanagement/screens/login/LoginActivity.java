package com.example.sportsclubmanagement.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.home.HomeActivity;
import com.example.sportsclubmanagement.screens.register.RegisterActivity;
import com.example.sportsclubmanagement.utils.Validations;

public class LoginActivity extends AppCompatActivity {

    TextView regTxt;
    Button logBtn;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeAllElement();
        initListeners();
    }

    private boolean checkInputs() {
        StringBuilder errors = new StringBuilder("Wrong\n");
        boolean ok = true;
        if (!Validations.passwordValidation(pass.getText().toString())) {
            ok = false;
            errors.append("-password\n");
        }
        if (!Validations.emailValidation(email.getText().toString())) {
            ok = false;
            errors.append("-email address\n");
        }
        if (!ok) {
            Toast.makeText(LoginActivity.this, errors, Toast.LENGTH_SHORT).show();
        }

        return ok;
    }

    void initializeAllElement() {
        regTxt = findViewById(R.id.registerTxt);
        logBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
    }

    void initListeners() {
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
                if (checkInputs()) {
                    startActivity(intent_go_home);
                    finish();
                }
            }
        });
    }
}