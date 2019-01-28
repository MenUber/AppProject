package com.capsuladigital.androidcore.data.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamPlayer {

    @SerializedName("idPlayer")
    @Expose
    private int id;
    @SerializedName("playerName")
    @Expose
    private String name;
    @SerializedName("playerLastName")
    @Expose
    private String lastName;
    @SerializedName("playerRole")
    @Expose
    private int role;
    @SerializedName("playerImage")
    @Expose
    private String photo;

    public TeamPlayer(int id, String name, String lastName, int role, String photo) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
