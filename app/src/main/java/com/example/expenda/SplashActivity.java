package com.example.expenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

    public class SplashActivity extends AppCompatActivity {
        private static int Splash_SCREEN = 4000;

        Animation topAnim, bottomAnim;
        ImageView image;
        TextView logo, slogan;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);

            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animations);

            image = findViewById(R.id.wallet);
            logo = findViewById(R.id.logo);
            slogan = findViewById(R.id.slogan);

            image.setAnimation(topAnim);
            logo.setAnimation(bottomAnim);
            slogan.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(com.example.expenda.SplashActivity.this, EditProfile.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(image, "logoImg");
                    pairs[1] = new Pair<View, String>(logo, "appName");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(com.example.expenda.SplashActivity.this, pairs);
                    startActivity(new Intent(SplashActivity.this,activity_login.class));
                }
            }, Splash_SCREEN);
        }
    }