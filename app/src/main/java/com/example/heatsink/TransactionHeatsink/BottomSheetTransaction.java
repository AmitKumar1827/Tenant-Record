package com.example.heatsink.TransactionHeatsink;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.BankHeatsink.BanksDatabase;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.R;
import com.example.heatsink.TenantsHeatsink.TenantDatabase;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.tenants_activity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class BottomSheetTransaction extends BottomSheetDialogFragment {

    public BottomSheetTransaction(){

    }
    private RadioGroup filter;
    private TextView done, clear;
    private Spinner ownerName , BankName , propertyName , TenantName;
    private TextView startingDate , endingDate;
    TenantDatabase tenantDatabase;
    List<Tenants> tenantsList = new ArrayList<>();
    PropertyDatabase propertyDatabase;
    List<Property> propertyList = new ArrayList<>();
    BanksDatabase banksDatabase;
    List<Banks> banksList = new ArrayList<>();

    OwnersDatabase ownersDatabase;
    List<Owners> ownersList = new ArrayList<>();

    RadioButton button;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener MonDateSetListener;
     int sdate, smonth, syear , edate,emonth, eyear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottomsheettransaction , container , false);

        filter = view.findViewById(R.id.filterTransaction);
        done = view.findViewById(R.id.doneTransaction);
        clear = view.findViewById(R.id.cancel11Transaction);
        ownerName  = view.findViewById(R.id.OwnerName);
        BankName  = view.findViewById(R.id.BankName);
        propertyName  = view.findViewById(R.id.propertyName);
        TenantName  = view.findViewById(R.id.tenantsName);
        startingDate  = view.findViewById(R.id.startingDate);
        endingDate  = view.findViewById(R.id.endingDate);

//        ........................................................................................................................................................


        startingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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
                sdate = date;
                smonth = month;
                syear = year;

                startingDate.setText(date + "/" + month  +"/" + year);
            }
        };


        endingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        MonDateSetListener,
                        year ,month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

            }
        });



        MonDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year  , int month, int date) {
                edate = date;
                emonth = month;
                eyear = year;
                endingDate.setText(date + "/" + month  +"/" + year);
            }
        };


//        ........................................................................................................................................................

        tenantDatabase = Room.databaseBuilder(getContext(), TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        tenantsList.addAll(tenantDatabase.getTenantDAO().getTenant());


        final ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Select Tenant");
        for(Tenants i : tenantsList){
            arrayList2.add(i.getTenantName());
        }
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TenantName.setAdapter(arrayAdapter2);

        final String[] text1 = new String[1];
        TenantName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text1[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

//        ........................................................................................................................................................


        propertyDatabase = Room.databaseBuilder(getContext() , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        propertyList.addAll(propertyDatabase.getPropertyDAO().getProperty());

        final ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add("Select Property");
        for(Property i : propertyList){
            arrayList3.add(i.getPropertyName());
        }
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyName.setAdapter(arrayAdapter3);

        final String[] text2 = new String[1];
        propertyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text2[0] = parent.getItemAtPosition(position).toString();



            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



//        ........................................................................................................................................................
        banksDatabase = Room.databaseBuilder(getContext() , BanksDatabase.class , "banks").allowMainThreadQueries().build();
        banksList.addAll(banksDatabase.getBanksDAO().getBanks());

        final ArrayList<String> arrayList5 = new ArrayList<>();
        arrayList5.add("Select Bank");
        for(Banks i : banksList){
            arrayList5.add(i.getBankName());
        }
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList5);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BankName.setAdapter(arrayAdapter5);

        final String[] text4 = new String[1];
        BankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text4[0] = parent.getItemAtPosition(position).toString();



            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });




//        ........................................................................................................................................................
        ownersDatabase = Room.databaseBuilder(getContext() , OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        ownersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
        final ArrayList<String> arrayList6 = new ArrayList<>();
        arrayList6.add("Select Owner");
        for(Owners i : ownersList){
            arrayList6.add(i.getName());
        }
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList6);
        arrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerName.setAdapter(arrayAdapter6);

        final String[] text5 = new String[1];
        ownerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text5[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });





//        ........................................................................................................................................................

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                int selectedID  = -1;
                selectedID= filter.getCheckedRadioButtonId();
                button = (RadioButton)view.findViewById(selectedID);
                String value = "";
                String owner1 , tenant1 , property1 , bank1 ,startingDate1 ,endingDate1 ;
                owner1 = text5[0];
                tenant1  = text1[0];
                property1  = text2[0];
                bank1  = text4[0];
                startingDate1 = startingDate.getText().toString();
                endingDate1 = endingDate.getText().toString();


                if(selectedID == -1){
                   value = "empty";
                }else {
                    value = button.getText().toString();

                }
                if(startingDate.getText().toString().matches("Starting Date") && endingDate.getText().toString().matches("Ending Date")){
                    Intent intent = new Intent(getContext(), transaction_activity.class);
                    intent.putExtra("code", value);
                    intent.putExtra("owner" , owner1);
                    intent.putExtra("tenant" , tenant1);
                    intent.putExtra("property" , property1);
                    intent.putExtra("bank" , bank1);
                    intent.putExtra("starting" , startingDate1);
                    intent.putExtra("ending" , endingDate1);
                    intent.addCategory("filter");
                    startActivity(intent);
                    CustomIntent.customType(getContext(), "fadein-to-fadeout");
                    dismiss();
                }else {

                    if (eyear >= syear) {
                        if (emonth >= smonth) {
                            if (edate >= sdate) {
                                Intent intent = new Intent(getContext(), transaction_activity.class);
                                intent.putExtra("code", value);
                                intent.putExtra("owner", owner1);
                                intent.putExtra("tenant", tenant1);
                                intent.putExtra("property", property1);
                                intent.putExtra("bank", bank1);
                                intent.putExtra("starting" , startingDate1);
                                intent.putExtra("ending" , endingDate1);
                                intent.putExtra("sdate", sdate);
                                intent.putExtra("smonth", smonth);
                                intent.putExtra("syear", syear);
                                intent.putExtra("edate", edate);
                                intent.putExtra("emonth", emonth);
                                intent.putExtra("eyear", eyear);
                                intent.addCategory("filter");
                                startActivity(intent);
                                CustomIntent.customType(getContext(), "fadein-to-fadeout");
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "starting date can't be greater than ending date", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "starting date can't be greater than ending date", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "starting date can't be greater than ending date", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return  view;
    }
}



















