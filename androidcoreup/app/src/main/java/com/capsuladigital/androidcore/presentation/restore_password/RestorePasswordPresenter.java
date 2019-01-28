package com.capsuladigital.androidcore.presentation.restore_password;

import android.content.Context;
import android.support.annotation.Nullable;

import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.PersonRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 5/04/2018.
 */

public class RestorePasswordPresenter implements RestorePasswordContract.Presenter{

    private Context context;
    private RestorePasswordContract.View view;
    private SessionManager session;

    public RestorePasswordPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(RestorePasswordContract.View view) {
        this.view = view;
    }

    @Nullable
    private RestorePasswordContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }


    @Override
    public void updatePassword(String password, String code) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.updatePassword(session.getKeyRestoreEmail(), password, code);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    int status = jsonObject.get("status").getAsInt();
                    switch (status) {
                        case 1:

                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showPassRestoreSuccess();
                                getView().launchLoginActivity();
                            }
                            break;
                        case 2:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showPassRestoreFailure();
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
}
