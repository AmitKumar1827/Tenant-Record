package com.example.heatsink.TenantsHeatsink;

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
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.PropertyHeatsink.edit_property;
import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class edit_tenant extends AppCompatActivity {
    ImageView home  ,setting;
    EditText Name , Mobile ,Email  , IdProof , rent;
    Button cancel, update;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Toolbar toolbar;
    Spinner PropertyId, idProofName;
    TenantDatabase tenantDatabase;
    Bundle bundle;
    Tenants tenants;
    List<Tenants> list4 = new ArrayList<>();
    PropertyDatabase propertyDatabase;

    List<Property> listcheck1 = new ArrayList<>();
    Boolean isName = false ,isEmail = false , isMobile = false , isIdProof = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tenant);
        toolbar = findViewById(R.id.toolbarAddTenantEdit);
        home = toolbar.findViewById(R.id.homeAddTenantEdit);
        setting = toolbar.findViewById(R.id.settingAddTenantEdit);


        try {
        Name = findViewById(R.id.getNameAddTenantEdit);
        Mobile = findViewById(R.id.getPhoneAddTenantEdit);
        Email = findViewById(R.id.getEmailAddTenantEdit);
        PropertyId = findViewById(R.id.getPropertyAddTenantEdit);
        idProofName = findViewById(R.id.idNameEdit);
        IdProof = findViewById(R.id.getIdProofAddTenantEdit);

        cancel = findViewById(R.id.cancelAddTenantEdit);
        update = findViewById(R.id.saveAddTenantEdit);
        rent = findViewById(R.id.getRentEdit);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(edit_tenant.this , MainActivity.class));
                CustomIntent.customType(edit_tenant.this, "fadein-to-fadeout");
                finish();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_tenant.this , com.example.heatsink.setting.class));
                CustomIntent.customType(edit_tenant.this, "fadein-to-fadeout");
                finish();
            }
        });


        String verification[] = {"Aadhar Card " , "PAN Card" , "Passport"};

        final ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Select Id");
        for(String i : verification){
            arrayList1.add(i);
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idProofName.setAdapter(arrayAdapter1);

        final String[] text = new String[1];
        idProofName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                text[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Property");

        tenantDatabase = (TenantDatabase)  Room.databaseBuilder(getApplicationContext(), TenantDatabase.class, "tenants").allowMainThreadQueries().build();
        list4.addAll(tenantDatabase.getTenantDAO().getTenant());
        propertyDatabase = (PropertyDatabase) Room.databaseBuilder(getApplicationContext(), PropertyDatabase.class, "property").allowMainThreadQueries().build();
//        new getAllOwnersAsynkTask().execute();
        listcheck1.addAll(propertyDatabase.getPropertyDAO().getProperty());
        for(Property b : listcheck1){
            arrayList.add(b.getPropertyName());
        }

        final String[] property = new String[1];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);


        PropertyId.setAdapter(arrayAdapter);

        PropertyId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                property[0] = parent.getItemAtPosition(position).toString();



            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });











        final int position;
        bundle = getIntent().getBundleExtra("tenants");
        position = getIntent().getIntExtra("position", -1);

        if(bundle != null) {
            tenants = (Tenants) bundle.getSerializable("tenants");
            Name.setText(tenants.getTenantName());
            Mobile.setText(tenants.getTenantMobile());
            Email.setText(tenants.getTenantEmail());
            IdProof.setText(tenants.getTenantIdProof());
            rent.setText(tenants.getTenantRent());
            String propertyId1 = tenants.getTenantPropertyId();
            String idProofName1 = tenants.getIdProofName();
            ArrayAdapter myAdap = (ArrayAdapter) PropertyId.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(propertyId1);
            PropertyId.setSelection(spinnerPosition);

            ArrayAdapter myAdap1 = (ArrayAdapter) idProofName.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition1 = myAdap.getPosition(idProofName1);
            idProofName.setSelection(spinnerPosition);



        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_tenant.this, "Cancelled", Toast.LENGTH_SHORT).show();
               onBackPressed();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1, mobile1, email1, idproperty1, idProof1 ,idProofName1,rent1;
                rent1 = rent.getText().toString();
                name1 = Name.getText().toString();
                mobile1 = Mobile.getText().toString();
                email1 = Email.getText().toString();
                idproperty1 = property[0];
                idProofName1 = text[0];
                idProof1 = IdProof.getText().toString();

                if( !name1.isEmpty() && !mobile1.isEmpty()&& !email1.isEmpty()&& !idProof1.isEmpty()&& !idproperty1.matches(arrayList.get(0))
                        && !idProofName1.matches(arrayList1.get(0))){

                    if(mobile1.length() <10){
                        Toast.makeText(edit_tenant.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
                    }
                    else if(!email1.matches(emailPattern)){
                        Toast.makeText(edit_tenant.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(edit_tenant.this, name1 + " updated", Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(edit_tenant.this , tenants_activity.class);
                        intent.putExtra("name", name1);
                        intent.putExtra("email", email1);
                        intent.putExtra("mobile", mobile1);
                        intent.putExtra("idproperty", idproperty1);
                        intent.putExtra("idproof", idProof1);
                        intent.putExtra("idProofName", idProofName1);
                        intent.putExtra("rent", rent1);
                        intent.putExtra("updatePosition" , position);
                        intent.addCategory("update");
                        startActivity(intent);
                        CustomIntent.customType(edit_tenant.this, "fadein-to-fadeout");
                        finish();
                    }

                }else{
                    Toast.makeText(edit_tenant.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }catch (Exception e){
        e.printStackTrace();
    }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(edit_tenant.this , particularTenantActivity.class));
        CustomIntent.customType(edit_tenant.this, "fadein-to-fadeout");
        finish();
    }
}