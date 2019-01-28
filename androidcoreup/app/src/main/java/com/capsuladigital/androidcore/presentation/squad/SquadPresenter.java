package com.capsuladigital.androidcore.presentation.squad;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.SquadResponse;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.TeamsRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 19/12/2018.
 */

public class SquadPresenter implements SquadContract.Presenter {
    private SquadContract.View view;
    private Context context;
    private final String TAG = SquadPresenter.class.getSimpleName();

    public SquadPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onViewAttach(SquadContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettach() {
        view = null;
    }

    @Nullable
    private SquadContract.View getView() {
        return view;
    }

    @Override
    public void getTeamSquad(int id) {
        if (isAttached()) {
            getView().hideWSError();
            getView().hideRecycler();
        }
        TeamsRequest teamsRequest = ServiceGenerator.createServiceSoccerAPI(TeamsRequest.class);
        Call<SquadResponse> call = teamsRequest.getTeamSquad(id);
        call.enqueue(new Callback<SquadResponse>() {
            @Override
            public void onResponse(Call<SquadResponse> call, Response<SquadResponse> response) {
                if (response.isSuccessful()) {
                    SquadResponse squadResponse = response.body();
                    if (squadResponse.getSquad() != null) {
                        Log.e("status", "1");
                        squadResponse.setSquadPerRole(squadResponse.getSquad());
                        Log.e(TAG,squadResponse.getSquad().get(0).getName());
                        if (isAttached()) {
                            getView().setKeepers(squadResponse.getKeepers());
                            getView().setDefenders(squadResponse.getDefenders());
                            getView().setMidfielders(squadResponse.getMidfielders());
                            getView().setAttackers(squadResponse.getAttackers());
                            getView().showRecycler();
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
            public void onFailure(Call<SquadResponse> call, Throwable t) {
                if (isAttached()) {
                    getView().stopRefresh();
                    getView().showWSError();
                }
            }
        });
    }

    private boolean isAttached() {
        return getView() != null;
    }

}
