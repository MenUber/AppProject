package com.capsuladigital.androidcore.data.repository.local.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.capsuladigital.androidcore.data.model.person.PersonProfile;
import com.capsuladigital.androidcore.data.repository.local.db.UserSQLiteOpenHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SessionManager {

    //Variables declaration
    private static final String PREFERENCE_NAME = "FileSP";
    private int PRIVATE_MODE = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SessionManager sessionManager;
    private String TAG = SessionManager.class.getSimpleName();


    /*
     * Constructors
     */
    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    private SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /*
     * Session
     */

    private static final String KEY_SESSION = "session";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_P_EMAIL = "personEmail";
    private static final String KEY_L_TYPE = "loginType";
    private static final String KEY_PERSON_INFO = "personInfo";
    private static final String KEY_GOOGLE_API_CLIENT = "googleApiClient";


    public void setLTypeAsNormal() {
        editor.putInt(KEY_L_TYPE, 1);
        editor.commit();

    }

    public void setLTypeAsSocial() {
        editor.putInt(KEY_L_TYPE, 2);
        editor.commit();

    }

    public int getKeyLType() {
        return preferences.getInt(KEY_L_TYPE, 0);
    }

    //Token set and get
    public void setKeyToken(String token) {
        editor.putString(KEY_TOKEN, "Bearer " + token);
        editor.commit();
    }

    public String getKeyToken() {
        return preferences.getString(KEY_TOKEN, "");
    }


    public void setKeyPEmail(String email) {
        editor.putString(KEY_P_EMAIL, email);
        editor.commit();
    }

    public String getKeyPEmail() {
        return preferences.getString(KEY_P_EMAIL, "");
    }

    public void setUserInfo(JsonObject personProfile) {

        Gson gson = new Gson();
        String json = gson.toJson(personProfile);
        editor.putString(KEY_PERSON_INFO, json);
        editor.commit();
    }

    public PersonProfile getUserInfo() {
        PersonProfile user_info = new PersonProfile();
        Gson gson = new Gson();
        String json = preferences.getString(KEY_PERSON_INFO, "");


        if (json.equals("")) {
            Log.d(TAG, "No data saved to the session.");

        } else {

            Log.d(TAG, "Data exists, setting variables...");
            user_info = gson.fromJson(json, PersonProfile.class);

        }
        return user_info;
    }


    //Change session status in login
    public void login() {
        editor.putBoolean(KEY_SESSION, true);
        editor.commit();
    }

    //Change session status in logout
    public void logOut() {
        //This cleans shared preferences
        editor.clear();
        editor.putBoolean(KEY_SESSION, false);
        editor.commit();
        //This cleans all favorite teams stored in the db
        UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                "user", null, 1);
        SQLiteDatabase db = user.getWritableDatabase();
        db.execSQL("delete from TEAMS");
    }

    //Ask if the user is already login
    public boolean isActive() {
        return preferences.getBoolean(KEY_SESSION, false);
    }


    /*
     * Restore Password
     */
    private static final String KEY_RESTORE_EMAIL = "restoreEmail";

    public void setKeyRestoreEmail(String email) {
        editor.putString(KEY_RESTORE_EMAIL, email);
        editor.commit();
    }

    public String getKeyRestoreEmail() {
        return preferences.getString(KEY_RESTORE_EMAIL, "");
    }

    /*
     * SingInActivity
     */

    private static final String KEY_SIGN_UP_NAME = "signUpName";
    private static final String KEY_SIGN_UP_LAST_NAME = "signUpLastName";
    private static final String KEY_SIGN_UP_BIRTHDAY = "signUpBirthday";
    private static final String KEY_SIGN_UP_GENDER = "signUpGender";
    private static final String KEY_SIGN_UP_EMAIL = "signUpEmail";
    private static final String KEY_SIGN_UP_PASSWORD = "signUpPassword";


    public void setKeySignUpName(String name) {
        editor.putString(KEY_SIGN_UP_NAME, name);
        editor.commit();
    }

    public String getKeySignUpName() {
        return preferences.getString(KEY_SIGN_UP_NAME, "");
    }

    public void setKeySignUpLastName(String lastName) {
        editor.putString(KEY_SIGN_UP_LAST_NAME, lastName);
        editor.commit();
    }

    public String getKeySignUpLastName() {
        return preferences.getString(KEY_SIGN_UP_LAST_NAME, "");
    }

    public void setKeySignUpBirthday(String birthday) {
        editor.putString(KEY_SIGN_UP_BIRTHDAY, birthday);
        editor.commit();
    }

    public String getKeySignUpBirthday() {
        return preferences.getString(KEY_SIGN_UP_BIRTHDAY, "");
    }

    public void setKeySignUpGender(String gender) {
        editor.putString(KEY_SIGN_UP_GENDER, gender);
        editor.commit();
    }

    public String getKeySignUpGender() {
        return preferences.getString(KEY_SIGN_UP_GENDER, "");
    }

    public void setKeySignUpEmail(String email) {
        editor.putString(KEY_SIGN_UP_EMAIL, email);
        editor.commit();
    }

    public String getKeySignUpEmail() {
        return preferences.getString(KEY_SIGN_UP_EMAIL, "");
    }

    public void setKeySignUpPassword(String pass) {
        editor.putString(KEY_SIGN_UP_PASSWORD, pass);
        editor.commit();
    }

    public String getKeySignUpPassword() {
        return preferences.getString(KEY_SIGN_UP_PASSWORD, "");
    }


}


