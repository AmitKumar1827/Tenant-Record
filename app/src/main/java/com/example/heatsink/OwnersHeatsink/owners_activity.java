package com.example.heatsink.OwnersHeatsink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.heatsink.MainActivity;
import com.example.heatsink.R;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class owners_activity extends AppCompatActivity implements com.example.heatsink.OwnersHeatsink.programingAdapter.OnclickOwners {
    public List<Owners> OwnersList = new ArrayList<>();
    Toolbar toolbar ;
    ImageButton sort;
    com.example.heatsink.OwnersHeatsink.programingAdapter programingAdapter;
    LinearLayout fab;
    ImageView home , setting;
    private OwnersDatabase ownersDatabase;


    public owners_activity(){

    }

//    private BottomSheetBehavior sheetBehavior;
//    private LinearLayout bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners);
        toolbar = findViewById(R.id.toolbar_owners);
        setSupportActionBar(toolbar);
        sort = findViewById(R.id.sort);
        home = toolbar.findViewById(R.id.home_owners);
        setting = toolbar.findViewById(R.id.setting_owners);





//*********************************************************************************************************************************************

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetOwner bottomSheetOwner = new BottomSheetOwner();
                bottomSheetOwner.show(getSupportFragmentManager() , "TAG");
            }
        });



//*********************************************************************************************************************************************




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(owners_activity.this , MainActivity.class));
                CustomIntent.customType(owners_activity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(owners_activity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(owners_activity.this, "fadein-to-fadeout");
                finish();
            }
        });





        final SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().hide();

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getSupportActionBar().show();


                return false;
            }
        });



        ownersDatabase = (OwnersDatabase) Room.databaseBuilder(getApplicationContext(), OwnersDatabase.class, "owners").allowMainThreadQueries().build();
//        new getAllOwnersAsynkTask().execute();
        OwnersList.addAll(ownersDatabase.getOwnersDAO().getOwners());



//        new getAllOwnersAsynkTask().execute();
//        toolbar = findViewById(R.id.toolbar_owners);
//        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(owners_activity.this, addOwners_activity.class);
                startActivityForResult(intent, 1);
                CustomIntent.customType(owners_activity.this, "fadein-to-fadeout");


            }
        });
        int position;
        Bundle bundle = getIntent().getBundleExtra("owners");
        position = getIntent().getIntExtra("position", -1);
        Owners owners;
        if(bundle != null){
            owners = (Owners) bundle.getSerializable("owners");
            deleteOwners(owners, position);
        }


        if(getIntent().hasCategory("update")) {
             final int position1 ;

            position1 = getIntent().getIntExtra("updatePosition", -1);
            String name = getIntent().getStringExtra("name");
            String mobile = getIntent().getStringExtra("mobile");
            String address = getIntent().getStringExtra("address");
            String email = getIntent().getStringExtra("email");
            String idProod = getIntent().getStringExtra("idProof");
            String idName = getIntent().getStringExtra("idName");
                updateOwners(position1, name, mobile, email, address, idProod, idName);
            }

        if(getIntent().hasCategory("filter")){

            String name = getIntent().getStringExtra("code");
            if(name.matches("Most Recent")){
                OwnersList.clear();
                OwnersList.addAll( ownersDatabase.getOwnersDAO().recentOwners());

            }else if(name.matches("By Name A-Z Ascending")){
                OwnersList.clear();
                OwnersList.addAll(ownersDatabase.getOwnersDAO().assendingOwners());
            }else if(name.matches("By Name A-Z Descending")){
                OwnersList.clear();
                OwnersList.addAll(ownersDatabase.getOwnersDAO().descOwners());
            }else if(name.matches("oldest")){
                OwnersList.clear();
                OwnersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
            }else{
                OwnersList.clear();
                OwnersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
            }
        }



//        if(getIntent().hasExtra(String.valueOf(DEFAULT_KEYS_DIALER))) {
//
//            int pos = getIntent().getIntExtra("pos", -1);
//
//
//             OwnersList.clear();
//             Owners owners = OwnersList.get(pos);
//            Toast.makeText(this, pos, Toast.LENGTH_SHORT).show();
//
//            if(pos != -1) {
//                deleteOwners(owners, pos);
//            }
//
//        }

//        if(getIntent().hasCategory("Edit")){
//            Intent eintent = getIntent();
//            String pos = eintent.getStringExtra("pos");
//            String name = eintent.getStringExtra("Name");
//            String email = eintent.getStringExtra("Email");
//            String mobile = eintent.getStringExtra("Mobile");
//            String Address = eintent.getStringExtra("Address");
//            String idProof = eintent.getStringExtra("idProof");
//            int value = Integer.parseInt(pos);
//            if(name  != null && email != null && mobile != null && Address != null && idProof != null){
//                Toast.makeText(this, "yesssss", Toast.LENGTH_SHORT).show();
//
//
//                updateOwners(value,name,mobile,email , Address, idProof);
//            }
//
//        }

