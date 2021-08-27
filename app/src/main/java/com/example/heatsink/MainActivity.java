package com.example.heatsink;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heatsink.BankHeatsink.banks_activity;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.TenantsHeatsink.TenantDatabase;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.tenants_activity;
import com.example.heatsink.TransactionHeatsink.TransactionDatabase;
import com.example.heatsink.TransactionHeatsink.Transactions;
import com.example.heatsink.TransactionHeatsink.addTransaction_activity;
import com.example.heatsink.TransactionHeatsink.transaction_activity;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import maes.tech.intentanim.CustomIntent;


public class MainActivity extends AppCompatActivity {
    TextView owners, transaction, property, tenants, bankAccount,received , remaining;
    ImageView home,setting;
    LinearLayout fab;
    TransactionDatabase transactionDatabase ;
    List<Transactions> transactionsList = new ArrayList<>();

    TenantDatabase tenantDatabase ;
    List<Tenants> tenantsList = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;



    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       try {
           owners = findViewById(R.id.owners);
           transaction = findViewById(R.id.transaction);
           property = findViewById(R.id.property);
           tenants = findViewById(R.id.tenants);
           bankAccount = findViewById(R.id.bank);

           received = findViewById(R.id.received);
           remaining = findViewById(R.id.remaining);


           tenantDatabase = Room.databaseBuilder(getApplicationContext(), TenantDatabase.class, "tenants").allowMainThreadQueries().build();
           tenantsList.addAll(tenantDatabase.getTenantDAO().getTenant());

           transactionDatabase = Room.databaseBuilder(getApplicationContext(), TransactionDatabase.class, "Transactions").allowMainThreadQueries().build();
           transactionsList.addAll(transactionDatabase.getTransactionDAO().getTransaction());


           SharedPreferences month = getSharedPreferences("month", MODE_PRIVATE);

           DateFormat dateFormat = new SimpleDateFormat("MM");
           Date date = new Date();





           int month1 = month.getInt("find", Integer.parseInt(dateFormat.format(date)));
           int real = Integer.parseInt(dateFormat.format(date));


           SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
           boolean firstStart = pref.getBoolean("first", true);
           int remain;
           if (firstStart) {
               remain = checkRemaing();
               SharedPreferences sharedPreferences = getSharedPreferences("month", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putInt("get", remain);
               editor.apply();
           }

           remain = month.getInt("get", 0);


           int rent1 = 0, amount1 = 0;
           if (month1 == real) {
               for (Transactions t : transactionsList) {
                   if (t.getMonthInt() == real) {
                       amount1 += Integer.parseInt(t.getAmount());
                   }
               }
               received.setText("₹ " + Integer.toString(amount1));
               for (Tenants t : tenantsList) {
                   rent1 += Integer.parseInt(t.getTenantRent());
               }
               rent1 = rent1 - amount1 + remain;
               if (rent1 < 0) {
                   rent1 = 0;
               }
               remaining.setText("₹ " + Integer.toString(rent1));


           } else {

               int value = Integer.parseInt(remaining.getText().toString());
               SharedPreferences sharedPreferences = getSharedPreferences("month", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putInt("find", Integer.parseInt(dateFormat.format(date)));
               editor.putInt("check", value);
               editor.apply();

           }


           Toolbar toolbar = findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);
           home = (ImageView) toolbar.findViewById(R.id.home);
           setting = (ImageView) toolbar.findViewById(R.id.setting);
           fab = findViewById(R.id.fab);
           home.setAlpha(0);
           setting.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, setting.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });


           owners.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, owners_activity.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });

           transaction.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, transaction_activity.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });

           property.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, property_activity.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });

           tenants.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, tenants_activity.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });

           bankAccount.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, banks_activity.class));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
               }
           });

           fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this, transaction_activity.class).addCategory("MainTransaction"));
                   CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");


               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }




    }

    private int checkRemaing() {


          SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
          SharedPreferences.Editor editor = pref.edit();
          editor.putBoolean("first", false);
          editor.apply();

          SharedPreferences prefs = getSharedPreferences("month", MODE_PRIVATE);
          int r = prefs.getInt("check", 0);
          return r;
      }





    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}