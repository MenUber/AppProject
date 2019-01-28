package com.capsuladigital.androidcore.data.model.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {
    @SerializedName("idMatch")
    @Expose
    private int id;
    @SerializedName("groupName")
    @Expose
    private String group;
    @SerializedName("idTeamLocal")
    @Expose
    private int idLocal;
    @SerializedName("idTeamVisit")
    @Expose
    private int idVisitor;
    @SerializedName("teamLocal")
    @Expose
    private String local;
    @SerializedName("teamVisit")
    @Expose
    private String visitor;
    @SerializedName("teamLocalImage")
    @Expose
    private String local_shield;
    @SerializedName("teamVisitImage")
    @Expose
    private String visitor_shield;
    @SerializedName("matchDateText")
    @Expose
    private String dateText;
    @SerializedName("matchDate")
    @Expose
    private String date;
    @SerializedName("matchTime")
    @Expose
    private String hour;
    @SerializedName("stadiumName")
    @Expose
    private String stadium;
    @SerializedName("matchLocalGoals")
    @Expose
    private String local_goals;
    @SerializedName("matchVisitGoals")
    @Expose
    private String visitor_goals;
    @SerializedName("matchStatus")
    @Expose
    private String status;

    public Match(int id, String group, int idLocal, int idVisitor, String local, String visitor,
                 String local_shield, String visitor_shield, String dateText, String date,
                 String hour, String stadium, String local_goals, String visitor_goals,
                 String status) {
        this.id = id;
        this.group = group;
        this.idLocal = idLocal;
        this.idVisitor = idVisitor;
        this.local = local;
        this.visitor = visitor;
        this.local_shield = local_shield;
        this.visitor_shield = visitor_shield;
        this.dateText = dateText;
        this.date = date;
        this.hour = hour;
        this.stadium = stadium;
        this.local_goals = local_goals;
        this.visitor_goals = visitor_goals;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public int getLocalId() {
        return idLocal;
    }

    public int getVisitorId() {
        return idVisitor;
    }

    public String getLocal() {
        return local;
    }

    public String getVisitor() {
        return visitor;
    }

    public String getLocalShield() {
        return local_shield;
    }

    public String getVisitorShield() {
        return visitor_shield;
    }

    public String getDateText() {
        return dateText;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getStadium() {
        return stadium;
    }

    public String getLocalGoals() {
        return local_goals;
    }

    public String getVisitorGoals() {
        return visitor_goals;
    }

    public String getStatus() {
        return status;
    }

}
