package com.example.heatsink.TransactionHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.app.DatePickerDialog;
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

import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.BankHeatsink.BanksDatabase;
import com.example.heatsink.MainActivity;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.R;
import com.example.heatsink.TenantsHeatsink.TenantDatabase;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.edit_tenant;
import com.example.heatsink.TenantsHeatsink.tenants_activity;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class edit_transaction extends AppCompatActivity {
    TextView getTime_transaction, getDate_transaction;
    Spinner name, tenantProperty , OwnerName , BankHolder,BankAccount  , modeOfPayement ;
    EditText  Amount;
    Button cancel , update;
    Toolbar toolbar;
    ImageView home, setting;
    Bundle bundle;
    Transactions transactions;
    TenantDatabase tenantDatabase;
    List<Tenants> tenantsList = new ArrayList<>();
    PropertyDatabase propertyDatabase;
    List<Property> propertyList = new ArrayList<>();
    BanksDatabase banksDatabase;
    List<Banks> banksList = new ArrayList<>();

    int date11;
    int month11;
    int year11;

    OwnersDatabase ownersDatabase;
    List<Owners> ownersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        name = findViewById(R.id.getNameAddTransactionEdit);
        tenantProperty = findViewById(R.id.getPropertyNameAddTransactionEdit);
//        OwnerName = findViewById(R.id.getOwnerNameAddTransactionEdit);
        BankHolder = findViewById(R.id.getBankHolderAddTransactionEdit);
        BankAccount = findViewById(R.id.getBankAccountAddTransactionEdit);
        Amount = findViewById(R.id.getAmountAddTransactionEdit);
        modeOfPayement = findViewById(R.id.getModeOfPaymentAddTransactionEdit);
        cancel = findViewById(R.id.cancelAddTransactionEdit);
        update = findViewById(R.id.saveAddTransactionEdit);
        toolbar = findViewById(R.id.toolbarAddTransactionEdit);
        home = toolbar.findViewById(R.id.homeAddTransactionEdit);
        setting = toolbar.findViewById(R.id.settingAddTransactionEdit);
        getDate_transaction = findViewById(R.id.getDate_TransactionEdit);
        getTime_transaction = findViewById(R.id.getTime_transactionEdit);

try {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_transaction.this , MainActivity.class));
                CustomIntent.customType(edit_transaction.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_transaction.this , com.example.heatsink.setting.class));
                CustomIntent.customType(edit_transaction.this, "fadein-to-fadeout");
                finish();
            }
        });

