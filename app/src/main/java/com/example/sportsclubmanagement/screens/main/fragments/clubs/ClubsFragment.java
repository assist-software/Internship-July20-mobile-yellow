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
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;

import java.util.ArrayList;
import java.util.List;

public class ClubsFragment extends Fragment implements ClubAdapterListener {

    private List<ClubAdapterModel> clubList = new ArrayList<>();
    private List<ClubAdapterModel> clubList2 = new ArrayList<>();
    private List<ClubAdapterModel> clubList3 = new ArrayList<>();
    private RecyclerView newClubRecyclerView, joinedClubRecyclerView, pendingClubRecyclerView;
    private ClubAdapter newClubAdapter, joinedClubAdapter, pendingClubAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clubs, container, false);
        newClubRecyclerView = view.findViewById(R.id.newClub_recyclerView);
        joinedClubRecyclerView = view.findViewById(R.id.joinedClubs_RecyclerView);
        pendingClubRecyclerView = view.findViewById(R.id.pendingClubs_RecyclerView);


        populateNewClubList();
        populateJoinedClubList();
        populatePendingClubList();

        newClubAdapter = new ClubAdapter(clubList, this, true);
        joinedClubAdapter = new ClubAdapter(clubList2, this, false);
        pendingClubAdapter = new ClubAdapter(clubList3, this, false);

        newClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        joinedClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newClubRecyclerView.setAdapter(newClubAdapter);
        joinedClubRecyclerView.setAdapter(joinedClubAdapter);
        pendingClubRecyclerView.setAdapter(pendingClubAdapter);

        return view;
    }

    private void populateNewClubList() {
        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Join");
        clubList.add(club);
        club = new ClubAdapterModel("Running", "Join");
        clubList.add(club);
        club = new ClubAdapterModel("Hiking", "Join");
        clubList.add(club);
        club = new ClubAdapterModel("Swimming", "Join");
        clubList.add(club);
//        for (int i = 0; i < 100; i++) {
//            club = new ClubAdapterModel("Running", "Join");
//            clubList.add(club);
//        }
    }
    private void populateJoinedClubList() {
        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Joined");
        clubList2.add(club);
        club = new ClubAdapterModel("Running", "Joined");
        clubList2.add(club);
        club = new ClubAdapterModel("Hiking", "Joined");
        clubList2.add(club);
        club = new ClubAdapterModel("Swimming", "Joined");
        clubList2.add(club);
    }
    private void populatePendingClubList() {
        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Pending");
        clubList3.add(club);
        club = new ClubAdapterModel("Running", "Pending");
        clubList3.add(club);
        club = new ClubAdapterModel("Hiking", "Pending");
        clubList3.add(club);
        club = new ClubAdapterModel("Swimming", "Pending");
        clubList3.add(club);
    }

    @Override
    public void onJoinClick(String clubName) {
        Toast.makeText(this.getContext(), clubName, Toast.LENGTH_SHORT).show();
    }
}