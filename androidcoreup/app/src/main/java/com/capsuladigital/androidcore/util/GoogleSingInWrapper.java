package com.capsuladigital.androidcore.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleSingInWrapper {
    private static GoogleSingInWrapper instance;
    private GoogleApiClient mGoogleApiClient;

    private GoogleSingInWrapper(final Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().requestProfile().
                build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(context, context.getResources().getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public static GoogleSingInWrapper getInstance(Context context) {
        if(instance == null) {
            instance = new GoogleSingInWrapper(context);
        }
        return instance;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }


    // Other methods
    public void logout(){
        if(mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }
}