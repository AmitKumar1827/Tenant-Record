package com.example.heatsink.TenantsHeatsink;

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

import com.example.heatsink.MainActivity;
import com.example.heatsink.PropertyHeatsink.BottomSheetProperty;
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.PropertyHeatsink.addProperty_activity;
import com.example.heatsink.PropertyHeatsink.particularPropertyActivity;
import com.example.heatsink.PropertyHeatsink.propertyAdapter;
import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.R;
import com.example.heatsink.TransactionHeatsink.transaction_activity;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class tenants_activity extends AppCompatActivity implements com.example.heatsink.TenantsHeatsink.tenantAdapter.OnclickTenant {

    Toolbar toolbar ;
    ImageButton sort;
    com.example.heatsink.OwnersHeatsink.programingAdapter programingAdapter;
    LinearLayout addTenants;
    ImageView home , setting;
    tenantAdapter tenantAdapter;
    private  TenantDatabase tenantDatabase;
    List<Tenants> tenantsList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        toolbar = findViewById(R.id.toolbar_tenants);
        addTenants = findViewById(R.id.addTenants);
        home = toolbar.findViewById(R.id.home_tenants);
        setting = toolbar.findViewById(R.id.setting_tenants);
        setSupportActionBar(toolbar);
        sort = findViewById(R.id.sortTenants);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTenants bottomSheetBank = new BottomSheetTenants();
                bottomSheetBank.show(getSupportFragmentManager() , "TAG");
            }
        });

        tenantDatabase = (TenantDatabase) Room.databaseBuilder(getApplicationContext(), TenantDatabase.class, "tenants").allowMainThreadQueries().build();
        tenantsList.addAll(tenantDatabase.getTenantDAO().getTenant());

        RecyclerView program = findViewById(R.id.recyclerTenant);
        program.setLayoutManager(new LinearLayoutManager(this));
        program.addItemDecoration(new DividerItemDecoration(tenants_activity.this, DividerItemDecoration.VERTICAL));
        tenantAdapter = new tenantAdapter(tenantsList , this);
        program.setAdapter(tenantAdapter);


        int position;
        Bundle bundle = getIntent().getBundleExtra("tenants");
        position = getIntent().getIntExtra("position", -1);
        Tenants tenants;
        if(bundle != null){
            tenants = (Tenants) bundle.getSerializable("tenants");
            deleteProperty(tenants, position);
        }



        if(getIntent().hasCategory("update")) {
            final int position1 ;

            position1 = getIntent().getIntExtra("updatePosition", -1);
            String name = getIntent().getStringExtra("name");

            String idproperty = getIntent().getStringExtra("idproperty");
            String mobile  = getIntent().getStringExtra("mobile");
            String email = getIntent().getStringExtra("email");
            String idProof = getIntent().getStringExtra("idproof");
            String idProofName = getIntent().getStringExtra("idProofName");
            String rent = getIntent().getStringExtra("rent");
            updateTenant(position1, name, mobile, email, idproperty ,idProof ,idProofName,rent);
        }

        if(getIntent().hasCategory("filter")){

            String name = getIntent().getStringExtra("code");
            if(name.matches("Most Recent")){
                tenantsList.clear();
                tenantsList.addAll( tenantDatabase.getTenantDAO().recentTenants());

            }else if(name.matches("By Name A-Z Ascending")){
                tenantsList.clear();
                tenantsList.addAll( tenantDatabase.getTenantDAO().assendingTenant());

            }else if(name.matches("By Name A-Z Descending")){
                tenantsList.clear();
                tenantsList.addAll( tenantDatabase.getTenantDAO().descTenant());

            }else if(name.matches("oldest")){
                tenantsList.clear();
                tenantsList.addAll( tenantDatabase.getTenantDAO().getTenant());

            }else{
                tenantsList.clear();
                tenantsList.addAll( tenantDatabase.getTenantDAO().getTenant());

            }
        }

        final SearchView searchView = findViewById(R.id.searchViewTenant);

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


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tenants_activity.this , MainActivity.class));
                CustomIntent.customType(tenants_activity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tenants_activity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(tenants_activity.this, "fadein-to-fadeout");
            }
        });

        addTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tenants_activity.this, addTenant_activity.class);
                startActivityForResult(intent, 4);
                CustomIntent.customType(tenants_activity.this, "fadein-to-fadeout");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                tenantAdapter.getFilter().filter(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                tenantAdapter.getFilter().filter(s);
                tenantAdapter.notifyDataSetChanged();

                return false;
            }
        });

    }

    public void createTenant(long id,String name,String mobile , String email , String idproperty , String idproof, String idName , String rent){

        long iid =  tenantDatabase.getTenantDAO().addTenant(new Tenants(name, idproperty, id, mobile,  email, idproof , idName , rent));
        Tenants tenants = tenantDatabase.getTenantDAO().getTenant(iid);
        tenantsList.add(tenants);
        tenantAdapter.notifyDataSetChanged();


//        new createOwnersAsynkTask().equals(new Owners(id,name,mobile,email,address,idProof));



    }

    public void deleteProperty(Tenants tenants, int value){
        tenantsList.remove(value);
        tenantDatabase.getTenantDAO().deleteTenant(tenants);


    }

    private void updateTenant(int position, String name,String mobile , String email , String idproperty, String idProof ,String idProofName,String rent){
        Tenants tenants = tenantsList.get(position);
        tenants.setTenantName(name);
        tenants.setTenantEmail(email);
        tenants.setTenantMobile(mobile);
        tenants.setTenantPropertyId(idproperty);
        tenants.setTenantIdProof(idProof);
        tenants.setIdProofName(idProofName);
        tenants.setTenantRent(rent);


        long id =  tenants.getTenantId();
        tenantDatabase.getTenantDAO().updateTenant(id,name,mobile ,email , idproperty , idProof ,idProofName,rent);
        tenantsList.set(position,tenants);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==4){
            String name = intent.getStringExtra("name");
            String mobile = intent.getStringExtra("mobile");
            String email = intent.getStringExtra("email");
            String idproperty = intent.getStringExtra("idproperty");
            String idName = intent.getStringExtra("idName");
            String idproof = intent.getStringExtra("idproof");
            String rent = intent.getStringExtra("rent");



            if(name=="0"){
                Toast.makeText(tenants_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else{
                createTenant(0 , name, mobile , email , idproperty , idproof ,idName , rent);

            }
        }

    }

    @Override
    public void onListTenant(int position) {
        Intent intent = new Intent(tenants_activity.this , particularTenantActivity.class);
        Tenants tenants  = tenantDatabase.getTenantDAO().getTenant(tenantsList.get(position).getTenantId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("tenants" , tenants);
        intent.putExtra("tenants" , bundle);
        intent.putExtra("position" , position);
        startActivity(intent);
        CustomIntent.customType(tenants_activity.this, "fadein-to-fadeout");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(tenants_activity.this , MainActivity.class));
        CustomIntent.customType(tenants_activity.this, "fadein-to-fadeout");
        finish();
    }
}