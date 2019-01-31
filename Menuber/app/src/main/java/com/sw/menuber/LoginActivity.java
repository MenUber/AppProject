package com.sw.menuber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.sw.menuber.LoginInterface.View;

public class LoginActivity extends AppCompatActivity implements View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
