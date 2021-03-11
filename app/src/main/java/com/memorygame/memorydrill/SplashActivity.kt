package com.memorygame.memorydrill;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by aspire on 04-07-2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.memorygame.memorydrill.R.layout.splash);

        TextView tvMasterMind = (TextView) findViewById(com.memorygame.memorydrill.R.id.tvMasterMind);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/papyrus.ttf");
        tvMasterMind.setTypeface(face);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
