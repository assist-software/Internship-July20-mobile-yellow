package com.example.sportsclubmanagement.models.apiModels.Response;

import com.example.sportsclubmanagement.models.ClubAdapterModel;

public class Clubs {
    private int id;
    private String name;

    public String getClubStats(){
        return clubStats;
    }

    public void setClubStats(String clubStats) {
        this.clubStats = clubStats;
    }

    private String clubStats;

    public Clubs(int id, String name, String clubStatus) {
        this.id = id;
        this.name = name;
        this.clubStats = clubStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
