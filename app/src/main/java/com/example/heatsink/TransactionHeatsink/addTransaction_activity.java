package com.example.heatsink.TransactionHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.heatsink.TenantsHeatsink.addTenant_activity;
import com.example.heatsink.TenantsHeatsink.edit_tenant;
import com.example.heatsink.TenantsHeatsink.tenants_activity;
import com.example.heatsink.setting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class addTransaction_activity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener  {
    TextView getTime_transaction, getDate_transaction;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Spinner name, tenantProperty , OwnerName , BankHolder,BankAccount  , modeOfPayement ;
    EditText Amount;
    Button cancel , save;
    Toolbar toolbar;
    ImageView home, setting;
    TenantDatabase tenantDatabase;
    List<Tenants> tenantsList = new ArrayList<>();
    PropertyDatabase propertyDatabase;
    List<Property> propertyList = new ArrayList<>();
    BanksDatabase banksDatabase;
    List<Banks> banksList = new ArrayList<>();

    OwnersDatabase ownersDatabase;
    List<Owners> ownersList = new ArrayList<>();
    int date11;
    int month11;
    int year11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_activity);
        name = findViewById(R.id.getNameAddTransaction);
        tenantProperty = findViewById(R.id.getPropertyNameAddTransaction);

        BankHolder = findViewById(R.id.getBankHolderAddTransaction);
        BankAccount = findViewById(R.id.getBankAccountAddTransaction);
        Amount = findViewById(R.id.getAmountAddTransaction);
        modeOfPayement = findViewById(R.id.getModeOfPaymentAddTransaction);
        cancel = findViewById(R.id.cancelAddTransaction);
        save = findViewById(R.id.saveAddTransaction);
        toolbar = findViewById(R.id.toolbarAddTransaction);
        home = toolbar.findViewById(R.id.homeAddTransaction);
        setting = toolbar.findViewById(R.id.settingAddTransaction);



try {

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addTransaction_activity.this , MainActivity.class));
                CustomIntent.customType(addTransaction_activity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addTransaction_activity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(addTransaction_activity.this, "fadein-to-fadeout");
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(addTransaction_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });




        getDate_transaction = findViewById(R.id.getDate_Transaction);
        getTime_transaction = findViewById(R.id.getTime_transaction);
        getDate_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(addTransaction_activity.this ,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year ,month, day
                        );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

            }
        });



        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year  , int month, int date) {
                date11 = date;
                month11 = month+1;
                year11 = year;

                getDate_transaction.setText(date + "/" + month11  +"/" + year);
            }
        };

        getTime_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager() , "timePicker");

            }
        });
//        ****************** ****************** ****************** ****************** ****************** ****************** ****************** ****************** ******************
        String mop2[] = {"Cash" , "UPI" , "E-Wallets" , "Check" , "Bank Transfer"};

        final ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Select mode of pay");
        for(String i : mop2){
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
//         ownersDatabase = Room.databaseBuilder(getApplicationContext() , OwnersDatabase.class , "owners").allowMainThreadQueries().build();
//        ownersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
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
        ownersDatabase = Room.databaseBuilder(getApplicationContext() , OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        ownersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
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



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date1 , time1 , tenantName1 , tenantProperty1,Owner1  , AccountHolder1 , BankAccount1, Amount1 , modeOfPayment1;
                int date,month,year;
                Owners owners;
                date1 = getDate_transaction.getText().toString();
                time1 = getTime_transaction.getText().toString();
                date  = date11;
                month  = month11;
                year  = year11;
                tenantName1 = text1[0];
                tenantProperty1 = text2[0];
                int i = propertyList.indexOf(tenantProperty1);
//                owners = ownersDatabase.getOwnersDAO().getOwners(i);
//                Owner1 = owners.getName();

                AccountHolder1 = text5[0];
                BankAccount1 = text4[0];
                Amount1 = Amount.getText().toString();
                modeOfPayment1 = text[0];

                if( !date1.matches("Choose Date") && !time1.matches("Choose time")&& !tenantName1.matches(arrayList2.get(0))&& !tenantProperty1.matches(arrayList3.get(0))

                        && !AccountHolder1.matches(arrayList6.get(0)) && !BankAccount1.matches(arrayList5.get(0)) &&
                        !Amount1.isEmpty() && !modeOfPayment1.matches(arrayList1.get(0))  ){

                    Toast.makeText(addTransaction_activity.this,   " Transaction added", Toast.LENGTH_SHORT).show();

                    Intent intent = getIntent();
                    intent.putExtra("date", date1);
                    intent.putExtra("time", time1);
                    intent.putExtra("name", tenantName1);
                    intent.putExtra("property", tenantProperty1);
                    intent.putExtra("dateP", date);
                    intent.putExtra("yearP", year);
                    intent.putExtra("monthP", month);
//                    intent.putExtra("owner", Owner1);
                    intent.putExtra("holder", AccountHolder1);
                    intent.putExtra("bank", BankAccount1);
                    intent.putExtra("amount", Amount1);
                    intent.putExtra("mop", modeOfPayment1);
                    setResult(5, intent);
                    finish();




                }else{
                    Toast.makeText(addTransaction_activity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }catch (Exception e){
        e.printStackTrace();
    }

    }



    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

            getTime_transaction.setText(i + " : " + i1 );

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(addTransaction_activity.this, transaction_activity.class));
        CustomIntent.customType(addTransaction_activity.this, "fadein-to-fadeout");
        finish();
    }

}