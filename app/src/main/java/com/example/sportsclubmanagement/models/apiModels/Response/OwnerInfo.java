package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class OwnerInfo {
    @SerializedName("name")
    private String clubName;
    @SerializedName("id_Owner")
    private IdOwner idOwner;

    public OwnerInfo(String clubName, IdOwner idOwner) {
        this.clubName = clubName;
        this.idOwner = idOwner;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public IdOwner getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(IdOwner idOwner) {
        this.idOwner = idOwner;
    }
}
