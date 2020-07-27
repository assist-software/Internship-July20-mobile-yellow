package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class MembersListItem {
    @SerializedName("id_User")
    private IdUser idUser;
    private boolean is_invited;
    private boolean is_requested;
    private boolean is_member;

    public MembersListItem(IdUser idUser, boolean is_invited, boolean is_requested, boolean is_member) {
        this.idUser = idUser;
        this.is_invited = is_invited;
        this.is_requested = is_requested;
        this.is_member = is_member;
    }

    public IdUser getIdUser() {
        return idUser;
    }

    public void setIdUser(IdUser idUser) {
        this.idUser = idUser;
    }

    public boolean isIs_invited() {
        return is_invited;
    }

    public void setIs_invited(boolean is_invited) {
        this.is_invited = is_invited;
    }

    public boolean isIs_requested() {
        return is_requested;
    }

    public void setIs_requested(boolean is_requested) {
        this.is_requested = is_requested;
    }

    public boolean isIs_member() {
        return is_member;
    }

    public void setIs_member(boolean is_member) {
        this.is_member = is_member;
    }
}
