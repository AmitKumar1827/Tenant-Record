package com.example.heatsink.BankHeatsink;

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
import com.example.heatsink.OwnersHeatsink.BottomSheetOwner;
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

public class banks_activity extends AppCompatActivity implements BankAdapter.OnclickBank {
    Toolbar toolbar ;
    ImageButton sort;
    com.example.heatsink.OwnersHeatsink.programingAdapter programingAdapter;
    LinearLayout addBanks;
    ImageView home , setting;
    List<Banks> bankList = new ArrayList<>();
    private BanksDatabase banksDatabase;
    BankAdapter bankAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);

       try {
           toolbar = findViewById(R.id.toolbar_banks);
           addBanks = findViewById(R.id.addBanks);
           home = toolbar.findViewById(R.id.home_banks);
           setting = toolbar.findViewById(R.id.setting_banks);
           setSupportActionBar(toolbar);
           sort = findViewById(R.id.sortBank);

           sort.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   BottomSheetBank bottomSheetBank = new BottomSheetBank();
                   bottomSheetBank.show(getSupportFragmentManager(), "TAG");
               }
           });

           SearchView searchView = findViewById(R.id.searchViewBank);

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
                   startActivity(new Intent(banks_activity.this, MainActivity.class));
                   CustomIntent.customType(banks_activity.this, "fadein-to-fadeout");
                   finish();
               }
           });

           setting.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(banks_activity.this, com.example.heatsink.setting.class));
                   CustomIntent.customType(banks_activity.this, "fadein-to-fadeout");
               }
           });

           addBanks.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(banks_activity.this, addBank_activity.class);
                   startActivityForResult(intent, 2);
                   CustomIntent.customType(banks_activity.this, "fadein-to-fadeout");

               }
           });

           banksDatabase = (BanksDatabase) Room.databaseBuilder(getApplicationContext(), BanksDatabase.class, "banks").allowMainThreadQueries().build();
           bankList.addAll(banksDatabase.getBanksDAO().getBanks());


           final int position1;
           Bundle bundle = getIntent().getBundleExtra("banks");
           position1 = getIntent().getIntExtra("position", -1);
           Banks banks;
           if (bundle != null) {
               banks = (Banks) bundle.getSerializable("banks");
               deleteBanks(banks, position1);
           }

           if (getIntent().hasCategory("update")) {
               final int position;

               position = getIntent().getIntExtra("updatePosition", -1);
               String bank = getIntent().getStringExtra("bank");
               String accountNumber = getIntent().getStringExtra("account");
               String ifsc = getIntent().getStringExtra("ifsc");
               String holder = getIntent().getStringExtra("holder");

               updateOwners(position, bank, accountNumber, ifsc, holder);
           }


           if (getIntent().hasCategory("filter")) {

               String name = getIntent().getStringExtra("code");
               if (name.matches("Most Recent")) {
                   bankList.clear();
                   bankList.addAll(banksDatabase.getBanksDAO().recentBank());

               } else if (name.matches("By Name A-Z Ascending")) {
                   bankList.clear();
                   bankList.addAll(banksDatabase.getBanksDAO().assendingBank());
               } else if (name.matches("By Name A-Z Descending")) {
                   bankList.clear();
                   bankList.addAll(banksDatabase.getBanksDAO().descBank());
               } else if (name.matches("oldest")) {
                   bankList.clear();
                   bankList.addAll(banksDatabase.getBanksDAO().getBanks());
               } else {
                   bankList.clear();
                   bankList.addAll(banksDatabase.getBanksDAO().getBanks());
               }
           }

           RecyclerView program = findViewById(R.id.recyclerBank);
           program.setLayoutManager(new LinearLayoutManager(this));
           program.addItemDecoration(new DividerItemDecoration(banks_activity.this, DividerItemDecoration.VERTICAL));
           bankAdapter = new BankAdapter(bankList, this);
           program.setAdapter(bankAdapter);

           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String s) {

                   bankAdapter.getFilter().filter(s);


                   return false;
               }

               @Override
               public boolean onQueryTextChange(String s) {

                   bankAdapter.getFilter().filter(s);
                   bankAdapter.notifyDataSetChanged();

                   return false;
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }




    }

    public void createBank(long id, String bankName , String bankNumber , String ifsc , String accountHolder ){

        long iid =  banksDatabase.getBanksDAO().addBanks(new Banks(bankName,bankNumber,ifsc,accountHolder,id));
        Banks banks = banksDatabase.getBanksDAO().getBanks(iid);
        bankList.add(banks);
        bankAdapter.notifyDataSetChanged();


//        new createOwnersAsynkTask().equals(new Owners(id,name,mobile,email,address,idProof));



    }

    public void deleteBanks(Banks banks, int value){
        bankList.remove(value);
        banksDatabase.getBanksDAO().deleteBanks(banks);


    }
    public void updateOwners(int position , String bank, String account , String ifsc , String holder){
        Banks banks = bankList.get(position);
        banks.setBankName(bank);
        banks.setBankAccountNumber(account);
        banks.setCodeIFSC(ifsc);
        banks.setAccountHolder(holder);


        long id =  banks.getAccountId();
        banksDatabase.getBanksDAO().updateBanks(id,bank , account , ifsc , holder);
        bankList.set(position,banks);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==2){
            String bankName = intent.getStringExtra("bank");
            String bankNumber = intent.getStringExtra("account");
            String ifsc = intent.getStringExtra("ifsc");
            String accountHolder = intent.getStringExtra("holder");

            createBank(0, bankName ,bankNumber , ifsc, accountHolder);


        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(banks_activity.this, MainActivity.class));
        CustomIntent.customType(banks_activity.this, "fadein-to-fadeout");
        finish();
    }

    @Override
    public void onListBank(int position) {



        Intent intent = new Intent(banks_activity.this , particularBankActivity.class);
        Banks banks  = banksDatabase.getBanksDAO().getBanks(bankList.get(position).getAccountId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("banks" , banks);
        intent.putExtra("banks" , bundle);
        intent.putExtra("position" , position);
        startActivity(intent);
        CustomIntent.customType(banks_activity.this, "fadein-to-fadeout");

    }

}