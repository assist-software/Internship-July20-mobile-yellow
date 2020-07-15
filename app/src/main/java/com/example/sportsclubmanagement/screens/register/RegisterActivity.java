package com.example.sportsclubmanagement.screens.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagement.screens.login.LoginActivity;
import com.example.sportsclubmanagement.utils.Validations;

public class RegisterActivity extends AppCompatActivity {
    TextView loginTextView;
    Button registerBtn;
    EditText name,email,pass,confirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        registerBtn = findViewById(R.id.register_btn);
        loginTextView = findViewById(R.id.login_textView_link);
        name = findViewById(R.id.name_editText);
        email = findViewById(R.id.email_editText);
        pass = findViewById(R.id.password_editText);
        confirmPass = findViewById(R.id.confirm_password_editText);
    }

    private void initListeners() {
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), AccountSetupActivity.class);
                if(checkInfo()){
                startActivity(registerIntent);
                finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"Invalid data",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInfo() {
        return Validations.nameValidation(name.getText().toString()) && Validations.emailValidation(email.getText().toString()) &&
                Validations.passwordValidation(pass.getText().toString()) && Validations.confirmPasswordValidation(pass.getText().toString(),confirmPass.getText().toString());
    }

}