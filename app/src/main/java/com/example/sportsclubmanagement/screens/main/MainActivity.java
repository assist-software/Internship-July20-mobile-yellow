package com.example.sportsclubmanagement.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.ClubsFragment;
import com.example.sportsclubmanagement.screens.main.fragments.events.EventsFragment;
import com.example.sportsclubmanagement.screens.main.fragments.home.HomeFragment;
import com.example.sportsclubmanagement.screens.main.fragments.workouts.WorkoutsFragment;
import com.example.sportsclubmanagement.screens.login.LoginActivity;
import com.example.sportsclubmanagement.screens.myprofile.MyProfileActivity;
import com.example.sportsclubmanagement.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView homeNavigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle homeDrawerToggle;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (homeDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        toolbar = findViewById(R.id.home_tool);
        drawerLayout = findViewById(R.id.home_drawer_layout);
        homeNavigationView = findViewById(R.id.home_nav_view);
        bottomNav = findViewById(R.id.bottom_navigation);
        homeDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        homeDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorWhite));
        drawerLayout.addDrawerListener(homeDrawerToggle);
        homeDrawerToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.home);
        toolbar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit(); //show home fragment first time you enter the activity
        homeNavigationView.setNavigationItemSelectedListener(this);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        SharedPreferences pref = getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        if(pref.contains(Constants.TOKEN)){
            Toast.makeText(this, pref.getString(Constants.TOKEN, null), Toast.LENGTH_SHORT).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.bottom_nav_home:
                            selectedFragment = new HomeFragment();
                            setToolbarStatus(toolbar, true, getResources().getString(R.string.home));
                        break;
                        case R.id.bottom_nav_clubs:
                            selectedFragment = new ClubsFragment();
                            setToolbarStatus(toolbar, false, getResources().getString(R.string.clubs));
                            break;
                        case R.id.bottom_nav_events:
                            selectedFragment = new EventsFragment();
                            setToolbarStatus(toolbar,false,getResources().getString(R.string.events));
                            break;
                        case R.id.bottom_nav_workouts:
                            selectedFragment = new WorkoutsFragment();
                            setToolbarStatus(toolbar, false, getResources().getString(R.string.workouts));
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    //In switch-ul de mai jos adaugam functionalitatea pentru fiecare buton din meniul lateral din home activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_my_profile:
                Intent my_prof = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(my_prof);
                break;
            case R.id.nav_notification:
                Toast.makeText(this, "Open Notification Screen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_calendar:
                Toast.makeText(this, "Open Calendar Screen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_log_out:
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(Constants.TOKEN);
                editor.commit();
                Intent logOutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logOutIntent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setToolbarStatus(Toolbar toolbar, boolean burgerState, String title){
        TextView title_bar = findViewById(R.id.toolbar_title);
        title_bar.setText(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(burgerState);
        toolbar.setTitle("");
        ImageView img = findViewById(R.id.icon_img);
        if(burgerState){
            img.setVisibility(View.GONE);
        }else{
            img.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.img_event).apply(RequestOptions.circleCropTransform()).into(img);
        }
    }
}