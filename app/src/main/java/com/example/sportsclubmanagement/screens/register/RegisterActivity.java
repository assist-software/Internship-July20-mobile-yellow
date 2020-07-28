package com.example.sportsclubmanagement.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.UserRegister;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.login.LoginActivity;
import com.example.sportsclubmanagement.utils.Validations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView loginTextView;
    Button registerBtn;
    EditText name, email, pass, confirmPass;
    APIInterface apiInterface;

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
        apiInterface = APIClient.getClient().create(APIInterface.class);
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
                Intent registerIntent = new Intent(getApplicationContext(), LoginActivity.class);
                if (checkInfo()) {
                    restUserRegister(createEntity());
                    startActivity(registerIntent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInfo() {
        return Validations.nameValidation(name.getText().toString()) && Validations.emailValidation(email.getText().toString()) &&
                Validations.passwordValidation(pass.getText().toString()) && Validations.confirmPasswordValidation(pass.getText().toString(), confirmPass.getText().toString());
    }

    private UserRegister createEntity() {
        String[] first_last = name.getText().toString().split(" ");
        return new UserRegister(first_last[0], first_last[first_last.length-1], email.getText().toString(), pass.getText().toString());
    }

    private void restUserRegister(UserRegister userRegister) {
        Call<Void> call = apiInterface.userRegister(userRegister);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.code() + "");
                } else {
                    Log.d("error message", response.message());
                    Toast.makeText(RegisterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}