package com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter;

public interface ClubAdapterListener {
    void onJoinClick(String clubName, int clubId);
    void onClubClick(String clubName, int clubId);
}
