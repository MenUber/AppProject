package com.capsuladigital.androidcore.presentation.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.repository.local.db.UserSQLiteOpenHelper;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.data.repository.remote.ServiceGenerator;
import com.capsuladigital.androidcore.data.repository.remote.request.PersonRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luciano on 5/04/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private Context context;
    private LoginContract.View view;
    private SessionManager session;
    private String TAG=LoginPresenter.class.getSimpleName();

    public LoginPresenter(Context context) {
        this.context = context;
        session = SessionManager.getInstance(context);
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(LoginContract.View view) {
        this.view = view;
    }

    @Nullable
    private LoginContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void login(final String email, String password) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.login(email, password, Locale.getDefault().getLanguage());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        JsonObject jsonObject = response.body();
                        int status = jsonObject.get("status").getAsInt();

                        switch (status) {
                            case 1:
                                //Set login type
                                session.setLTypeAsNormal();
                                //Set user data in shared preferences
                                session.setKeyToken(jsonObject.
                                        get("token").getAsString());
                                session.setKeyPEmail(email);
                                session.setUserInfo(jsonObject.get("person").getAsJsonObject());
                                Log.e(TAG,jsonObject.get("person").getAsJsonObject().toString());

                                if (jsonObject.get("teams").isJsonArray()) {
                                    //Set Favorite teams in db
                                    UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                                            "user", null, 1);
                                    SQLiteDatabase db = user.getWritableDatabase();

                                    JsonArray favTeams = jsonObject.getAsJsonArray("teams");
                                    for (int i = 0; i < favTeams.size(); i++) {
                                        JsonObject team = favTeams.get(i).getAsJsonObject();
                                        db.execSQL(
                                                "INSERT INTO TEAMS Values('"
                                                        + team.get("idTeam").getAsInt()
                                                        + "','"
                                                        + team.get("teamName").getAsString()
                                                        + "','" +
                                                        team.get("teamIcon").getAsString()
                                                        + "')");
                                    }
                                }
                                session.login();
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showSuccessToast(jsonObject.
                                            getAsJsonObject("person").
                                            get("personName").getAsString());
                                    getView().subscribeToNotifications();
                                    getView().launchHome();
                                }
                                break;
                            case 2:
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showWrongCredentialsToast();
                                }

                                break;
                            case 3:
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showEmailConfirmError();
                                }
                                break;
                            case 4:
                                if (isAttached()) {
                                    getView().hideLoadingDialog();
                                    getView().showSocialMediaLoginAttemptError();
                                }
                                break;

                        }
                    }
                }

            }

            //Connection error message
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
    public void socialMedia(final String email, String name, String lastName, String photo, String birthday) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.socialMedia(email, name, lastName, photo, birthday,
                Locale.getDefault().getLanguage());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    int status = jsonObject.get("status").getAsInt();
                    switch (status) {
                        case 1:
                            //Set login type
                            session.setLTypeAsSocial();
                            //Set user data in shared preferences
                            session.setKeyToken(jsonObject.get("token").getAsString());
                            session.setKeyPEmail(email);
                            session.setUserInfo(jsonObject.get("person").getAsJsonObject());

                            if (jsonObject.get("teams").isJsonArray()) {
                                //Set Favorite teams in db
                                UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                                        "user", null, 1);
                                SQLiteDatabase db = user.getWritableDatabase();

                                JsonArray favTeams = jsonObject.getAsJsonArray("teams");
                                for (int i = 0; i < favTeams.size(); i++) {
                                    JsonObject team = favTeams.get(i).getAsJsonObject();
                                    db.execSQL("INSERT INTO TEAMS Values('"
                                            + team.get("idTeam").getAsInt()
                                            + "','"
                                            + team.get("teamName").getAsString()
                                            + "','" +
                                            team.get("teamIcon").getAsString()
                                            + "')");
                                }
                            }

                            session.login();
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSuccessToast(jsonObject.
                                        getAsJsonObject("person").
                                        get("personName").getAsString());
                                getView().subscribeToNotifications();
                                getView().launchHome();
                            }
                            break;

                        case 2:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSocialMediaWSError();
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
    public void sendPassUpdateAttempt(final String email) {
        if (isAttached()) {
            getView().showLoadingDialog();
        }
        final SessionManager sessionManager = SessionManager.getInstance(context);
        PersonRequest personRequest = ServiceGenerator.createService(PersonRequest.class);
        Call<JsonObject> call = personRequest.sendPassUpdateAttempt(email);
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
                                getView().launchRestorePassword();
                                getView().showPassRestoreDialogSucess();
                            }
                            sessionManager.setKeyRestoreEmail(email);

                            break;
                        case 2:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showPassRestoreDialogFailure();
                            }

                            break;
                        case 3:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showPassRestoreEmailNotConfirmed();
                            }

                            break;
                        case 4:
                            if (isAttached()) {
                                getView().hideLoadingDialog();
                                getView().showSocialMediaLoginAttemptError();
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
