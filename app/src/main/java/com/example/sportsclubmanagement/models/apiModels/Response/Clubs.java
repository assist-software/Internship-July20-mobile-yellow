package com.example.sportsclubmanagement.models.apiModels.Response;

import com.example.sportsclubmanagement.models.apiModels.ClubInfo;
import com.google.gson.annotations.SerializedName;

public class Clubs {
    @SerializedName("id_club")
    private ClubInfo clubInfo;
    @SerializedName("is_invited")
    private boolean isInvited;
    @SerializedName("is_requested")
    private boolean isRequested;
    @SerializedName("is_member")
    private boolean isMember;

    public Clubs() {
    }

    public Clubs(ClubInfo clubInfo, boolean isInvited, boolean isRequested, boolean isMember) {
        this.clubInfo = clubInfo;
        this.isInvited = isInvited;
        this.isRequested = isRequested;
        this.isMember = isMember;
    }

    public ClubInfo getClubInfo() {
        return clubInfo;
    }

    public void setClubInfo(ClubInfo clubInfo) {
        this.clubInfo = clubInfo;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }

    public boolean isRequested() {
        return isRequested;
    }

    public void setRequested(boolean requested) {
        isRequested = requested;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
