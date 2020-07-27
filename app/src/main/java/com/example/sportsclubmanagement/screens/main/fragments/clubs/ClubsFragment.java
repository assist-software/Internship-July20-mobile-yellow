package com.example.sportsclubmanagement.screens.main.fragments.clubs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.clubdetails.ClubDetailsActivity;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapter;
import com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter.ClubAdapterListener;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubsFragment extends Fragment implements ClubAdapterListener {

    private RecyclerView newClubRecyclerView, joinedClubRecyclerView, pendingClubRecyclerView;
    private ClubAdapter newClubAdapter, joinedClubAdapter, pendingClubAdapter;
    private APIInterface apiInterface;
    private List<Clubs> clubsList, newClubList, pendingClubList, joinedClubList;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clubs, container, false);
        initComp(view);
        requestClubs();
        return view;
    }

    private void initComp(View view) {
        newClubRecyclerView = view.findViewById(R.id.newClub_recyclerView);
        joinedClubRecyclerView = view.findViewById(R.id.joinedClubs_RecyclerView);
        pendingClubRecyclerView = view.findViewById(R.id.pendingClubs_RecyclerView);
        pref = getActivity().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, 0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    private void requestClubs() {
        Call<List<Clubs>> call = apiInterface.getAllClubs(pref.getString(Constants.TOKEN, null));
        call.enqueue(new Callback<List<Clubs>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Clubs>> call, Response<List<Clubs>> response) {
                if (response.isSuccessful()) {
                    clubsList = response.body();
                    newClubList = clubsList.stream().filter(club -> (!club.isMember() && !club.isRequested())).collect(Collectors.toList());
                    joinedClubList = clubsList.stream().filter(it -> it.isMember()).collect(Collectors.toList());
                    pendingClubList = (clubsList.stream().filter(it -> it.isRequested()).collect(Collectors.toList()));
                    initAdapter(newClubList, newClubAdapter, newClubRecyclerView, true);
                    initAdapter(joinedClubList, joinedClubAdapter, joinedClubRecyclerView, false);
                    initAdapter(pendingClubList, pendingClubAdapter, pendingClubRecyclerView, false);
                }
            }

            @Override
            public void onFailure(Call<List<Clubs>> call, Throwable t) {
                Toast.makeText(getContext(), "Error in getClubs call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onJoinClick(String clubName, int club_id) {
        joinClubFunction(club_id, this);
    }

    private void joinClubFunction(int club_id, final ClubAdapterListener listener) {
        Call<Void> call = apiInterface.joinClub(pref.getString(Constants.TOKEN, null), club_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Clubs club = newClubList.stream().filter(c -> c.getClubInfo().getId() == club_id).findFirst().orElse(null);
                    if (club != null) {
                        club.setRequested(true);
                        pendingClubList.add(club);
                        newClubList.remove(club);
                        initAdapter(pendingClubList, pendingClubAdapter, pendingClubRecyclerView, false);
                        initAdapter(newClubList, newClubAdapter, newClubRecyclerView, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClubClick(String clubName,int clubId) {
        Intent clubIntent = new Intent(getActivity(), ClubDetailsActivity.class);
        clubIntent.putExtra(Constants.CLUB_NAME, clubName);
        clubIntent.putExtra(Constants.CLUB_ID, clubId);
        startActivity(clubIntent);
    }

    private void initAdapter(List<Clubs> list, ClubAdapter adapter, RecyclerView recyclerView, boolean hasBorder) {
        adapter = new ClubAdapter(list, ClubsFragment.this, hasBorder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}