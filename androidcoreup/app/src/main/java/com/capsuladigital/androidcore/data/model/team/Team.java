package com.capsuladigital.androidcore.data.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Team {

    @SerializedName("teamName")
    @Expose
    private String name;
    @SerializedName("teamIcon")
    @Expose
    private String icon;
    @SerializedName("idTeam")
    @Expose
    private int id;

    private int status;

    public Team(String name, String icon, int id) {
        this.name = name;
        this.icon = icon;
        this.id = id;
        this.status = -2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
