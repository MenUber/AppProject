package com.capsuladigital.androidcore.presentation.sign_up.sign_up_form;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.data.model.person.PersonSignUpForm;
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

public class SignUpFormPresenter implements SignUpFormContract.Presenter{
    private Context context;
    private SignUpFormContract.View view;
    private SessionManager session;

    public SignUpFormPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(SignUpFormContract.View view) {
        this.view = view;

    }

    @Nullable
    private SignUpFormContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void  storeSignUpInfo(final PersonSignUpForm person)  {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.verifyEmail(person.getPersonEmail());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("status", "Entro causa");
                        JsonObject athleteResponse = response.body();
                        int status = athleteResponse.get("status").getAsInt();
                        //Print status. Oh yeah!
                        Log.e("status", status + "");
                        switch (status) {
                            case 0:
                                session.setKeySignUpName(person.getPersonName());
                                session.setKeySignUpLastName(person.getPersonLastName());
                                session.setKeySignUpBirthday(person.getPersonBirthDay());
                                session.setKeySignUpGender(person.getPersonGender());
                                session.setKeySignUpEmail(person.getPersonEmail());
                                session.setKeySignUpPassword(person.getPersonPassword());
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().launchImproveXperienceActivity();
                                }
                                break;
                            case 1:
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showEmailAlreadyRegisteredDialog();
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

