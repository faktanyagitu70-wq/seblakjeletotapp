package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.seblakjeletot.app.R;

public class SplashActivity extends Activity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
