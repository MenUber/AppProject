package com.capsuladigital.androidcore.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.presentation.home.MainActivity;
import com.capsuladigital.androidcore.presentation.login.LoginActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    public final String TAG = SplashActivity.class.getSimpleName();
    private static final long SPLASH_SCREEN_DELAY = 1700;
    private SessionManager sessionManager;
    private String url = "https://play.google.com/store/apps/details?id=com.capsuladigital.perumundial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // This hide the notifications bar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /*if (hasActiveInternetConnection()) {
                    try {
                        if (BuildConfig.VERSION_NAME.equals( VersionChecker.getPlayStoreAppVersion(url + "&hl=en"))) {
                            launchProperActivity();
                        }else{
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    notifyUpdate();
                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.e("gg", e.toString());
                        launchProperActivity();
                    }
                } else {*/
                    launchProperActivity();

                //}
            }

        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);


    }


    private void launchProperActivity() {
        sessionManager = SessionManager.getInstance(getApplicationContext());
        if (sessionManager.isActive()) {
            launchActivity(getApplicationContext(), MainActivity.class);
        } else {
            launchActivity(getApplicationContext(), LoginActivity.class);

        }

    }


    private void launchActivity(Context origin, Class destiny) {
        Intent i = new Intent(origin, destiny);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean hasActiveInternetConnection() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com/").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Conne" +
                        "ction", "close");
                urlc.setConnectTimeout(1000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }

    public void notifyUpdate() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Message
        alertDialog.setTitle(getString(R.string.update_dlg_title))
                .setMessage(getString(R.string.update_dlg_content))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.update_dlg_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                        }
                    }
                })
                .setNegativeButton(getString(R.string.update_dlg_negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       launchProperActivity();
                    }
                });
        alertDialog.show();
    }


}

