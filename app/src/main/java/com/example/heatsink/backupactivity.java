package com.example.heatsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maes.tech.intentanim.CustomIntent;

public class backupactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backupactivity);
        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(backupactivity.this, setting.class).addCategory("backup"));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(backupactivity.this, setting.class));
        CustomIntent.customType(backupactivity.this, "fadein-to-fadeout");
        finish();
    }
}