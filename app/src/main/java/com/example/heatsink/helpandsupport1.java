package com.example.heatsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class helpandsupport1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpandsupport1);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(helpandsupport1.this, setting.class));
        CustomIntent.customType(helpandsupport1.this, "fadein-to-fadeout");
        finish();
    }
}