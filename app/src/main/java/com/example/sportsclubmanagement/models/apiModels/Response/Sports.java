package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class Sports {
    @SerializedName("description")
    private String descrioption;

    public String getDescrioption() {
        return descrioption;
    }

    public void setDescrioption(String descrioption) {
        this.descrioption = descrioption;
    }

    public Sports(String descrioption) {
        this.descrioption = descrioption;
    }
}
