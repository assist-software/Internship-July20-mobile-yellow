package com.example.sportsclubmanagement.screens.main.fragments.clubs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.clubdetails.ClubDetailsActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClubsFragment extends Fragment implements ClubAdapterListener {

    //private List<ClubAdapterModel> clubList = new ArrayList<>();
    private List<ClubAdapterModel> clubList2 = new ArrayList<>();
    private List<ClubAdapterModel> clubList3 = new ArrayList<>();
    private RecyclerView newClubRecyclerView, joinedClubRecyclerView, pendingClubRecyclerView;
    private ClubAdapter newClubAdapter, joinedClubAdapter, pendingClubAdapter;
    APIInterface apiInterface;
    List<Clubs> clubsList;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clubs, container, false);
        newClubRecyclerView = view.findViewById(R.id.newClub_recyclerView);
        joinedClubRecyclerView = view.findViewById(R.id.joinedClubs_RecyclerView);
        pendingClubRecyclerView = view.findViewById(R.id.pendingClubs_RecyclerView);

        pref = getActivity().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, 0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        callFunction(this);

//        populateJoinedClubList();
//        populatePendingClubList();

//        joinedClubAdapter = new ClubAdapter(clubList2, this, false);
//        pendingClubAdapter = new ClubAdapter(clubList3, this, false);

//        joinedClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        pendingClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        joinedClubRecyclerView.setAdapter(joinedClubAdapter);
//        pendingClubRecyclerView.setAdapter(pendingClubAdapter);

        return view;
    }

    private void callFunction(final ClubAdapterListener listener){
        Call<List<Clubs>> call = apiInterface.getAllClubs(pref.getString("token", "nimic"));
        call.enqueue(new Callback<List<Clubs>>() {
            @Override
            public void onResponse(Call<List<Clubs>> call, Response<List<Clubs>> response) {
                if (response.isSuccessful()) {
                    clubsList = response.body();
                    newClubAdapter = new ClubAdapter(clubsList, listener, true);
                    newClubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    newClubRecyclerView.setAdapter(newClubAdapter);
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Clubs>> call, Throwable t) {
                Toast.makeText(getContext(), "Error in getClubs call", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void populateJoinedClubList() {
//        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Joined");
//        clubList2.add(club);
//        club = new ClubAdapterModel("Running", "Joined");
//        clubList2.add(club);
//        club = new ClubAdapterModel("Hiking", "Joined");
//        clubList2.add(club);
//        club = new ClubAdapterModel("Swimming", "Joined");
//        clubList2.add(club);
//    }
//
//    private void populatePendingClubList() {
//        ClubAdapterModel club = new ClubAdapterModel("Cycling", "Pending");
//        clubList3.add(club);
//        club = new ClubAdapterModel("Running", "Pending");
//        clubList3.add(club);
//        club = new ClubAdapterModel("Hiking", "Pending");
//        clubList3.add(club);
//        club = new ClubAdapterModel("Swimming", "Pending");
//        clubList3.add(club);
//    }

    @Override
    public void onJoinClick(String clubName) {
        Toast.makeText(this.getContext(), clubName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClubClick(String clubName) {
        Intent clubIntent = new Intent(getActivity(), ClubDetailsActivity.class);
        clubIntent.putExtra(Constants.CLUB_NAME, clubName);
        startActivity(clubIntent);
    }
}