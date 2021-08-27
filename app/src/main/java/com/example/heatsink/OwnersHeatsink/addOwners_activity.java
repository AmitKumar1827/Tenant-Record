package com.example.heatsink.OwnersHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

public class addOwners_activity extends AppCompatActivity {
    EditText name, email, mobile , idProof, address;
    Button save, cancel;
    Toolbar toolbar;
    Spinner spinner;
    List<Owners> list = new ArrayList<>();
    OwnersDatabase ownersDatabase;
    boolean isName = false, isMobile = false , isEmail = false , isAddress = false , isIdProof = false;



    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owners_activity);
        name = findViewById(R.id.getName_owners);
        email = findViewById(R.id.getEmail_owners);
        mobile = findViewById(R.id.getPhone_owners);
        idProof = findViewById(R.id.getidProof_owners);
        address = findViewById(R.id.getAdd_owners);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        toolbar = findViewById(R.id.toolbar_addOwners);
        spinner = findViewById(R.id.IdProofSpinner);





        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select ID");
        arrayList.add("AADHAR CARD");
        arrayList.add("PAN CARD");
        arrayList.add("PASSPORT");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final String[] idName = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                idName[0] = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });









        setSupportActionBar(toolbar);
        ownersDatabase = (OwnersDatabase) Room.databaseBuilder(getApplicationContext(), OwnersDatabase.class, "owners").allowMainThreadQueries().build();

        list.addAll(ownersDatabase.getOwnersDAO().getOwners());



        final TextView title = toolbar.findViewById(R.id.Title_add_Owners);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(addOwners_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        for(Owners i : list) {
            Log.d("list", i.getName());
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name1, address1, mobile1, email1 , idProof1 ,idName1;
                name1 = name.getText().toString();
                address1 = address.getText().toString();
                mobile1 = mobile.getText().toString();
                email1 = email.getText().toString();
                idName1 = idName[0];
                idProof1 = idProof.getText().toString();
                if( !name1.isEmpty() && !address1.isEmpty() && !mobile1.isEmpty() && !email1.isEmpty() && !idProof1.isEmpty() && !idName1.matches(arrayList.get(0)) ) {


                    check(name1 , mobile1 , email1 , address1 , idProof1);
                    if(isName == true){
                        Toast.makeText(addOwners_activity.this, "Name in use", Toast.LENGTH_SHORT).show();
                    }else if(isMobile ==true){
                        Toast.makeText(addOwners_activity.this, "Mobile in use", Toast.LENGTH_SHORT).show();
                    }else if(isEmail){
                        Toast.makeText(addOwners_activity.this, " Email in use", Toast.LENGTH_SHORT).show();
                    }else if(isAddress){
                        Toast.makeText(addOwners_activity.this, "Address in use", Toast.LENGTH_SHORT).show();
                    }else if(isIdProof){
                        Toast.makeText(addOwners_activity.this, "Member with Same id Exits", Toast.LENGTH_SHORT).show();
                    }
                    else if(mobile1.length() <10){
                        Toast.makeText(addOwners_activity.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
                    }
                    else if(!email1.matches(emailPattern)){
                        Toast.makeText(addOwners_activity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    }
                    else if(address1.length() <10){
                        Toast.makeText(addOwners_activity.this, "Please add full Address", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(addOwners_activity.this, name1 + " added", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        intent.putExtra("Name", name1);
                        intent.putExtra("Email", email1);
                        intent.putExtra("Mobile", mobile1);
                        intent.putExtra("Address", address1);
                        intent.putExtra("idProof", idProof1);
                        intent.putExtra("idName", idName1);
                        setResult(1, intent);
                        finish();
                    }
                }else{
                    Toast.makeText(addOwners_activity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public void check(String name1 , String mobile1 , String email1 , String address1 , String idProof1 ){
        isName = false;
        isAddress = false;
        isMobile = false;
        isIdProof = false;
        isEmail = false;
        for(Owners i : list){
            if(i.getName().matches(name1)){
                isName = true;
                break;
            }
            if(i.getMobile().matches(mobile1)){
                isMobile = true;
                break;
            }
            if(i.getEmail().matches(email1)){
                isEmail = true;
                break;
            }
            if(i.getAddress().matches(address1)){
                isAddress = true;
                break;
            }
            if(i.getIdProof().matches(idProof1)){
                isIdProof = true;
                break;
            }

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(addOwners_activity.this, owners_activity.class));
        finish();
    }
}