package com.example.sportsclubmanagement.models.apiModels.Response;

import com.google.gson.annotations.SerializedName;

public class HasEvent {
    @SerializedName("has_events")
    private boolean has;

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public HasEvent(boolean has) {
        this.has = has;
    }
}
