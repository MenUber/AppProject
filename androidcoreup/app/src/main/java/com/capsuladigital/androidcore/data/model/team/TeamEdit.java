package com.capsuladigital.androidcore.data.model.team;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lvert on 6/03/2018.
 */

public class TeamEdit {
 @SerializedName("idTeam")
    int idTeam;
 @SerializedName("action")
    int action;

    public TeamEdit(int idTeam, int action) {
        this.idTeam = idTeam;
        this.action = action;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}


