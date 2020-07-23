package com.example.sportsclubmanagement.screens.myprofile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Request.UserAccountSetup;
import com.example.sportsclubmanagement.models.apiModels.Response.Sports;
import com.example.sportsclubmanagement.models.apiModels.Response.UserDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.utils.Constants;
import com.example.sportsclubmanagement.utils.Validations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    Toolbar profileBar;
    Spinner primarySports, secondarySports;
    AdapterView.OnItemSelectedListener spinnerListener;
    TextView fullName;
    EditText height, weight, age;
    ImageView img_user;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    UserDetails userDetails;
    APIInterface apiInterface;
    Button save_btn;
    List<String> sports;
    List<String> prm, scd;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        //init UI components
        initComp();
        initListeners();
        //fill data about user from server
        restGetUserDetails();
        //get all sporst from server
        getSports();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fill spinners with sports
        //all listeners
        initSpinnerListeners();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initComp() {
        profileBar = findViewById(R.id.myprofile_toolbar);
        primarySports = findViewById(R.id.spinnerPrimarySports);
        secondarySports = findViewById(R.id.spinnerSecondSports);
        img_user = findViewById(R.id.image_user);
        fullName = findViewById(R.id.name_user);
        height = findViewById(R.id.inputHeight);
        weight = findViewById(R.id.inputWeight);
        age = findViewById(R.id.inputAge);
        save_btn = findViewById(R.id.profile_save_changes_btn);
        pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = pref.edit();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sports = new ArrayList<>();
        prm = new ArrayList<>();
        scd = new ArrayList<>();

        //set profile image
        Glide.with(MyProfileActivity.this).load(R.drawable.img_event).apply(RequestOptions.circleCropTransform()).into(img_user);

        //customize toolbar
        profileBar.setTitleTextColor(ContextCompat.getColor(MyProfileActivity.this, R.color.colorWhite));
        profileBar.setTitle("My Profile");
        setSupportActionBar(profileBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void fillUserInfo() {
        fullName.setText(userDetails.getFirstName() + " " + userDetails.getLastName());
        primarySports.setSelection(populateSpinner(getApplicationContext(), prm).getPosition(userDetails.getPrimarySport()));
        secondarySports.setSelection(populateSpinner(getApplicationContext(), scd).getPosition(userDetails.getSecondarySport()));
        height.setText(String.valueOf(userDetails.getHeight()));
        weight.setText(String.valueOf(userDetails.getWeight()));
        age.setText(String.valueOf(userDetails.getAge()));
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
                    Toast.makeText(MyProfileActivity.this, "get sports error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sports>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void restGetUserDetails() {
        Call<UserDetails> call = apiInterface.userDetails(pref.getString("token", null), pref.getInt("id", 0));
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    userDetails = response.body();
                    fillUserInfo();
                } else {
                    Log.d("error message", response.message());
                    Toast.makeText(MyProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void restAccountSetup() {
        UserAccountSetup userAccountSetup = new UserAccountSetup(Integer.parseInt(age.getText().toString()),
                primarySports.getSelectedItem().toString(), secondarySports.getSelectedItem().toString(),
                Integer.parseInt(height.getText().toString()), Double.parseDouble(weight.getText().toString()));
        Call<Void> call = apiInterface.userAccountSetup(pref.getString("token", "nimic"), userAccountSetup, pref.getInt("id", 0));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG_USER_DETAILS", "Account details update successful");
                    Toast.makeText(MyProfileActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG_USER_DETAILS", "Account details didn't update");
                    Toast.makeText(MyProfileActivity.this, "Update error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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

    private void initListeners() {
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputs())
                    restAccountSetup();
                else
                    Toast.makeText(MyProfileActivity.this, "Fill all inputs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInputs() {
        return primarySports.getSelectedItemId() != 0 && secondarySports.getSelectedItemId() != 0 && Validations.weightValidation(weight.getText().toString())
                && Validations.heightValidation(height.getText().toString()) && Validations.ageValidation(age.getText().toString()) && primarySports.getSelectedItemId() != secondarySports.getSelectedItemId();
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