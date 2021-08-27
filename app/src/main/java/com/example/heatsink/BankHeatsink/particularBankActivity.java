package com.example.heatsink.BankHeatsink;

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
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.OwnersHeatsink.edit_owners;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.OwnersHeatsink.particularOwnerActivity;
import com.example.heatsink.R;
import com.example.heatsink.setting;

import maes.tech.intentanim.CustomIntent;

public class particularBankActivity extends AppCompatActivity {
    TextView particularBankId, particularBankName, particularBankIFSC, particularBankHolderId, particularBankHolderName;
    Button edit,delete;
    Banks banks;
    BanksDatabase banksDatabase;
    ImageView home , setting;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_bank);
        particularBankId = findViewById(R.id.idParticularBank);
        particularBankName = findViewById(R.id.particularBankName);
        particularBankIFSC = findViewById(R.id.particularIFSC);
        toolbar = findViewById(R.id.toolbarParticularBank);
        home = toolbar.findViewById(R.id.homeParticularBank);
        setting = toolbar.findViewById(R.id.settingParticularBank);

        particularBankHolderName = findViewById(R.id.particularAccountHolderName);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularBankActivity.this , MainActivity.class));
                CustomIntent.customType(particularBankActivity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularBankActivity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(particularBankActivity.this, "fadein-to-fadeout");
                finish();
            }
        });

        edit = findViewById(R.id.editParticularBank);
        delete = findViewById(R.id.deleteParticularBank);
        final int position;
        final Bundle bundle = getIntent().getBundleExtra("banks");
        position = getIntent().getIntExtra("position", -1);
        Banks banks;
        if(bundle != null){
            banks = (Banks) bundle.getSerializable("banks");
            particularBankId.setText(Long.toString(banks.getAccountId()));

            particularBankName.setText(banks.getBankName());
            particularBankIFSC.setText(banks.getCodeIFSC());

            particularBankHolderName.setText(banks.getAccountHolder());

        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particularBankActivity.this, edit_banks.class);

                intent.putExtra("banks" , bundle);
                intent.putExtra("position" , position);
                startActivity(intent);
                CustomIntent.customType(particularBankActivity.this, "fadein-to-fadeout");

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alert = new AlertDialog.Builder(particularBankActivity.this);

                alert.setTitle("Delete Bank");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        Intent intent = new Intent(particularBankActivity.this, banks_activity.class);

                        intent.putExtra("banks" , bundle);
                        intent.putExtra("position" , position);
                        startActivity(intent);
                        CustomIntent.customType(particularBankActivity.this, "fadein-to-fadeout");
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
        startActivity(new Intent(particularBankActivity.this , banks_activity.class));
        CustomIntent.customType(particularBankActivity.this, "fadein-to-fadeout");
        finish();
    }
}