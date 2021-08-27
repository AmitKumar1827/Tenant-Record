package com.example.heatsink.BankHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heatsink.MainActivity;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.OwnersHeatsink.addOwners_activity;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.R;
import com.example.heatsink.TransactionHeatsink.transaction_activity;
import com.example.heatsink.setting;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class addBank_activity extends AppCompatActivity {

    EditText bankName , bankAccountNumber , bankIFSC ;
    Button save , cancel;
    Toolbar toolbar;
    ImageView home , setting;
    Spinner spinner;

    private  BanksDatabase banksDatabase;
    private OwnersDatabase ownersDatabase;
    List<Banks> listcheck = new ArrayList<>();
    List<Owners> listcheck1 = new ArrayList<>();
    Boolean  isAccount = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_activity);
        bankName = findViewById(R.id.getName_banks);
        bankAccountNumber = findViewById(R.id.getAccountNumber);
        bankIFSC = findViewById(R.id.getIFSC);
        spinner = findViewById(R.id.getAccountHolder);
        save = findViewById(R.id.saveBank);
        cancel = findViewById(R.id.cancelBank);
        toolbar = findViewById(R.id.toolbar_addBank);
        setSupportActionBar(toolbar);
        home = toolbar.findViewById(R.id.homeAddBank);
        setting = toolbar.findViewById(R.id.settingAddBank);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Owners");

        banksDatabase = (BanksDatabase) Room.databaseBuilder(getApplicationContext(), BanksDatabase.class, "banks").allowMainThreadQueries().build();
        listcheck.addAll(banksDatabase.getBanksDAO().getBanks());
        ownersDatabase= (OwnersDatabase)Room.databaseBuilder(getApplicationContext(), OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        listcheck1.addAll(ownersDatabase.getOwnersDAO().getOwners());




        for(Owners b : listcheck1){
            arrayList.add(b.getName());
        }

        final String[] Bankholder = new String[1];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                 Bankholder[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });








        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addBank_activity.this , MainActivity.class));
                CustomIntent.customType(addBank_activity.this, "fadein-to-fadeout");
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addBank_activity.this , setting.class));
                CustomIntent.customType(addBank_activity.this, "fadein-to-fadeout");

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(addBank_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bank1, account1, ifsc1, holder1;
                bank1 = bankName.getText().toString();
                account1 = bankAccountNumber.getText().toString();
                ifsc1 = bankIFSC.getText().toString();
                holder1 = Bankholder[0];

                if (!bank1.isEmpty() && !account1.isEmpty() && !ifsc1.isEmpty() && !holder1.matches(arrayList.get(0))) {
                    check(account1);

                    if(isAccount == true){
                        Toast.makeText(addBank_activity.this, "Account Number already exist", Toast.LENGTH_SHORT).show();
                    }else if(account1.length() <8){
                        Toast.makeText(addBank_activity.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(addBank_activity.this, bank1 + " added", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        intent.putExtra("bank", bank1);
                        intent.putExtra("account", account1);
                        intent.putExtra("ifsc", ifsc1);
                        intent.putExtra("holder", holder1);

                        setResult(2, intent);
                        CustomIntent.customType(addBank_activity.this, "fadein-to-fadeout");
                        finish();
                    }

                }else {
                    Toast.makeText(addBank_activity.this, "Enter data", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    private void check(String account1){
        isAccount = false;
        for(Banks i : listcheck){
            if(i.getBankAccountNumber().matches(account1)){
                isAccount = true;
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(addBank_activity.this, banks_activity.class));
        CustomIntent.customType(addBank_activity.this, "fadein-to-fadeout");
        finish();
    }

}