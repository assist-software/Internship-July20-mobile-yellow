package com.example.sportsclubmanagement.models;

public class ClubAdapterModel {
    private String clubName;
    private String clubStats;

    public ClubAdapterModel(String clubName, String clubStatus) {
        this.clubName = clubName;
        this.clubStats = clubStatus;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubStats() {
        return clubStats;
    }

    public void setClubStats(String clubStats) {
        this.clubStats = clubStats;
    }
}
