package com.capsuladigital.androidcore.presentation.sign_up.improve_xperience;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.data.model.person.PersonSignUpPost;
import com.capsuladigital.androidcore.data.model.team.TeamsResponse;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.PersonRequest;
import com.capsuladigital.androidcore.data.repository.remote.request.TeamsRequest;
import com.google.gson.JsonObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 5/04/2018.
 */

public class ImproveXperiencePresenter implements ImproveXperienceContract.Presenter {
    private Context context;
    private ImproveXperienceContract.View view;
    private SessionManager session;

    public ImproveXperiencePresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(ImproveXperienceContract.View view) {
        this.view = view;

    }

    @Nullable
    private ImproveXperienceContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void signUp(PersonSignUpPost person) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.signUp(person);
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
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSuccessToast();
                            }
                            break;
                        case 2:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showAlreadyRegisteredUser();
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
