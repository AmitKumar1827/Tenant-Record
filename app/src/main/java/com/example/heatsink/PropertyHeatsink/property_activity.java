package com.example.heatsink.PropertyHeatsink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.heatsink.BankHeatsink.BottomSheetBank;
import com.example.heatsink.BankHeatsink.banks_activity;
import com.example.heatsink.MainActivity;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.OwnersHeatsink.addOwners_activity;
import com.example.heatsink.OwnersHeatsink.owners_activity;
import com.example.heatsink.OwnersHeatsink.particularOwnerActivity;
import com.example.heatsink.OwnersHeatsink.programingAdapter;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class property_activity extends AppCompatActivity  implements com.example.heatsink.PropertyHeatsink.propertyAdapter.OnclickProperty {
    Toolbar toolbar ;
    ImageButton sort;
    com.example.heatsink.OwnersHeatsink.programingAdapter programingAdapter;
    LinearLayout addProperty;
    ImageView home , setting;
    PropertyDatabase propertyDatabase;
    List<Property> propertyList = new ArrayList<>();
    propertyAdapter propertyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertyy);
        toolbar = findViewById(R.id.toolbar_property);
        home = toolbar.findViewById(R.id.home_property);
        setting = toolbar.findViewById(R.id.setting_property);
        addProperty = findViewById(R.id.addProperty);
        setSupportActionBar(toolbar);
        sort = findViewById(R.id.sortProperty);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetProperty bottomSheetBank = new BottomSheetProperty();
                bottomSheetBank.show(getSupportFragmentManager() , "TAG");
            }
        });


        final SearchView searchView = findViewById(R.id.searchViewProperty);

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
        propertyDatabase = Room.databaseBuilder(getApplicationContext(), PropertyDatabase.class, "property").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        propertyList.addAll(propertyDatabase.getPropertyDAO().getProperty());

        RecyclerView program = findViewById(R.id.recyclerProperty);
        program.setLayoutManager(new LinearLayoutManager(this));
        program.addItemDecoration(new DividerItemDecoration(property_activity.this, DividerItemDecoration.VERTICAL));
        propertyAdapter = new propertyAdapter(propertyList , this);
        program.setAdapter(propertyAdapter);


        int position;
        Bundle bundle = getIntent().getBundleExtra("property");
        position = getIntent().getIntExtra("position", -1);
        Property property;
        if(bundle != null){
            property = (Property) bundle.getSerializable("property");
            deleteProperty(property, position);
        }


        if(getIntent().hasCategory("update")) {
            final int position1 ;

            position1 = getIntent().getIntExtra("updatePosition", -1);
            String name = getIntent().getStringExtra("name");
            String owner = getIntent().getStringExtra("owner");
            String address = getIntent().getStringExtra("address");

            updateProperty(position1, name, address ,owner);
        }

        if(getIntent().hasCategory("filter")){

            String name = getIntent().getStringExtra("code");
            if(name.matches("Most Recent")){
                propertyList.clear();
                propertyList.addAll( propertyDatabase.getPropertyDAO().recentProperty());

            }else if(name.matches("By Name A-Z Ascending")){
                propertyList.clear();
                propertyList.addAll( propertyDatabase.getPropertyDAO().assendingProperty());
            }else if(name.matches("By Name A-Z Descending")){
                propertyList.clear();
                propertyList.addAll( propertyDatabase.getPropertyDAO().descProperty());
            }else if(name.matches("oldest")){
                propertyList.clear();
                propertyList.addAll( propertyDatabase.getPropertyDAO().getProperty());
            }else{
                propertyList.clear();
                propertyList.addAll( propertyDatabase.getPropertyDAO().getProperty());
            }
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                propertyAdapter.getFilter().filter(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                propertyAdapter.getFilter().filter(s);
                propertyAdapter.notifyDataSetChanged();

                return false;
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(property_activity.this , MainActivity.class));
                CustomIntent.customType(property_activity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(property_activity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(property_activity.this, "fadein-to-fadeout");
            }
        });

        addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(property_activity.this, addProperty_activity.class);
                startActivityForResult(intent, 3);
                CustomIntent.customType(property_activity.this, "fadein-to-fadeout");
            }
        });
    }


    public void createProperty(long id,String name,String Address, String owner){

        long iid =  propertyDatabase.getPropertyDAO().addProperty(new Property(name,owner, Address , id));
        Property property = propertyDatabase.getPropertyDAO().getProperty(iid);
        propertyList.add(property);
        propertyAdapter.notifyDataSetChanged();

//        new createOwnersAsynkTask().equals(new Owners(id,name,mobile,email,address,idProof));



    }

    private void updateProperty(int position, String name,String address, String owner){
        Property property = propertyList.get(position);
        property.setPropertyName(name);
        property.setPropertyOwnerName(owner);
        property.setPropertyAddress(address);


        long id =  property.getPropertyId();
        propertyDatabase.getPropertyDAO().updateProperty(id,name,address,owner);
        propertyList.set(position,property);




    }

    public void deleteProperty(Property property, int value){
        propertyList.remove(value);
        propertyDatabase.getPropertyDAO().deleteProperty(property);


    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(property_activity.this , MainActivity.class));
        CustomIntent.customType(property_activity.this, "fadein-to-fadeout");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==3){
            String name = intent.getStringExtra("Name");

            String Address = intent.getStringExtra("Address");
            String owner = intent.getStringExtra("owner");


            if(name=="0"){
                Toast.makeText(property_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else{
                createProperty(0 , name, Address , owner);

            }
        }

    }

    @Override
    public void onListProperty(int position) {
        Intent intent = new Intent(property_activity.this , particularPropertyActivity.class);
        Property property  = propertyDatabase.getPropertyDAO().getProperty(propertyList.get(position).getPropertyId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("property" , property);
        intent.putExtra("property" , bundle);
        intent.putExtra("position" , position);
        startActivity(intent);
        CustomIntent.customType(property_activity.this, "fadein-to-fadeout");
    }
}