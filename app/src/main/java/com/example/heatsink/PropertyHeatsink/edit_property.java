package com.example.heatsink.PropertyHeatsink;

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

import com.example.heatsink.BankHeatsink.edit_banks;
import com.example.heatsink.BankHeatsink.particularBankActivity;
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

public class edit_property extends AppCompatActivity {

    Toolbar toolbar;
    ImageView home , setting;
    Button cancel , update ;
    EditText getName , getAddress;
    Bundle bundle;
    Spinner spinner;
    Property property;
    PropertyDatabase propertyDatabase;

    OwnersDatabase ownersDatabase;

    List<Property> list3 = new ArrayList<>();
    List<Owners> listcheck1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        toolbar = findViewById(R.id.toolbar_addPropertyEdit);
        setSupportActionBar(toolbar);
        home = toolbar.findViewById(R.id.homeAddPropertyEdit);
        setting = toolbar.findViewById(R.id.settingAddPropertyEdit);

        update = findViewById(R.id.saveAddPropertyEdit);
        cancel = findViewById(R.id.cancelAddPropertyEdit);
        getName = findViewById(R.id.getNameAddPropertyEdit);
        spinner = findViewById(R.id.OwnerPropertyNameEdit);
        getAddress = findViewById(R.id.addressAddPropertyEdit);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        startActivity(new Intent(edit_property.this , MainActivity.class));
                        CustomIntent.customType(edit_property.this, "fadein-to-fadeout");
                        finish();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_property.this , com.example.heatsink.setting.class));
                CustomIntent.customType(edit_property.this, "fadein-to-fadeout");
                finish();
            }
        });



        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Owners");

        ownersDatabase= (OwnersDatabase)Room.databaseBuilder(getApplicationContext(), OwnersDatabase.class , "owners").allowMainThreadQueries().build();
        listcheck1.addAll(ownersDatabase.getOwnersDAO().getOwners());




        for(Owners b : listcheck1){
            arrayList.add(b.getName());
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
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });




        final int position;
        bundle = getIntent().getBundleExtra("property");
        position = getIntent().getIntExtra("position", -1);
        Property property1;
        if(bundle != null) {
            property1 = (Property) bundle.getSerializable("property");
           getName.setText(property1.getPropertyName());

           getAddress.setText(property1.getPropertyAddress());
            String owner = property1.getPropertyOwnerName();
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(owner);
            spinner.setSelection(spinnerPosition);

        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_property.this, "Cancelled", Toast.LENGTH_SHORT).show();
               onBackPressed();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = getName.getText().toString();
                String address1 = getAddress.getText().toString();
                String owner1 = property[0];

                if( !name1.isEmpty() && !address1.isEmpty() && !owner1.matches(arrayList.get(0))) {
                    if(address1.length() <10){
                        Toast.makeText(edit_property.this, "Please add full Address", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(edit_property.this, name1 + " Updated", Toast.LENGTH_SHORT).show();

                        Intent intentq = new Intent(edit_property.this , property_activity.class);
                        intentq.putExtra("name" , name1);
                        intentq.putExtra("address" , address1);
                        intentq.putExtra("updatePosition" , position);
                        intentq.putExtra("owner" , owner1);
                        intentq.addCategory("update");
                        startActivity(intentq);
                        CustomIntent.customType(edit_property.this, "fadein-to-fadeout");
                        finish();
                    }



                }else{
                    Toast.makeText(edit_property.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(edit_property.this , particularPropertyActivity.class));
        CustomIntent.customType(edit_property.this, "fadein-to-fadeout");
        finish();
    }
}