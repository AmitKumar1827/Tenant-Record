package com.example.heatsink.OwnersHeatsink;

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

import com.example.heatsink.MainActivity;
import com.example.heatsink.R;
import com.example.heatsink.setting;

import maes.tech.intentanim.CustomIntent;

public class particularOwnerActivity extends AppCompatActivity {

    TextView idNumber , detailName , detailMobile , detailEmail ,detailAddress , detailIdName , detailIdNumber;
    Button edit,delete;
    Owners owners;
    OwnersDatabase ownersDatabase;
    ImageView home, setting;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_owner);
        idNumber = findViewById(R.id.idNumber);
        detailName = findViewById(R.id.detailName);
        detailMobile = findViewById(R.id.detailMobile);
        detailEmail = findViewById(R.id.detailEmail);
        detailAddress = findViewById(R.id.detailAddress);
        detailIdName = findViewById(R.id.detailIdName);
        detailIdNumber = findViewById(R.id.detailIdNumber);

        toolbar = findViewById(R.id.toolbar_ownersParticular);
        home = toolbar.findViewById(R.id.homeParticularOwner);
        setting = toolbar.findViewById(R.id.settingParticularOwner);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularOwnerActivity.this , MainActivity.class));
                CustomIntent.customType(particularOwnerActivity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularOwnerActivity.this  , com.example.heatsink.setting.class));
                CustomIntent.customType(particularOwnerActivity.this , "fadein-to-fadeout");
                finish();
            }
        });




        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        final int position;


         final Bundle bundle = getIntent().getBundleExtra("owners");
         position = getIntent().getIntExtra("position", -1);
         Owners owners;
         if(bundle != null){
            owners = (Owners) bundle.getSerializable("owners");
            idNumber.setText(Long.toString(owners.getId()));
//            idNumber.setText(Integer.toString(position));
            detailName.setText(owners.getName());
            detailMobile.setText(owners.getMobile());
            detailAddress.setText(owners.getAddress());
            detailEmail.setText(owners.getEmail());
            detailIdName.setText(owners.getIdName());
            detailIdNumber.setText(owners.getIdProof());
         }







        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particularOwnerActivity.this, edit_owners.class);

                intent.putExtra("owners" , bundle);
                intent.putExtra("position" , position);
                startActivity(intent);
                CustomIntent.customType(particularOwnerActivity.this , "fadein-to-fadeout");

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alert = new AlertDialog.Builder(particularOwnerActivity.this);

                alert.setTitle("Delete Owner");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        Intent intent = new Intent(particularOwnerActivity.this, owners_activity.class);

                        intent.putExtra("owners" , bundle);
                        intent.putExtra("position" , position);
                        startActivity(intent);

                        finish();
                        CustomIntent.customType(particularOwnerActivity.this, "fadein-to-fadeout");

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
        startActivity(new Intent(particularOwnerActivity.this  , owners_activity.class));
        CustomIntent.customType(particularOwnerActivity.this , "fadein-to-fadeout");
        finish();


    }
}