package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.data.model.team.TeamEdit;
import com.capsuladigital.androidcore.data.model.team.TeamsEditPost;
import com.capsuladigital.androidcore.data.model.team.TeamsResponse;
import com.capsuladigital.androidcore.data.repository.local.db.UserSQLiteOpenHelper;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.PersonRequest;
import com.capsuladigital.androidcore.data.repository.remote.request.TeamsRequest;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 14/05/2018.
 */

public class EditUserTeamsPresenter implements EditUserTeamsContract.Presenter {
    private Context context;
    private EditUserTeamsContract.View view;
    private SessionManager session;

    public EditUserTeamsPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(EditUserTeamsContract.View view) {
        this.view = view;
    }

    @Nullable
    private EditUserTeamsContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void editFavTeams(final List<Team> teams) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        ArrayList<TeamEdit> teamsPost = new ArrayList<>();
        for (Team team : teams) {
            if (team.getStatus() != -2) {
                teamsPost.add(new TeamEdit(team.getId(), team.getStatus()));
                Log.e("gg", team.getId() + " " + team.getStatus());
            }
        }
        TeamsEditPost finalTeams = new TeamsEditPost(teamsPost);

        SessionManager sessionManager = SessionManager.getInstance(context);
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);

        Call<JsonObject> call = personRequest.editTeams(sessionManager.getKeyToken(), finalTeams);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    int status = jsonObject.get("status").getAsInt();
                    //Print status. Oh yeah!
                    Log.e("status", status + "");
                    switch (status) {
                        case 1:
                            UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                                    "user", null, 1);
                            SQLiteDatabase db = user.getWritableDatabase();
                            db.execSQL("delete from TEAMS");
                            for (Team team : teams) {
                                if (team.getStatus() == 0 || team.getStatus() == 1) {
                                    db.execSQL(
                                            "INSERT INTO TEAMS Values('"
                                                    + team.getId()
                                                    + "','"
                                                    + team.getName()
                                                    + "','" +
                                                    team.getIcon()
                                                    + "')");
                                }
                            }

                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSuccessToast();
                                getView().launchProfile();
                            }
                            break;
                        case 2:
                            Log.e("error",  jsonObject.get("error").getAsString());
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showWSError();
                            }

                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }


            }
        });
    }

    @Override
    public void getAllTeams() {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        TeamsRequest teamsRequest = ServiceGenerator.createService(TeamsRequest.class);
        Call<TeamsResponse> call = teamsRequest.getAllTeams(Locale.getDefault().getLanguage());
        call.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(Call<TeamsResponse> call, Response<TeamsResponse> response) {
                if (response.isSuccessful()) {
                    TeamsResponse teamResponse = response.body();
                    int status = teamResponse.getStatus();
                    //Print status. Oh yeah!
                    Log.e("status", status + "");
                    switch (status) {
                        case 1:
                            if (isAttached()) {
                                getView().setTeams(teamResponse.getTeams());
                                getView().hideLoadingDialog();
                            }
                            break;
                        case 2:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showWSError();
                            }
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamsResponse> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }
            }
        });
    }

}
