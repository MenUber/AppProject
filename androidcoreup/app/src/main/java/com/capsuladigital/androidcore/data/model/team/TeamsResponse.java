package com.capsuladigital.androidcore.data.model.team;

import java.util.List;

/**
 * Created by lvert on 5/03/2018.
 */

public class TeamsResponse {
    private int status;
    private List<Team> teams;

    public TeamsResponse(int status, List<Team> teams) {
        this.status = status;
        this.teams = teams;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