//        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************

        String mop3[] = {"Cash" , "UPI" , "E-Wallets" , "Check", "Bank Transfer"};

        final ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Select mode of pay");
        for(String i : mop3){
            arrayList1.add(i);
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeOfPayement.setAdapter(arrayAdapter1);

        final String[] text = new String[1];
        modeOfPayement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

//        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************
        tenantDatabase = Room.databaseBuilder(getApplicationContext(), TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        tenantsList.addAll(tenantDatabase.getTenantDAO().getTenant());


        final ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Select Tenant");
        for(Tenants i : tenantsList){
            arrayList2.add(i.getTenantName());
        }
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name.setAdapter(arrayAdapter2);

        final String[] text1 = new String[1];
        name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text1[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
//        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************
        propertyDatabase = Room.databaseBuilder(getApplicationContext() , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        propertyList.addAll(propertyDatabase.getPropertyDAO().getProperty());

        final ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add("Select Property");
        for(Property i : propertyList){
            arrayList3.add(i.getPropertyName());
        }
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenantProperty.setAdapter(arrayAdapter3);

        final String[] text2 = new String[1];
        tenantProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text2[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        //        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************
        ownersDatabase = Room.databaseBuilder(getApplicationContext() , OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        ownersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
//
//        final ArrayList<String> arrayList4 = new ArrayList<>();
//        arrayList4.add("Select Owner");
//        for(Owners i : ownersList){
//            arrayList4.add(i.getName());
//        }
//        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList4);
//        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        OwnerName.setAdapter(arrayAdapter4);
//
//        final String[] text3 = new String[1];
//        OwnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
//                ((TextView) parent.getChildAt(0)).setTextSize(14);
//                text3[0] = parent.getItemAtPosition(position).toString();
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView <?> parent) {
//            }
//        });
        //        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************
        banksDatabase = Room.databaseBuilder(getApplicationContext() , BanksDatabase.class , "banks").allowMainThreadQueries().build();
        banksList.addAll(banksDatabase.getBanksDAO().getBanks());

        final ArrayList<String> arrayList5 = new ArrayList<>();
        arrayList5.add("Select Bank");
        for(Banks i : banksList){
            arrayList5.add(i.getBankName());
        }
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BankAccount.setAdapter(arrayAdapter5);

        final String[] text4 = new String[1];
        BankAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text4[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
//        ******************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************

        final ArrayList<String> arrayList6 = new ArrayList<>();
        arrayList6.add("Select Account Holder");
        for(Owners i : ownersList){
            arrayList6.add(i.getName());
        }
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BankHolder.setAdapter(arrayAdapter6);

        final String[] text5 = new String[1];
        BankHolder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text5[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });











//        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************





        final int position;
        bundle = getIntent().getBundleExtra("transaction");
        position = getIntent().getIntExtra("position", -1);

        if(bundle != null) {
            transactions = (Transactions) bundle.getSerializable("transaction");
            getDate_transaction.setText(transactions.getDate());
            getTime_transaction.setText(transactions.getTime());
            Amount.setText(transactions.getAmount());

            String name1 = transactions.getTenantName();
            String property1 = transactions.getTenantProperty();

            String bank1 = transactions.getBankAccount();
            String bankHolder = transactions.getAccountHolder();
            String mop1 = transactions.getModeOfPayment();

            ArrayAdapter myAdap = (ArrayAdapter) name.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(name1);
            name.setSelection(spinnerPosition);

            ArrayAdapter myAdap1 = (ArrayAdapter) tenantProperty.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition1 = myAdap1.getPosition(property1);
            tenantProperty.setSelection(spinnerPosition1);


            ArrayAdapter myAdap3 = (ArrayAdapter) BankAccount.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition3 = myAdap3.getPosition(bank1);
            BankAccount.setSelection(spinnerPosition3);

            ArrayAdapter myAdap4 = (ArrayAdapter) BankHolder.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition4 = myAdap4.getPosition(bankHolder);
            BankHolder.setSelection(spinnerPosition4);

            ArrayAdapter myAdap5 = (ArrayAdapter) modeOfPayement.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition5 = myAdap5.getPosition(mop1);
            modeOfPayement.setSelection(spinnerPosition5);



        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_transaction.this, "Cancelled", Toast.LENGTH_SHORT).show();
               onBackPressed();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date1 , time1 , tenantName1 , tenantProperty1 , Owner1 = null, AccountHolder1 , BankAccount1, Amount1 , modeOfPayment1;
                date1 = getDate_transaction.getText().toString();
                time1 = getTime_transaction.getText().toString();
                date11 = transactions.getDateInt();
                month11 = transactions.getMonthInt();
                year11 = transactions.getYearInt();
                tenantName1 = text1[0];
                tenantProperty1 = text2[0];
                propertyList.clear();
                propertyList.addAll(propertyDatabase.getPropertyDAO().getProperty());
                for(Property p : propertyList){
                    if (p.getPropertyName().matches(tenantProperty1)) {
                        
                        Owner1 = p.getPropertyOwnerName();
                        break;
                    }
                }
                
                AccountHolder1 = text5[0];
                BankAccount1 =text4[0];
                Amount1 = Amount.getText().toString();
                modeOfPayment1 =text[0];

                if( !date1.isEmpty() && !time1.isEmpty()&& !tenantName1.matches(arrayList2.get(0))&& !tenantProperty1.matches(arrayList3.get(0))
                        && !AccountHolder1.matches(arrayList6.get(0)) &&
                        !BankAccount1.matches(arrayList5.get(0)) && !Amount1.isEmpty() && !modeOfPayment1.matches(arrayList1.get(0))  ){

                    Toast.makeText(edit_transaction.this,   " Transaction added", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(edit_transaction.this , transaction_activity.class);
                    intent.putExtra("date", date1);
                    intent.putExtra("time", time1);
                    intent.putExtra("name", tenantName1);
                    intent.putExtra("property", tenantProperty1);
                    intent.putExtra("owner", Owner1);
                    intent.putExtra("holder", AccountHolder1);
                    intent.putExtra("bank", BankAccount1);
                    intent.putExtra("amount", Amount1);
                    intent.putExtra("mop", modeOfPayment1);
                    intent.putExtra("dateP", date11);
                    intent.putExtra("yearP", year11);
                    intent.putExtra("monthP", month11);
                    intent.putExtra("updatePosition" , position);
                    intent.addCategory("update");
                    startActivity(intent);
                    CustomIntent.customType(edit_transaction.this, "fadein-to-fadeout");
                    finish();

                }else{
                    Toast.makeText(edit_transaction.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }catch (Exception e){
        e.printStackTrace();
    }


    }

    @Override
    public void onBackPressed() {

                startActivity(new Intent(edit_transaction.this , particularTransactionActivity.class));
                CustomIntent.customType(edit_transaction.this, "fadein-to-fadeout");
                finish();

    }
}