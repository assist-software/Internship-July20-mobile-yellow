package com.example.sportsclubmanagement.screens.main.fragments.clubs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class ClubsFragment extends Fragment implements ClubAdapterListener{

    private List<ClubAdapterModel> clubList = new ArrayList<>();
    private RecyclerView clubRecyclerView;
    private ClubAdapter clubAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_clubs, container, false);
        clubRecyclerView = view.findViewById(R.id.recycler_view);

        clubAdapter = new ClubAdapter(clubList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        clubRecyclerView.setLayoutManager(mLayoutManager);
        clubRecyclerView.setAdapter(clubAdapter);

        populateClubList();
        return view;
    }

    private void populateClubList() {
        ClubAdapterModel club = new ClubAdapterModel("Cycling");
        clubList.add(club);
        club = new ClubAdapterModel("Running");
        clubList.add(club);
        club = new ClubAdapterModel("Hiking");
        clubList.add(club);
        club = new ClubAdapterModel("Swimming");
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
        clubList.add(club);
    }

    @Override
    public void onJoinClick(String clubName) {
        Toast.makeText(this.getContext(), clubName, Toast.LENGTH_SHORT).show();
    }
}