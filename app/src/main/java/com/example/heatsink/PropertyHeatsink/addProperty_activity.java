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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.OwnersHeatsink.addOwners_activity;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

public class addProperty_activity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView home , setting;
    Button cancel , save ;
    EditText getName , getAddress;

    PropertyDatabase propertyDatabase;
    OwnersDatabase ownersDatabase;
    Spinner spinner;
    List<Property> list3 = new ArrayList<>();
    List<Owners> listcheck1 = new ArrayList<>();
    Boolean  isAddress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property_activity);
        toolbar = findViewById(R.id.toolbar_addProperty);
        setSupportActionBar(toolbar);
        home = toolbar.findViewById(R.id.homeAddProperty);
        setting = toolbar.findViewById(R.id.settingAddProperty);
        spinner = findViewById(R.id.OwnerPropertyName);
        save = findViewById(R.id.saveAddProperty);
        cancel = findViewById(R.id.cancelAddProperty);

        getName = findViewById(R.id.getNameAddProperty);
        getAddress = findViewById(R.id.addressAddProperty);


        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Owners");
        propertyDatabase = (PropertyDatabase) Room.databaseBuilder(getApplicationContext(), PropertyDatabase.class, "property").allowMainThreadQueries().build();
//        new getAllOwnersAsynkTask().execute();
        list3.addAll(propertyDatabase.getPropertyDAO().getProperty());
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





        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(addProperty_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                onBackPressed();


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1, address1, property1;
                name1 = getName.getText().toString();
                property1 = property[0];
                address1 = getAddress.getText().toString();

                if( !name1.isEmpty() && !address1.isEmpty() && !property1.matches(arrayList.get(0)) ){

                    Check(address1);
                    if(isAddress){
                        Toast.makeText(addProperty_activity.this, "Address in use", Toast.LENGTH_SHORT).show();
                    }
                    else if(address1.length() <10){
                        Toast.makeText(addProperty_activity.this, "Please add full Address", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(addProperty_activity.this, name1 + " added", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        intent.putExtra("Name", name1);
                        intent.putExtra("Address", address1);
                        intent.putExtra("owner" , property1);
                        setResult(3, intent);
                        finish();
                    }

                }else{
                    Toast.makeText(addProperty_activity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
    public void Check( String Address ){

        isAddress = false ;

        for(Property p : list3){
            if(p.getPropertyAddress().matches(Address)){
                isAddress = true;
            }

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(addProperty_activity.this, property_activity.class));
        finish();
    }

}