package com.example.sportsclubmanagement.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.UserLogin;
import com.example.sportsclubmanagement.models.apiModels.Response.Token;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.screens.register.RegisterActivity;
import com.example.sportsclubmanagement.utils.Constants;
import com.example.sportsclubmanagement.utils.Validations;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView regTxt;
    Button logBtn;
    EditText email, pass;
    APIInterface apiInterface;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean isHaveDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeAllElement();
        initListeners();
    }

    private void updatePref(Token token) {
        editor.putString("token", token.getToken());
        editor.putInt("id", token.getUser_id());
        editor.commit();
    }

    private void restUserLogin() {
        UserLogin userLogin = new UserLogin(email.getText().toString(), pass.getText().toString());
        Call<Token> call = apiInterface.userLogin(userLogin);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.code() + "");
                    Token body_resp = response.body();
                    updatePref(body_resp);
                    checkIfUserHaveDetails();
                } else {
                    Log.d("error message", response.message());
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void redirect(boolean toMain) {
        if (toMain) {
            Intent intent_go_home = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent_go_home);
        } else {
            Intent intent_go_home = new Intent(LoginActivity.this, AccountSetupActivity.class);
            startActivity(intent_go_home);
        }
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
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = pref.edit();
    }

    private void initListeners() {
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
                if (checkInputs()) {
                    restUserLogin();
                    if (checkIfUserHaveDetails()) {
                        Intent intent_go_home = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_go_home);
                    } else {
                        Intent intent_go_home = new Intent(LoginActivity.this, AccountSetupActivity.class);
                        startActivity(intent_go_home);
                    }

                    finish();
                }
            }
        });
    }

    private boolean checkIfUserHaveDetails() {

        Call<UserDetails> call = apiInterface.userDetails(pref.getString("token", null), pref.getInt("id", 0));
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.code() + "");
                    UserDetails resp = response.body();
                    if (resp.getGender().equals("M") || resp.getGender().equals("F")) {
                        updateHaveDetails();
                    }
                } else {
                    Log.d("error message", response.message());
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
        return isHaveDetails;
    }

    private void updateHaveDetails() {
        isHaveDetails = true;
    }
}