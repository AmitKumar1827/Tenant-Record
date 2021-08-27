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
import android.widget.Toast;

import com.example.heatsink.MainActivity;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.OwnersHeatsink.edit_owners;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.R;
import com.example.heatsink.setting;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class edit_banks extends AppCompatActivity {


    EditText bankName , bankAccountNumber , bankIFSC ;
    Spinner bankAccountHolder;
    Button update , cancel;
    Toolbar toolbar;
    ImageView home , setting;
    Bundle bundle;
    Banks banks;
    private  BanksDatabase banksDatabase;
    private OwnersDatabase ownersDatabase;
    List<Banks> listcheck = new ArrayList<>();
    List<Owners> listcheck1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_banks);

        bankName = findViewById(R.id.getName_banksEdit);
        bankAccountNumber = findViewById(R.id.getAccountNumberEdit);
        bankIFSC = findViewById(R.id.getIFSCEdit);
        bankAccountHolder = findViewById(R.id.getAccountHolderEdit);
        update = findViewById(R.id.updateBank);
        cancel = findViewById(R.id.cancelBankEdit);
        toolbar = findViewById(R.id.toolbar_addBankEdit);
        setSupportActionBar(toolbar);
        home = toolbar.findViewById(R.id.homeAddBankEdit);
        setting = toolbar.findViewById(R.id.settingAddBankEdit);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Owners");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_banks.this, MainActivity.class));
                CustomIntent.customType(edit_banks.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_banks.this, com.example.heatsink.setting.class));
                CustomIntent.customType(edit_banks.this, "fadein-to-fadeout");

            }
        });


        ownersDatabase= (OwnersDatabase)Room.databaseBuilder(getApplicationContext(), OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        listcheck1.addAll(ownersDatabase.getOwnersDAO().getOwners());

        final int position;
        bundle = getIntent().getBundleExtra("banks");
        position = getIntent().getIntExtra("position", -1);


        for(Owners b : listcheck1){
            arrayList.add(b.getName());
        }

        final String[] Bankholder = new String[1];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAccountHolder.setAdapter(arrayAdapter);


        bankAccountHolder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {

                Bankholder[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        if(bundle != null) {
            banks = (Banks) bundle.getSerializable("banks");
            bankName.setText(banks.getBankName());
            bankAccountNumber.setText(banks.getBankAccountNumber());
            bankIFSC.setText(banks.getCodeIFSC());

            String holder1 = banks.getAccountHolder();

            ArrayAdapter myAdap = (ArrayAdapter) bankAccountHolder.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(holder1);
            bankAccountHolder.setSelection(spinnerPosition);


        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_banks.this, "Cancelled", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bank1 = bankName.getText().toString();
                String account1 = bankAccountNumber.getText().toString();
                String ifsc1 = bankIFSC.getText().toString();
                String holder = Bankholder[0];

                if (!bank1.isEmpty() && !account1.isEmpty() && !ifsc1.isEmpty() && !holder.matches(arrayList.get(0))) {



                    if(account1.length() <8){
                        Toast.makeText(edit_banks.this, "Invalid Account Number", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(edit_banks.this, bank1 + " added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(edit_banks.this , banks_activity.class);
                        intent.putExtra("bank", bank1);
                        intent.putExtra("account", account1);
                        intent.putExtra("ifsc", ifsc1);
                        intent.putExtra("holder", holder);
                        intent.putExtra("updatePosition" , position);
                        intent.addCategory("update");
                        startActivity(intent);
                        CustomIntent.customType(edit_banks.this, "fadein-to-fadeout");
                        finish();
                    }

                }else {
                    Toast.makeText(edit_banks.this, "Enter data", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(edit_banks.this , particularBankActivity.class));
        CustomIntent.customType(edit_banks.this, "fadein-to-fadeout");
        finish();
    }
}