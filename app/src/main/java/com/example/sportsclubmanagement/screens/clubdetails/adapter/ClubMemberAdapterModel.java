package com.example.sportsclubmanagement.screens.clubdetails.adapter;

import com.example.sportsclubmanagement.models.ParticipantAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.MembersListItem;

import java.util.List;

public class ClubMemberAdapterModel{
    private List<MembersListItem> membersList;

    public ClubMemberAdapterModel(List<MembersListItem> membersList) {
        this.membersList = membersList;
    }

    public List<MembersListItem> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<MembersListItem> membersList) {
        this.membersList = membersList;
    }
}
