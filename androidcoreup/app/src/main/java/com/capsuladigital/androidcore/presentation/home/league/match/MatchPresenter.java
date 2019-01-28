package com.capsuladigital.androidcore.presentation.home.league.match;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.data.model.match.MatchesResponse;
import com.capsuladigital.androidcore.data.model.team.TeamsResponse;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.MatchRequest;
import com.capsuladigital.androidcore.data.repository.remote.request.TeamsRequest;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchPresenter implements MatchContract.Presenter {
    private MatchContract.View view;
    private Context context;
    private final String TAG = MatchPresenter.class.getSimpleName();
    public MatchPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onViewAttach(MatchContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettach() {
        view = null;
    }

    @Nullable
    private MatchContract.View getView() {
        return view;
    }

    @Override
    public void getMatches(int idLeague) {
        if (isAttached()) {
            getView().hideWSError();
            getView().hideContent();
        }
        MatchRequest leagueRequest = ServiceGenerator.createServiceSoccerAPI(MatchRequest.class);
        Call<MatchesResponse> call = leagueRequest.getMatches(idLeague, Locale.getDefault().getLanguage());
        call.enqueue(new Callback<MatchesResponse>() {
            @Override
            public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
                if (response.isSuccessful()) {
                    MatchesResponse matchesResponse = response.body();
                    if (matchesResponse.getMatches() != null) {
                        Log.e("Match status", "1");
                        if (isAttached()) {
                            getView().setMatches(matchesResponse.getMatches());
                            getView().showContent();
                            getView().stopRefresh();
                        }
                    } else {
                        if (isAttached()) {
                            getView().stopRefresh();
                            getView().showWSError();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchesResponse> call, Throwable t) {
                if (isAttached()) {
                    getView().stopRefresh();
                    getView().showWSError();
                }
            }
        });
    }

    @Override
    public void getTeams() {

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
                                getView().setTeamsArray(teamResponse.getTeams());
                            }
                            break;
                        case 2:
                            if (isAttached()) {
                                getView().showWSError();
                            }
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamsResponse> call, Throwable t) {
                if (isAttached()) {
                    getView().showConnectionError();
                }
            }
        });

    }

    private boolean isAttached() {
        return getView() != null;
    }

}
