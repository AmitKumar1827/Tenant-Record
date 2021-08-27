package com.example.heatsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heatsink.OwnersHeatsink.addOwners_activity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class logindetails extends AppCompatActivity {
    EditText userName, email, mobile;
    Button save;
    SharedPreferences sharedpreferences;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindetails);

        userName = findViewById(R.id.userName);
        email = findViewById(R.id.emailuser);
        mobile = findViewById(R.id.mobileuser);
        save = findViewById(R.id.saveuser);

        FirebaseApp.initializeApp(logindetails.this);





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final String name = userName.getText().toString();
                final String email1 = email.getText().toString();
                final String mobile1 = mobile.getText().toString();


                if(!name.isEmpty() && !email1.isEmpty()){



                    if(!email1.matches(emailPattern)){
                        Toast.makeText(logindetails.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    }else {

                        sharedpreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString("name", name);
                        editor.putString("email", email1);
                        editor.putBoolean("login" , false);
                        editor.commit();

//                    HashMap<String , Object> map = new HashMap<>();
//                    map.put("Name" , name);
//                    map.put("Email" , email1);
//
//                    if(mobile1.isEmpty()){
//                        map.put("Email" , email1);
//                    }else {
//                        map.put("Email" , mobile1);

                      FirebaseDatabase.getInstance().getReference().child("UserRecords").child(name).child("email").setValue(email1);



                        if (mobile1.isEmpty()) {
                            FirebaseDatabase.getInstance().getReference().child("UserRecords").child(name).child("mobile").setValue(mobile1);
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("UserRecords").child(name).child("mobile").setValue(mobile1);

                        }


                        startActivity(new Intent(logindetails.this, MainActivity.class));
                        CustomIntent.customType(logindetails.this, "fadein-to-fadeout");
                        finish();
                    }


                }else{
                    Toast.makeText(logindetails.this, "Please enter detail", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}