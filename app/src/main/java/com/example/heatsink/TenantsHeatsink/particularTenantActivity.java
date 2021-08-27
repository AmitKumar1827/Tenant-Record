package com.example.heatsink.TenantsHeatsink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heatsink.MainActivity;
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.edit_property;
import com.example.heatsink.PropertyHeatsink.particularPropertyActivity;
import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.R;

import maes.tech.intentanim.CustomIntent;

public class particularTenantActivity extends AppCompatActivity {


    TextView name,propertyId, mobile , email, idProof , id , idProofName , rent;
    Toolbar toolbar;
    Button edit, delete;
    ImageView home, setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_tenant);
        toolbar = findViewById(R.id.toolbarParticularTenant);
        home = toolbar.findViewById(R.id.homeParticularTenant);
        setting = toolbar.findViewById(R.id.settingParticularTenant);
        id = findViewById(R.id.idNumberParticularTenant);
        name = findViewById(R.id.detailNameParticularTenant);
        mobile = findViewById(R.id.detailMobileParticularTenant);
        email = findViewById(R.id.detailEmailParticularTenant);
        idProof = findViewById(R.id.detailIdProofParticularTenant);
        idProofName = findViewById(R.id.detailIdProofNameParticularTenant);
        propertyId = findViewById(R.id.propertyIdParticularTenant);
        edit = findViewById(R.id.editParticularTenants);
        delete =  findViewById(R.id.deleteParticularTenant);
        rent = findViewById(R.id.rentParticularTenant);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(particularTenantActivity.this , MainActivity.class));
                CustomIntent.customType(particularTenantActivity.this, "fadein-to-fadeout");
                finish();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularTenantActivity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(particularTenantActivity.this, "fadein-to-fadeout");
                finish();
            }
        });


        final int position;


        final Bundle bundle = getIntent().getBundleExtra("tenants");
        position = getIntent().getIntExtra("position", -1);
        Tenants tenants;
        if(bundle != null){
            tenants = (Tenants) bundle.getSerializable("tenants");
            id.setText(Long.toString(tenants.getTenantId()));
            idProofName.setText((tenants.getIdProofName()));
            name.setText(tenants.getTenantName());
            mobile.setText(tenants.getTenantMobile());
            email.setText(tenants.getTenantEmail());
            idProof.setText(tenants.getTenantIdProof());
            propertyId.setText(tenants.getTenantPropertyId());
            rent.setText(tenants.getTenantRent());


        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particularTenantActivity.this, edit_tenant.class);

                intent.putExtra("tenants" , bundle);
                intent.putExtra("position" , position);
                CustomIntent.customType(particularTenantActivity.this, "fadein-to-fadeout");
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alert = new AlertDialog.Builder(particularTenantActivity.this);

                alert.setTitle("Delete Tenants");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete


                        Intent intent = new Intent(particularTenantActivity.this, tenants_activity.class);

                        intent.putExtra("tenants" , bundle);
                        intent.putExtra("position" , position);
                        startActivity(intent);
                        CustomIntent.customType(particularTenantActivity.this, "fadein-to-fadeout");
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


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(particularTenantActivity.this , tenants_activity.class));
        CustomIntent.customType(particularTenantActivity.this, "fadein-to-fadeout");
        finish();
    }
}