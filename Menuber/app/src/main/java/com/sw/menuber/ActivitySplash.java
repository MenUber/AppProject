package com.sw.menuber;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY=4000;
    private ImageView  imgFondo,logo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

/*        imgFondo=(ImageView) findViewById(R.id.fondo);
        logo = (ImageView) findViewById(R.id.logo);*/

        AnimarFondo();
        AnimarLogo();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent().setClass(ActivitySplash.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer= new Timer();
        timer.schedule(task,SPLASH_SCREEN_DELAY);





    }
    public void AnimarFondo(){

        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(findViewById(R.id.fondo));
    }
    public void AnimarLogo(){

        YoYo.with(Techniques.RotateInUpLeft)
                .duration(2500)
                .repeat(0)
                .playOn(findViewById(R.id.logo));
    }
}
