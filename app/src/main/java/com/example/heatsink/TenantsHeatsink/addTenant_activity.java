package com.example.heatsink.TenantsHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Resources;
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
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.PropertyHeatsink.addProperty_activity;
import com.example.heatsink.PropertyHeatsink.edit_property;
import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class addTenant_activity extends AppCompatActivity {

    ImageView home  ,setting;
    EditText Name , Mobile ,Email ,PropertyId , IdProof , rent;
    Button cancel, save;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Toolbar toolbar;
    TenantDatabase tenantDatabase;

    PropertyDatabase propertyDatabase;
    Spinner spinner;
    Spinner idName;
    List<Property> listcheck1 = new ArrayList<>();



    List<Tenants> list4 = new ArrayList<>();
    Boolean isName = false ,isEmail = false , isMobile = false , isIdProof = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant_activity);
        toolbar = findViewById(R.id.toolbarAddTenant);
        home = toolbar.findViewById(R.id.homeAddTenant);
        setting = toolbar.findViewById(R.id.settingAddTenant);
        idName = findViewById(R.id.idName);
        Name = findViewById(R.id.getNameAddTenant);
        Mobile = findViewById(R.id.getPhoneAddTenant);
        Email = findViewById(R.id.getEmailAddTenant);
        spinner = findViewById(R.id.getPropertyAddTenant);
        IdProof = findViewById(R.id.getIdProofAddTenant);
        rent = findViewById(R.id.getRent);
try {
    cancel = findViewById(R.id.cancelAddTenant);
    save = findViewById(R.id.saveAddTenant);

    home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity(new Intent(addTenant_activity.this, MainActivity.class));
            CustomIntent.customType(addTenant_activity.this, "fadein-to-fadeout");
            finish();

        }
    });

    setting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(addTenant_activity.this, com.example.heatsink.setting.class));
            CustomIntent.customType(addTenant_activity.this, "fadein-to-fadeout");
            finish();
        }
    });


    String verification[] = {"Aadhar Card ", "PAN Card", "Passport"};


    final ArrayList<String> arrayList1 = new ArrayList<>();
    arrayList1.add("Select Id");
    for (String i : verification) {
        arrayList1.add(i);
    }
    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    idName.setAdapter(arrayAdapter1);

    final String[] text = new String[1];
    idName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
            ((TextView) parent.getChildAt(0)).setTextSize(14);
            text[0] = parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });


    final ArrayList<String> arrayList = new ArrayList<>();
    arrayList.add("Select Property");

    tenantDatabase = (TenantDatabase) Room.databaseBuilder(getApplicationContext(), TenantDatabase.class, "tenants").allowMainThreadQueries().build();
    list4.addAll(tenantDatabase.getTenantDAO().getTenant());
    propertyDatabase = (PropertyDatabase) Room.databaseBuilder(getApplicationContext(), PropertyDatabase.class, "property").allowMainThreadQueries().build();
//        new getAllOwnersAsynkTask().execute();
    listcheck1.addAll(propertyDatabase.getPropertyDAO().getProperty());
    for (Property b : listcheck1) {
        arrayList.add(b.getPropertyName());
    }

    final String[] property = new String[1];
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    spinner.setAdapter(arrayAdapter);

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
            ((TextView) parent.getChildAt(0)).setTextSize(14);
            property[0] = parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(addTenant_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    });

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name1, mobile1, email1, idproperty1, idProof1, idName1, rent1;
            name1 = Name.getText().toString();
            idName1 = text[0];
            mobile1 = Mobile.getText().toString();
            email1 = Email.getText().toString();
            idproperty1 = property[0];
            idProof1 = IdProof.getText().toString();
            rent1 = rent.getText().toString();

            if (!name1.isEmpty() && !rent1.isEmpty() && !mobile1.isEmpty() && !email1.isEmpty() && !idProof1.isEmpty() && !idproperty1.matches(arrayList.get(0))
                    && !idName1.matches(arrayList1.get(0))) {

                check(name1, email1, mobile1, idProof1);

                if (isName) {
                    Toast.makeText(addTenant_activity.this, "Name already exit", Toast.LENGTH_SHORT).show();
                } else if (isEmail) {
                    Toast.makeText(addTenant_activity.this, "Email already exit", Toast.LENGTH_SHORT).show();
                } else if (isMobile) {
                    Toast.makeText(addTenant_activity.this, "Mobile already exit", Toast.LENGTH_SHORT).show();
                } else if (isIdProof) {
                    Toast.makeText(addTenant_activity.this, "IdProof already exit", Toast.LENGTH_SHORT).show();
                } else if (mobile1.length() < 10) {
                    Toast.makeText(addTenant_activity.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else if (!email1.matches(emailPattern)) {
                    Toast.makeText(addTenant_activity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(addTenant_activity.this, name1 + " added", Toast.LENGTH_SHORT).show();

                    Intent intent = getIntent();
                    intent.putExtra("name", name1);
                    intent.putExtra("email", email1);
                    intent.putExtra("mobile", mobile1);
                    intent.putExtra("idproperty", idproperty1);
                    intent.putExtra("idproof", idProof1);
                    intent.putExtra("rent", rent1);
                    intent.putExtra("idName", idName1);
                    setResult(4, intent);
                    finish();
                }


            } else {
                Toast.makeText(addTenant_activity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
            }


        }
    });
}catch (Exception e){
    e.printStackTrace();
}

    }
    public void check(String name , String email , String mobile , String idProof){
        isName = false ;
        isEmail = false;
        isMobile = false;
        isIdProof = false;
        for(Tenants t : list4){
            if(t.getTenantName().matches(name)){
                isName = true;
                break;
            }
            if(t.getTenantEmail().matches(email)){
                isEmail = true;
                break;
            }
            if(t.getTenantMobile().matches(mobile)){
                isMobile = true;
                break;
            }
            if(t.getTenantIdProof().matches(idProof)){
                isIdProof = true;
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(addTenant_activity.this, tenants_activity.class));
        CustomIntent.customType(addTenant_activity.this, "fadein-to-fadeout");
        finish();
    }

}