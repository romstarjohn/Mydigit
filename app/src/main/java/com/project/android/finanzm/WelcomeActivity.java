package com.project.android.finanzm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.content.Intent;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void buttonClicked(View view) {
        Intent intent = new Intent(WelcomeActivity.this, ConfigurationActivity.class);
        startActivity(intent);
    }
}
