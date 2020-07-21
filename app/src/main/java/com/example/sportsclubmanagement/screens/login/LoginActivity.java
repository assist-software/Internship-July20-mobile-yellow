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
import com.example.sportsclubmanagement.models.apiModels.Token;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.screens.register.RegisterActivity;
import com.example.sportsclubmanagement.utils.Validations;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeAllElement();
        initListeners();
    }
    private void restUserLogin(){
       Call<Token> call = apiInterface.user_login(email.getText().toString(),pass.getText().toString());
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d("TAG",response.code()+"");
                Token body_resp = response.body();
                Toast.makeText(LoginActivity.this,body_resp.token,Toast.LENGTH_SHORT).show();
                editor.putString("token",body_resp.token);
                editor.commit();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
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
        pref = getApplicationContext().getSharedPreferences("tokenPref", 0); // 0 - for private mode
        editor = pref.edit();
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
                Intent intent_go_home = new Intent(LoginActivity.this, MainActivity.class);
                if (checkInputs()) {
                    restUserLogin();
                    startActivity(intent_go_home);
                    finish();
                }
            }
        });
    }
}