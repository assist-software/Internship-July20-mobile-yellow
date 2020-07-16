package com.example.sportsclubmanagement.screens.accountsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.home.HomeActivity;
import com.example.sportsclubmanagement.utils.Validations;

public class AccountSetupActivity extends AppCompatActivity {

    Spinner primarySports, secondarySports;
    Button toHomeBtn;
    EditText height, weight, age;
    RadioButton female, male;

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
                Intent intent_home = new Intent(AccountSetupActivity.this, HomeActivity.class);
                if (checkInfo()) {
                    startActivity(intent_home);
                    finish();
                } else {
                    Toast.makeText(AccountSetupActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        //fill primary spinner
        String[] primarySportsEnum = getResources().getStringArray(R.array.spinnerPrimarySports);
        final ArrayAdapter<String> adapterPrimary = new ArrayAdapter(AccountSetupActivity.this, R.layout.support_simple_spinner_dropdown_item, primarySportsEnum) {
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
        ArrayAdapter<String> adapterSecondary = new ArrayAdapter(AccountSetupActivity.this, R.layout.support_simple_spinner_dropdown_item, secondarySportsEnum) {
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
}