package com.example.heatsink.TransactionHeatsink;

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
import com.example.heatsink.R;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.edit_tenant;
import com.example.heatsink.TenantsHeatsink.particularTenantActivity;
import com.example.heatsink.TenantsHeatsink.tenants_activity;

import maes.tech.intentanim.CustomIntent;

public class particularTransactionActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView home , setting;
    TextView id,name,property, owner, bankHolder, bank, modeOfPayment , amount,dateTime;
    Button edit, delete;
    Transactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_transaction);
        toolbar = findViewById(R.id.toolbarParticularTransaction);
        home = toolbar.findViewById(R.id.homeParticularTransaction);
        setting = toolbar.findViewById(R.id.settingParticularTransaction);
        setSupportActionBar(toolbar);

        id = findViewById(R.id.idNumberParticularTransaction);
        name = findViewById(R.id.detailNameParticularTransaction);
        property = findViewById(R.id.propertyIdParticularTransaction);
        owner = findViewById(R.id.OwnerIdParticularTransaction);
        bankHolder = findViewById(R.id.bankHolderParticularTransaction);
        bank = findViewById(R.id.bankIDParticularTransaction);
        modeOfPayment = findViewById(R.id.mopParticularTransaction);
        amount = findViewById(R.id.amountParticularTransaction);
        dateTime = findViewById(R.id.dateTimeParticularTransaction);
        edit = findViewById(R.id.editParticularTransaction);
        delete = findViewById(R.id.deleteParticularTransaction);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularTransactionActivity.this , MainActivity.class));
                CustomIntent.customType(particularTransactionActivity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(particularTransactionActivity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(particularTransactionActivity.this, "fadein-to-fadeout");
                finish();
            }
        });

        final int position;


        final Bundle bundle = getIntent().getBundleExtra("transaction");
        position = getIntent().getIntExtra("position", -1);
        Tenants tenants;
        if(bundle != null){
            transactions = (Transactions) bundle.getSerializable("transaction");
            id.setText(Long.toString(transactions.getTransactionId()));

            name.setText(transactions.getTenantName());
            property.setText(transactions.getTenantProperty());
            owner.setText(transactions.getOwnerName());
            bankHolder.setText(transactions.getAccountHolder());
            bank.setText(transactions.getBankAccount());
            modeOfPayment.setText(transactions.getModeOfPayment());
            amount.setText(transactions.getAmount());
            String dateAndTime = transactions.getDate() + " | " + transactions.getTime();
            dateTime.setText(dateAndTime);

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particularTransactionActivity.this, edit_transaction.class);

                intent.putExtra("transaction" , bundle);
                intent.putExtra("position" , position);
                startActivity(intent);
                CustomIntent.customType(particularTransactionActivity.this, "fadein-to-fadeout");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                AlertDialog.Builder alert = new AlertDialog.Builder(particularTransactionActivity.this);

                alert.setTitle("Delete Transaction");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent intent = new Intent(particularTransactionActivity.this, transaction_activity.class);

                        intent.putExtra("transaction" , bundle);
                        intent.putExtra("position" , position);
                        startActivity(intent);
                        CustomIntent.customType(particularTransactionActivity.this, "fadein-to-fadeout");
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

                startActivity(new Intent(particularTransactionActivity.this , transaction_activity.class));
                CustomIntent.customType(particularTransactionActivity.this, "fadein-to-fadeout");
                finish();

    }
}