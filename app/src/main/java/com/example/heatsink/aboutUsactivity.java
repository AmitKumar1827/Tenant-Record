package com.example.heatsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class aboutUsactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_usactivity);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(aboutUsactivity.this, setting.class));
        CustomIntent.customType(aboutUsactivity.this, "fadein-to-fadeout");
        finish();
    }

}