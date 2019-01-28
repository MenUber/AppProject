package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.person.PersonEdit;
import com.capsuladigital.androidcore.data.model.person.PersonProfile;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.PersonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 14/05/2018.
 */

public class EditUserDataPresenter implements EditUserDataContract.Presenter {
    private Context context;
    private EditUserDataContract.View view;
    private SessionManager session;

    public EditUserDataPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(EditUserDataContract.View view) {
        this.view = view;
    }

    @Nullable
    private EditUserDataContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void editPerson(final PersonEdit person, final String birthdate) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        SessionManager sessionManager = SessionManager.getInstance(context);
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);

        Call<JsonObject> call = personRequest.editPerson(sessionManager.getKeyToken(), person);
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
                            PersonProfile personProfile = session.getUserInfo();
                            personProfile.setPersonEdit(person);
                            personProfile.setPersonBirthDay(birthdate);
                            Gson gson = new Gson();
                            session.setUserInfo((JsonObject) gson.toJsonTree(personProfile));

                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSuccessToast();
                                getView().launchProfile();
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
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (isAttached()) {
                    getView().hideLoadingDialog();
                    getView().showConnectionError();
                }


            }
        });

    }
}
