package com.example.artsyapp;

import static com.google.android.ump.FormError.ErrorCode.TIME_OUT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

public class TransactionActivity extends AppCompatActivity {

    private int TIME_OUT=2000;  // for 10 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtsyApp_NoActionBar);
        setContentView(R.layout.activity_transaction);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(TransactionActivity.this);
                startActivity(intent, options.toBundle());
                finish();
            }
        },TIME_OUT);
    }
}