package com.example.sportsclubmanagement.screens.accountsetup;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Response.Sports;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.main.MainActivity;
import com.example.sportsclubmanagement.utils.Constants;
import com.example.sportsclubmanagement.utils.Validations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSetupActivity extends AppCompatActivity {
    private Spinner primarySports, secondarySports;
    private Button toHomeBtn;
    private EditText height, weight, age;
    private RadioButton female, male;
    private APIInterface apiInterface;
    private SharedPreferences pref;
    private AdapterView.OnItemSelectedListener spinnerListener;
    private List<String> sports;
    private List<String> prm, scd;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);
        initializeComponents();
        //get all sporst from server
        getSports();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSpinnerListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void dontSetAccoutSetup() {
        editor.putBoolean(Constants.isSetup,false);
        editor.commit();
    }

    private void fillSpinners() {
        //fill primary sports
        prm.add("Primary sport");
        prm.addAll(sports);
        primarySports.setAdapter(populateSpinner(getApplicationContext(), prm));
        //fill secondary spinner
        scd.add("Secondary sport");
        scd.addAll(sports);
        secondarySports.setAdapter(populateSpinner(getApplicationContext(), scd));
    }

    private void initListeners() {
        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent(AccountSetupActivity.this, MainActivity.class);
                if (checkInfo()) {
                    restAccountSetup(createEntity());
                    startActivity(intent_home);
                    editor.putBoolean(Constants.isSetup,true);
                    editor.commit();
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
                && Validations.heightValidation(height.getText().toString()) && Validations.ageValidation(age.getText().toString()) && (female.isChecked() || male.isChecked()) && primarySports.getSelectedItemId() != secondarySports.getSelectedItemId();
    }

    private void getSports() {
        Call<List<Sports>> call = apiInterface.getSports();
        call.enqueue(new Callback<List<Sports>>() {
            @Override
            public void onResponse(Call<List<Sports>> call, Response<List<Sports>> response) {
                if (response.isSuccessful()) {
                    for (Sports s : response.body()) {
                        sports.add(s.getDescrioption());
                    }
                    fillSpinners();
                    Log.d("SpinnerFill", "get sports successful");
                } else {
                    Log.d("SpinnerFill", "get sports error");
                    Toast.makeText(AccountSetupActivity.this, "get sports error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sports>> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void initSpinnerListeners() {
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
        sports = new ArrayList<>();
        prm = new ArrayList<>();
        scd = new ArrayList<>();
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES,MODE_PRIVATE);
        editor = pref.edit();
    }

    private UserAccountSetup createEntity() {
        String gender;
        if (male.isChecked()) {
            gender = Constants.male;
        } else {
            gender = Constants.female;
        }
        return new UserAccountSetup(gender, Integer.parseInt(age.getText().toString()),
                primarySports.getSelectedItem().toString(), secondarySports.getSelectedItem().toString(),
                Integer.parseInt(height.getText().toString()), Double.parseDouble(weight.getText().toString()));
    }

    private void restAccountSetup(UserAccountSetup userAccountSetup) {

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
                if(t!=null)
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private ArrayAdapter<String> populateSpinner(Context context, final List<String> spinnerElements) {
        final ArrayAdapter<String> adapterPrimary = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerElements) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                return true;
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
        return adapterPrimary;
    }
}