package com.example.heatsink.PropertyHeatsink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heatsink.BankHeatsink.edit_banks;
import com.example.heatsink.BankHeatsink.particularBankActivity;
import com.example.heatsink.MainActivity;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.edit_owners;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.OwnersHeatsink.particularOwnerActivity;
import com.example.heatsink.R;
import com.example.heatsink.TenantsHeatsink.TenantDatabase;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.setting;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class particularPropertyActivity extends AppCompatActivity {
    TextView idNumber , OwnerName ,detailAddress,totalTenants ;
    Button edit,delete;
    ImageView home, setting ;
    TenantDatabase tenantDatabase;
    List<Tenants> tenantsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_property);
        Toolbar toolbar = findViewById(R.id.toolbarParticularProperty);
        home =toolbar.findViewById(R.id.homeParticularProperty);
        setting =toolbar.findViewById(R.id.settingParticularProperty);

        idNumber = findViewById(R.id.idParticularProperty);
        OwnerName = findViewById(R.id.particularPropertyName);
        detailAddress = findViewById(R.id.particularAddressProperty);
        totalTenants = findViewById(R.id.totalTenants);

        edit = findViewById(R.id.editParticularProperty);
        delete= findViewById(R.id.deleteParticularProperty);

        tenantDatabase = Room.databaseBuilder(getApplicationContext(), TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        tenantsList.addAll(tenantDatabase.getTenantDAO().getTenant());

       if(tenantsList.isEmpty()){
           totalTenants.setText("0");
       }else {
           String a = Integer.toString(tenantsList.size());
           totalTenants.setText(a);
       }

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(particularPropertyActivity.this , MainActivity.class));
               CustomIntent.customType(particularPropertyActivity.this, "fadein-to-fadeout");
               finish();
           }
       });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularPropertyActivity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(particularPropertyActivity.this, "fadein-to-fadeout");
                finish();
            }
        });


        final int position;


        final Bundle bundle = getIntent().getBundleExtra("property");
        position = getIntent().getIntExtra("position", -1);
        Property property;
        if(bundle != null){
            property = (Property) bundle.getSerializable("property");
            idNumber.setText(Long.toString(property.getPropertyId()));

            OwnerName.setText(property.getPropertyOwnerName());

            detailAddress.setText(property.getPropertyAddress());

        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alert = new AlertDialog.Builder(particularPropertyActivity.this);

                alert.setTitle("Delete Property");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Toast.makeText(particularPropertyActivity.this, "delete", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(particularPropertyActivity.this, property_activity.class);

                        intent.putExtra("property" , bundle);
                        intent.putExtra("position" , position);
                        startActivity(intent);
                        CustomIntent.customType(particularPropertyActivity.this, "fadein-to-fadeout");
                        finish();

                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();









            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particularPropertyActivity.this, edit_property.class);

                intent.putExtra("property" , bundle);
                intent.putExtra("position" , position);
                startActivity(intent);
                CustomIntent.customType(particularPropertyActivity.this, "fadein-to-fadeout");
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(particularPropertyActivity.this , property_activity.class));
        CustomIntent.customType(particularPropertyActivity.this, "fadein-to-fadeout");
        finish();
    }
}