package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    VideoView videoViewOne, mainSplash;
    Animation mainVideoAnimation, bottomVideoAnimation, developerIntoAnimation;
    TextView poweredBy, devName;
    final static int totalSplashScreenTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //This removed the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // this is for the main splash screen video
        mainSplash = (VideoView) findViewById(R.id.main_splash);
        String splash_path = "android.resource://" + getPackageName() + "/" + R.raw.main_splash;
        Uri videoSplash = Uri.parse(splash_path);
        mainSplash.setVideoURI(videoSplash);
        mainSplash.start();
        mainSplash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mainSplash.start();
            }
        });

        //this is for the below splash screen video
        videoViewOne = (VideoView) findViewById(R.id.splashScreenOne);
        String pathOne = "android.resource://" + getPackageName() + "/" + R.raw.splash_weather_one;
        Uri videoOne = Uri.parse(pathOne);
        videoViewOne.setVideoURI(videoOne);
        videoViewOne.start();
        videoViewOne.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoViewOne.start();
            }
        });

        //Putting Animations
        developerIntoAnimation = AnimationUtils.loadAnimation(this,R.anim.developer_intro_animation);

        devName = findViewById(R.id.dev_name);
        poweredBy = findViewById(R.id.powered_by);

        devName.setAnimation(developerIntoAnimation);
        poweredBy.setAnimation(developerIntoAnimation);

        //calling new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Weather_Dashboard.class);
                startActivity(intent);
                finish();
            }
        },totalSplashScreenTime );

    }
}