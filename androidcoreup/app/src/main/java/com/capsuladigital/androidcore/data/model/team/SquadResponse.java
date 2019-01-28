package com.capsuladigital.androidcore.data.model.team;

import com.capsuladigital.androidcore.data.model.team.TeamPlayer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SquadResponse {
    private List<TeamPlayer> squad;
    private List<TeamPlayer> keepers;
    private List<TeamPlayer> defenders;
    private List<TeamPlayer> midfielders;
    private List<TeamPlayer> attackers;

    public SquadResponse(List<TeamPlayer> squad) {
        this.squad = squad;
        setSquadPerRole(squad);
    }

    public void setSquadPerRole(List<TeamPlayer> squad) {
        keepers = new ArrayList<>();
        defenders = new ArrayList<>();
        midfielders = new ArrayList<>();
        attackers = new ArrayList<>();

        for (int i = 0; i < squad.size(); i++) {
            int role = squad.get(i).getRole();
            if (role == 1) {
                keepers.add(squad.get(i));
            } else if (role == 2) {
                defenders.add(squad.get(i));
            } else if (role == 3) {
                midfielders.add(squad.get(i));
            } else if (role == 4) {
                attackers.add(squad.get(i));
            }
        }
    }

    public List<TeamPlayer> getSquad() {
        return squad;
    }

    public List<TeamPlayer> getKeepers() {
        return keepers;
    }

    public List<TeamPlayer> getDefenders() {
        return defenders;
    }

    public List<TeamPlayer> getMidfielders() {
        return midfielders;
    }

    public List<TeamPlayer> getAttackers() {
        return attackers;
    }
}
