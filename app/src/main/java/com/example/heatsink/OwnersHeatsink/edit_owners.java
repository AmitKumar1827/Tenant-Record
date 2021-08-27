package com.example.heatsink.OwnersHeatsink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.heatsink.MainActivity;
import com.example.heatsink.R;
import com.example.heatsink.setting;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class edit_owners extends AppCompatActivity {
    EditText name, email, mobile , idProof, address;
    Button update, back;
    Toolbar toolbar;
    Owners owners;
    Bundle bundle;
    Spinner spinner;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_owners);

        name = findViewById(R.id.updateName_owners);
        email = findViewById(R.id.updateEmail_owners);
        mobile = findViewById(R.id.updatePhone_owners);
        idProof = findViewById(R.id.updateidProof_owners);
        address = findViewById(R.id.updateAdd_owners);
        update = findViewById(R.id.update);
        spinner = findViewById(R.id.IdProofSpinnerEdit);
        back = findViewById(R.id.back);
        toolbar = findViewById(R.id.toolbar_editOwners);
        setSupportActionBar(toolbar);


        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select ID");
        arrayList.add("AADHAR CARD");
        arrayList.add("PAN CARD");
        arrayList.add("PASSPORT");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
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



        final int position;
        bundle = getIntent().getBundleExtra("owners");
        position = getIntent().getIntExtra("position", -1);

        if(bundle != null) {
            owners = (Owners) bundle.getSerializable("owners");
            name.setText(owners.getName());
            email.setText(owners.getEmail());
            mobile.setText(owners.getMobile());
            idProof.setText(owners.getIdProof());
            address.setText(owners.getAddress());
            String idName1 = owners.getIdName();
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(idName1);
            spinner.setSelection(spinnerPosition);

        }





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_owners.this, "Cancelled", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });








        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String name1 = name.getText().toString();
                String mobile1 = mobile.getText().toString();
                 String email1 = email.getText().toString();
                 String address1 = address.getText().toString();
                 String idProof1 = idProof.getText().toString();
                 String idName1 = idName[0];

                Log.d("edit0 " , name1);
                Log.d("edit0 " , mobile1);
                Log.d("edit0 " , email1);


                if( !name1.isEmpty() && !address1.isEmpty() && !mobile1.isEmpty() && !email1.isEmpty() && !idProof1.isEmpty() && !idName1.matches(arrayList.get(0)) ) {



                    if(mobile1.length() <10){
                        Toast.makeText(edit_owners.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
                    }
                    else if(!email1.matches(emailPattern)){
                        Toast.makeText(edit_owners.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    }
                    else if(address1.length() <10){
                        Toast.makeText(edit_owners.this, "Please add full Address", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(edit_owners.this, name1 + " Updated", Toast.LENGTH_SHORT).show();

                        Intent intentq = new Intent(edit_owners.this , owners_activity.class);
                        intentq.putExtra("name" , name1);
                        intentq.putExtra("mobile" , mobile1);
                        intentq.putExtra("email" , email1);
                        intentq.putExtra("address" , address1);
                        intentq.putExtra("idProof" , idProof1);
                        intentq.putExtra("idName" , idName1);
                        intentq.putExtra("updatePosition" , position);
                        intentq.addCategory("update");
                        startActivity(intentq);
                        finish();
                    }
                }else{
                    Toast.makeText(edit_owners.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(edit_owners.this, particularOwnerActivity.class));
        CustomIntent.customType(edit_owners.this, "fadein-to-fadeout");
        finish();
    }

    //    public void check(String name1 , String mobile1 , String email1 , String address1 , String idProof1 ){
//        isName = false;
//        isAddress = false;
//        isMobile = false;
//        isIdProof = false;
//        isEmail = false;
//        for(Owners i : list){
//            if(i.getName().matches(name1)){
//                isName = true;
//                break;
//            }
//            if(i.getMobile().matches(mobile1)){
//                isMobile = true;
//                break;
//            }
//            if(i.getEmail().matches(email1)){
//                isEmail = true;
//                break;
//            }
//            if(i.getAddress().matches(address1)){
//                isAddress = true;
//                break;
//            }
//            if(i.getIdProof().matches(idProof1)){
//                isIdProof = true;
//                break;
//            }
//
//        }
//
//    }
}