//        Intent intent = getIntent();
//
//        String name = intent.getStringExtra("Name");
//        String email = intent.getStringExtra("Email");
//        String mobile = intent.getStringExtra("Mobile");
//        String Address = intent.getStringExtra("Address");
//        String idProof = intent.getStringExtra("idProof");
//
//        if(name  != null && email != null && mobile != null && Address != null && idProof != null){
//            createOwners(0,name,mobile,email , Address, idProof);
//
//        }


        RecyclerView program = findViewById(R.id.recycler);
        program.setLayoutManager(new LinearLayoutManager(this));
        program.addItemDecoration(new DividerItemDecoration(owners_activity.this, DividerItemDecoration.VERTICAL));
        programingAdapter = new programingAdapter(OwnersList, this);
        program.setAdapter(programingAdapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                    programingAdapter.getFilter().filter(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                    programingAdapter.getFilter().filter(s);
                    programingAdapter.notifyDataSetChanged();

                return false;
            }
        });




    }
    public void deleteOwners(Owners owners, int value){
            OwnersList.remove(value);
        ownersDatabase.getOwnersDAO().deleteOwners(owners);


    }

    private void updateOwners(int position, String name, String mobile, String email, String address, String idProof, String idName){
        Owners owners = OwnersList.get(position);
        owners.setName(name);
        owners.setMobile(mobile);
        owners.setEmail(email);
        owners.setAddress(address);
        owners.setIdProof(idProof);
        owners.setIdName(idName);

        long id =  owners.getId();
        ownersDatabase.getOwnersDAO().updateOwners(id,name,mobile,email,address,idProof,idName);
        OwnersList.set(position,owners);




    }


    public void createOwners(long id,String name, String mobile, String email, String address, String idProof, String idName){

        long iid =  ownersDatabase.getOwnersDAO().addOwners(new Owners(id,name,mobile,email,address,idProof, idName));
        Owners owners = ownersDatabase.getOwnersDAO().getOwners(iid);
        OwnersList.add(owners);
        programingAdapter.notifyDataSetChanged();

//        new createOwnersAsynkTask().equals(new Owners(id,name,mobile,email,address,idProof));



    }



    @Override
    public void onListOwners(int position) {
            Intent intent = new Intent(owners_activity.this , particularOwnerActivity.class);
           Owners owners  = ownersDatabase.getOwnersDAO().getOwners(OwnersList.get(position).getId());
            Bundle bundle = new Bundle();
            bundle.putSerializable("owners" , owners);
            intent.putExtra("owners" , bundle);
            intent.putExtra("position" , position);
            startActivity(intent);
        CustomIntent.customType(owners_activity.this, "fadein-to-fadeout");

    }
//
//
//    private  class getAllOwnersAsynkTask  extends AsyncTask<Void , Void , Void> {
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            OwnersList.addAll(ownersDatabase.getOwnersDAO().getOwners());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            programingAdapter.notifyDataSetChanged();
//        }
//    }

//
//    private class createOwnersAsynkTask extends AsyncTask<Owners, Void, Void>{
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            programingAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected Void doInBackground(Owners... owners) {
//
//
//            long iid =  ownersDatabase.getOwnersDAO().addOwners(owners[0]);
//            Owners owner = ownersDatabase.getOwnersDAO().getOwners(iid);
//            OwnersList.add(owner);
//
//
//            return null;
//        }
//    }
//
//    private class updateContactsAsynktask extends AsyncTask<Owners, Void, Void>{
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//            super.onPostExecute(aVoid);
//            programingAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected Void doInBackground(Owners... owners) {
//
//            return null;
//        }
//    }

//    private class deleteContactsAsynkTask extends AsyncTask<Owners,Void,Void>{
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            programingAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected Void doInBackground(Owners... owners) {
//
//            programingAdapter.notifyDataSetChanged();
//            return null;
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==1){
            String name = intent.getStringExtra("Name");
            String email = intent.getStringExtra("Email");
            String mobile = intent.getStringExtra("Mobile");
            String Address = intent.getStringExtra("Address");
            String idProof = intent.getStringExtra("idProof");
            String idName = intent.getStringExtra("idName");

            if(name=="0"){
                Toast.makeText(owners_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else{
                createOwners(0,name,mobile,email , Address, idProof, idName);

            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(owners_activity.this, MainActivity.class));
        finish();
        CustomIntent.customType(owners_activity.this, "fadein-to-fadeout");
    }

}