package com.example.sportsclubmanagement.screens.myprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.accountsetup.AccountSetupActivity;

public class MyProfileActivity extends AppCompatActivity {
    Toolbar profileBar;
    Spinner primarySports, secondarySports;
    AdapterView.OnItemSelectedListener spinnerListener;
    ImageView img_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        initComp();
        initListeners();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initComp() {
        profileBar = findViewById(R.id.myprofile_toolbar);
        primarySports = findViewById(R.id.spinnerPrimarySports);
        secondarySports = findViewById(R.id.spinnerSecondSports);
        img_user = findViewById(R.id.image_user);

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

        //fill primary spinner
        String[] primarySportsEnum = getResources().getStringArray(R.array.spinnerPrimarySports);
        primarySports.setAdapter(populateSpinner(getApplicationContext(),primarySportsEnum));

        //fill secondary spinner
        String[] secondarySportsEnum = getResources().getStringArray(R.array.spinnerSecondarySports);
        secondarySports.setAdapter(populateSpinner(getApplicationContext(),secondarySportsEnum));
    }

    private void initListeners() {
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

    private ArrayAdapter<String> populateSpinner(Context context, final String[] spinnerElements) {
        final ArrayAdapter<String> adapterPrimary = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinnerElements) {
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
        return adapterPrimary;
    }
}