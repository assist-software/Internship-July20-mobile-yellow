package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubDetailsObj {
    @SerializedName("Club details")
    private OwnerInfo ownerInfo;
    @SerializedName("Members")
    private List<MembersListItem> members;
    @SerializedName("Events")
    private List<EventsAvailable> events;
    @SerializedName("Coach clubs")
    private List<OwnerInfo> ownedClubs;

    public ClubDetailsObj(OwnerInfo ownerInfo, List<MembersListItem> members, List<EventsAvailable> events, List<OwnerInfo> ownedClubs) {
        this.ownerInfo = ownerInfo;
        this.members = members;
        this.events = events;
        this.ownedClubs = ownedClubs;
    }

    public OwnerInfo getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(OwnerInfo ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    public List<MembersListItem> getMembers() {
        return members;
    }

    public void setMembers(List<MembersListItem> members) {
        this.members = members;
    }

    public List<EventsAvailable> getEvents() {
        return events;
    }

    public void setEvents(List<EventsAvailable> events) {
        this.events = events;
    }

    public List<OwnerInfo> getOwnedClubs() {
        return ownedClubs;
    }

    public void setOwnedClubs(List<OwnerInfo> ownedClubs) {
        this.ownedClubs = ownedClubs;
    }
}
