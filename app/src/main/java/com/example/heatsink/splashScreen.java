package com.example.heatsink;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import maes.tech.intentanim.CustomIntent;

public class splashScreen extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread thread = new Thread(){
          
            @Override
            public void run() {
               try {
                   sleep(2000);


               }catch (Exception e){
                    e.printStackTrace();
               }finally {

                   SharedPreferences pref = getSharedPreferences("prefs" , MODE_PRIVATE);
                   boolean firstStart = pref.getBoolean("first" , true);

                   SharedPreferences sharedpreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                   boolean login = sharedpreferences.getBoolean("login" , true);

                   DateFormat dateFormat1 = new SimpleDateFormat("dd");
                   Date date1 = new Date();
                   System.out.println(Integer.parseInt(dateFormat1.format(date1)));

                   SharedPreferences sharedPreferences1 = getSharedPreferences("date" , Context.MODE_PRIVATE);
                   boolean validate = sharedPreferences1.getBoolean("validate" , true);


//                if(validate && Integer.parseInt(dateFormat1.format(date1)) < 22) {

                    if (firstStart) {
                        startActivity(new Intent(splashScreen.this, termCondition.class));
                        CustomIntent.customType(splashScreen.this, "fadein-to-fadeout");
                        finish();
                    } else if (login) {
                        startActivity(new Intent(splashScreen.this, logindetails.class));
                        CustomIntent.customType(splashScreen.this, "fadein-to-fadeout");
                        finish();


                    } else {

                        startActivity(new Intent(splashScreen.this, MainActivity.class));
                        CustomIntent.customType(splashScreen.this, "fadein-to-fadeout");
                        finish();
                    }
//                }else{
//
//                    startActivity(new Intent(splashScreen.this , validation.class));
//                    finish();
//
//                }

               }
            }
        };
        thread.start();
    }
}