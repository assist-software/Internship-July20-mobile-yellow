package com.example.sportsclubmanagement.screens.main.fragments.workouts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.WorkoutAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.WorkoutsDetails;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.addworkouts.AddWorkoutsActivity;
import com.example.sportsclubmanagement.screens.main.fragments.workouts.workoutadapter.workoutAdapter;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutsFragment extends Fragment {
    private RecyclerView allWorkoutRecycler,todayWorkouts;
    private Button addWorkoutBtn;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private APIInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComp();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWorkoutsFromBackend();
    }

    private void getWorkoutsFromBackend() {
        Call<List<WorkoutsDetails>> call = apiInterface.getAllWorkout(pref.getString("token", null));
        call.enqueue(new Callback<List<WorkoutsDetails>>() {
            @Override
            public void onResponse(Call<List<WorkoutsDetails>> call, Response<List<WorkoutsDetails>> response) {
                if (response.isSuccessful()) {
                    List<WorkoutAdapterModel> workoutsListForRecycler = new ArrayList<>();
                    for(WorkoutsDetails det : response.body()){
                        workoutsListForRecycler.add(new WorkoutAdapterModel(det.getDate_workout(),det.getTime_workout(),
                                det.getDistance_travelled(),det.getCalories(),det.getBpm()));
                    }
                    if(workoutsListForRecycler.stream().filter(v -> DateUtils.isToday(v.getDate_workout().getTime())).count() !=0){
                        initAWorkoutsRecycler(workoutsListForRecycler.stream().filter(v -> DateUtils.isToday(v.getDate_workout().getTime())).collect(Collectors.toList()), todayWorkouts);
                    }
                    initAWorkoutsRecycler(workoutsListForRecycler, allWorkoutRecycler);
                    Log.d("EventDetails", "Got workouts successful");
                } else {
                    Log.d("EventDetails", "Error getting workouts");
                }
            }

            @Override
            public void onFailure(Call<List<WorkoutsDetails>> call, Throwable t) {
                if(t!=null)
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void initAWorkoutsRecycler(List<WorkoutAdapterModel> workoutsListForRecycler,RecyclerView recyclerView) {
        workoutAdapter adapter = new workoutAdapter(workoutsListForRecycler, getActivity().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void initComp() {
        allWorkoutRecycler = this.getView().findViewById(R.id.recyclerWorkoutsHistory);
        todayWorkouts = this.getView().findViewById(R.id.recyclerTodayWorkout);
        addWorkoutBtn = this.getView().findViewById(R.id.addWorkout_btn);
        pref = getContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    private void initListeners() {
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent i = new Intent(getView().getContext(), AddWorkoutsActivity.class);
                                                 startActivity(i);//in add workout when user click save_btn , app will open this activity again
                                             }
                                         }
        );
    }
}