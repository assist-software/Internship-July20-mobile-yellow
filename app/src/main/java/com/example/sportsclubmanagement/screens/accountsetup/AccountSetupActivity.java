package com.example.sportsclubmanagement.screens.accountsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.utils.Constants;
import com.example.sportsclubmanagement.utils.Validations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSetupActivity extends AppCompatActivity {

    Spinner primarySports, secondarySports;
    Button toHomeBtn;
    EditText height, weight, age;
    RadioButton female, male;
    APIInterface apiInterface;
    SharedPreferences pref;
    ArrayAdapter<String> adapterSecondary, adapterPrimary;
    AdapterView.OnItemSelectedListener spinnerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        initializeComponents();
        initListeners();
    }

    private void initListeners() {
        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent(AccountSetupActivity.this, MainActivity.class);
                if (checkInfo()) {
                    restAccountSetup();
                    startActivity(intent_home);
                    finish();
                } else {
                    Toast.makeText(AccountSetupActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        primarySports.setOnItemSelectedListener(spinnerListener);
        secondarySports.setOnItemSelectedListener(spinnerListener);
    }

    private boolean checkInfo() {
        return primarySports.getSelectedItemId() != 0 && secondarySports.getSelectedItemId() != 0 && Validations.weightValidation(weight.getText().toString())
                && Validations.heightValidation(height.getText().toString()) && Validations.ageValidation(age.getText().toString()) && (female.isChecked() || male.isChecked());
    }

    private void initializeComponents() {
        //initialize all
        primarySports = findViewById(R.id.spinnerPrimarySports);
        secondarySports = findViewById(R.id.spinnerSecondSports);
        toHomeBtn = findViewById(R.id.continueBtn);
        height = findViewById(R.id.inputHeight);
        weight = findViewById(R.id.inputWeight);
        age = findViewById(R.id.inputAge);
        female = findViewById(R.id.radioFemale);
        male = findViewById(R.id.radioMale);
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //fill primary spinner
        String[] primarySportsEnum = getResources().getStringArray(R.array.spinnerPrimarySports);
        adapterPrimary = new ArrayAdapter(AccountSetupActivity.this, R.layout.support_simple_spinner_dropdown_item, primarySportsEnum) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorLightGray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));
                }
                return view;
            }

        };
        primarySports.setAdapter(adapterPrimary);

        //fill secondary spinner
        String[] secondarySportsEnum = getResources().getStringArray(R.array.spinnerSecondarySports);
        adapterSecondary = new ArrayAdapter(AccountSetupActivity.this, R.layout.support_simple_spinner_dropdown_item, secondarySportsEnum) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorLightGray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));
                }
                return view;
            }
        };
        secondarySports.setAdapter(adapterSecondary);
    }

    private void restAccountSetup() {
        String gender = "M";
        if (male.isChecked()) {
            gender = "M";
        } else {
            gender = "F";
        }
        UserAccountSetup userAccountSetup = new UserAccountSetup(gender, Integer.parseInt(age.getText().toString()),
                primarySports.getSelectedItem().toString(), secondarySports.getSelectedItem().toString(),
                Integer.parseInt(height.getText().toString()), Double.parseDouble(weight.getText().toString()));
        Call<Void> call = apiInterface.userAccountSetup(pref.getString("token", "nimic"), userAccountSetup, pref.getInt("id", 0));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", String.valueOf(response.code()));
                } else {
                    Log.d("error message", response.message());
                    Toast.makeText(AccountSetupActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }


}