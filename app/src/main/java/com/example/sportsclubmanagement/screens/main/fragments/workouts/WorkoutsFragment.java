package com.example.sportsclubmanagement.screens.main.fragments.workouts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.WorkoutAdapterModel;
import com.example.sportsclubmanagement.screens.addworkouts.AddWorkoutsActivity;
import com.example.sportsclubmanagement.screens.main.fragments.workouts.workoutadapter.workoutAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkoutsFragment extends Fragment {
    //TODO: make private
    RecyclerView workoutRecycler;
    workoutAdapter adapter;
    CardView todayWorkout;
    Button addWorkoutBtn;

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

    private void initComp() {
        workoutRecycler = this.getView().findViewById(R.id.recyclerWorkoutsHistory);
        todayWorkout = this.getView().findViewById(R.id.today_work_card);
        addWorkoutBtn = this.getView().findViewById(R.id.addWorkout_btn);
        //fill recycler
        adapter = new workoutAdapter(getMockedList(), getActivity().getApplicationContext());
        workoutRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        workoutRecycler.setAdapter(adapter);
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

    private List<WorkoutAdapterModel> getMockedList() {
        List<WorkoutAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new WorkoutAdapterModel(Calendar.getInstance().getTime(), 2500, 6.25, 555, 999));
        }
        return mocks;
    }
}