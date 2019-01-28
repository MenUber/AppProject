package com.capsuladigital.androidcore.data.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lvert on 5/03/2018.
 */

public class TeamsEditPost {
    @SerializedName("teams")
    @Expose
    private ArrayList<TeamEdit> teams;

    public TeamsEditPost(ArrayList<TeamEdit> teams) {
        this.teams = teams;
    }

    public ArrayList<TeamEdit> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<TeamEdit> teams) {
        this.teams = teams;
    }
}
