package com.example.heatsink.TransactionHeatsink;

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
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.R;
import com.example.heatsink.TenantsHeatsink.BottomSheetTenants;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.particularTenantActivity;
import com.example.heatsink.TenantsHeatsink.tenantAdapter;
import com.example.heatsink.TenantsHeatsink.tenants_activity;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class transaction_activity extends AppCompatActivity  implements TransactionAdapter.OnclickTransaction {
    Toolbar toolbar ;
    ImageButton sort;
    com.example.heatsink.OwnersHeatsink.programingAdapter programingAdapter;
    LinearLayout addTransaction;
    ImageView home , setting;
    TransactionDatabase transactionDatabase;
    TransactionAdapter transactionAdapter;
    List<Transactions> transactionsList = new ArrayList<>();
    PropertyDatabase propertyDatabase;
    List<Property> propertyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        toolbar = findViewById(R.id.toolbar_transaction);
        addTransaction = findViewById(R.id.Transaction);
        home = toolbar.findViewById(R.id.home_transaction);
        setting = toolbar.findViewById(R.id.setting_transaction);
        setSupportActionBar(toolbar);
        sort = findViewById(R.id.sortTransaction);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTransaction bottomSheetBank = new BottomSheetTransaction();
                bottomSheetBank.show(getSupportFragmentManager() , "TAG");
            }
        });


        final SearchView searchView = findViewById(R.id.searchViewTransaction);

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


        transactionDatabase = (TransactionDatabase) Room.databaseBuilder(getApplicationContext() ,
                TransactionDatabase.class , "Transactions").allowMainThreadQueries().build();
        transactionsList.addAll(transactionDatabase.getTransactionDAO().getTransaction());

        RecyclerView program = findViewById(R.id.recyclerTransaction);
        program.setLayoutManager(new LinearLayoutManager(this));
        program.addItemDecoration(new DividerItemDecoration(transaction_activity.this, DividerItemDecoration.VERTICAL));
        transactionAdapter = new TransactionAdapter(transactionsList, this);
        program.setAdapter(transactionAdapter);


        int position;
        Bundle bundle = getIntent().getBundleExtra("transaction");
        position = getIntent().getIntExtra("position", -1);
        Transactions transactions;
        if(bundle != null){
            transactions = (Transactions) bundle.getSerializable("transaction");
            deleteTransaction(transactions, position);
        }

        if(getIntent().hasCategory("update")) {
            final int position1 ;

            position1 = getIntent().getIntExtra("updatePosition", -1);

            String date = getIntent().getStringExtra("date");
            String time  = getIntent().getStringExtra("time");
            String TenantName = getIntent().getStringExtra("name");
            String propertyName = getIntent().getStringExtra("property");
            String OwnerId = getIntent().getStringExtra("owner");
            String holder = getIntent().getStringExtra("holder");
            String bank = getIntent().getStringExtra("bank");
            String amount = getIntent().getStringExtra("amount");
            int dateP = getIntent().getIntExtra("dateP" , -1);
            int monthP = getIntent().getIntExtra("monthP" , -1);
            int yearP = getIntent().getIntExtra("yearP" , -1);
            String mop = getIntent().getStringExtra("mop");
            updateTransaction(position1, TenantName , propertyName , OwnerId , holder , bank , amount , mop, date, time , dateP , monthP ,yearP);
        }

        if(getIntent().hasCategory("MainTransaction")){
            Intent intent = new Intent(transaction_activity.this, addTransaction_activity.class);
            startActivityForResult(intent, 5);
            CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");

        }

        if(getIntent().hasCategory("filter")){

            String name = getIntent().getStringExtra("code");
            String owner = getIntent().getStringExtra("owner");
            String property = getIntent().getStringExtra("property");
            String bank = getIntent().getStringExtra("bank");
            String tenant = getIntent().getStringExtra("tenant");
            String startingDate = getIntent().getStringExtra("starting");
            String endingDate = getIntent().getStringExtra("ending");
            int sdate = getIntent().getIntExtra("sdate" , -1);
            int smonth = getIntent().getIntExtra("smonth" , -1);
            int syear = getIntent().getIntExtra("syear" , -1);
            int edate = getIntent().getIntExtra("edate" , -1);
            int emonth = getIntent().getIntExtra("emonth" , -1);
            int eyear = getIntent().getIntExtra("eyear" , -1);

            if(name.matches("Most Recent")){
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().recenttransaction());


                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);




            }else if(name.matches("By Name A-Z Ascending")){
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().assendingTransaction());


                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);









            }else if(name.matches("By Name A-Z Descending")){


                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().descTransaction());


                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);






            }else if(name.matches("oldest")){
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().getTransaction());


                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);


            }
            else if(name.matches("by Amount max")){
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().descamount());


                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);


            }
            else if(name.matches("by Amount min")){
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().ascamount());

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);


            }
            else{
                List<Transactions> finalList = new ArrayList<>();
                List<Transactions> finalList2 = new ArrayList<>();
                List<Transactions> finalList3 = new ArrayList<>();

                finalList.addAll( transactionDatabase.getTransactionDAO().getTransaction());

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }



                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}
                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank) && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner) && t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) && t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getTenantProperty().matches(property)  && t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getOwnerName().matches(owner)  && t.getBankAccount().matches(bank) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && !tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantName().matches(tenant))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(!owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList) {
                        if (t.getOwnerName().matches(owner))
                            finalList2.add(t);
                    }

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&!bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if(t.getBankAccount().matches(bank))
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && !property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )){
                    for(Transactions t:finalList){
                        if( t.getTenantProperty().matches(property) )
                            finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                if(owner.matches("Select Owner") && property.matches("Select Property") &&bank.matches("Select Bank") && tenant.matches("Select Tenant" )) {
                    for (Transactions t : finalList) {

                        finalList2.add(t);}

                    if (!startingDate.matches("Starting Date") && !endingDate.matches("Ending Date")) {
                        for (Transactions t1 : finalList2) {
                            if (t1.getYearInt() >= syear && t1.getYearInt() <= eyear) {
                                if (t1.getMonthInt() >= smonth && t1.getMonthInt() <= emonth) {
                                    if (t1.getDateInt() >= sdate && t1.getDateInt() <= edate) {
                                        finalList3.add(t1);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                    else{
                        finalList3.addAll(finalList2);
                    }

                }

                transactionsList.clear();
                transactionsList.addAll(finalList3);
            }
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(transaction_activity.this , MainActivity.class));
                CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(transaction_activity.this , com.example.heatsink.setting.class));
                CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");
            }
        });

        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(transaction_activity.this, addTransaction_activity.class);
                startActivityForResult(intent, 5);
                CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                transactionAdapter.getFilter().filter(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                transactionAdapter.getFilter().filter(s);
                transactionAdapter.notifyDataSetChanged();

                return false;
            }
        });




    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        if(requestCode==2){
//            String name = intent.getStringExtra("Name");
//            String email = intent.getStringExtra("Email");
//            String mobile = intent.getStringExtra("Mobile");
//            String Address = intent.getStringExtra("Address");
//            String idProof = intent.getStringExtra("idProof");
//
//            if(name=="0"){
//                Toast.makeText(transaction_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//            }
//            else{
//
//                Toast.makeText(this, "Add owners", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    public void deleteTransaction(Transactions transactions, int position){
        transactionsList.remove(position);
        transactionDatabase.getTransactionDAO().deleteTransaction(transactions);

    }

    public void updateTransaction(int position1, String TenantName ,String propertyName ,String OwnerId ,
                                  String holder ,String bank ,String amount ,String mop,String date,String time
                                ,int d, int m ,int y){
        Transactions transactions = transactionsList.get(position1);
        transactions.setTenantName(TenantName);
        transactions.setTenantProperty(propertyName);
        transactions.setOwnerName(OwnerId);
        transactions.setAccountHolder(holder);
        transactions.setBankAccount(bank);
        transactions.setAmount(amount);
        transactions.setModeOfPayment(mop);
        transactions.setDate(date);
        transactions.setTime(time);
        transactions.setDateInt(d);
        transactions.setMonthInt(m);
        transactions.setYearInt(y);



        long id =  transactions.getTransactionId();
        transactionDatabase.getTransactionDAO().updateTransaction(id,TenantName ,propertyName ,OwnerId ,
                holder , bank , amount , mop, date, time , d, m, y);
        transactionsList.set(position1,transactions);



    }

    public void createTransaction(long id, String name, String property , String holder , String bank , String amount ,
                             String modeOfPayment , String date , String time, int d , int m, int y){

        propertyDatabase = Room.databaseBuilder(getApplicationContext() , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        propertyList.addAll(propertyDatabase.getPropertyDAO().getProperty());
        String owners = null;
        for(Property p : propertyList){
            if(p.getPropertyName().matches(property)){
                owners = p.getPropertyOwnerName();
                break;
            }
        }
        long iid =  transactionDatabase.getTransactionDAO().addTransaction(new Transactions(date, time, id, name,
                property,owners, bank , amount, modeOfPayment , holder, d,m,y));



        Transactions transactions = transactionDatabase.getTransactionDAO().getTransaction(iid);
        transactionsList.add(transactions);
        transactionAdapter.notifyDataSetChanged();



//        new createOwnersAsynkTask().equals(new Owners(id,name,mobile,email,address,idProof));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==5){
            String name = intent.getStringExtra("name");
            String property = intent.getStringExtra("property");
//            String owner = intent.getStringExtra("owner");
            String holder = intent.getStringExtra("holder");
            String bank = intent.getStringExtra("bank");
            String amount = intent.getStringExtra("amount");
            String modeOfPayment = intent.getStringExtra("mop");
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");
            int dateP = intent.getIntExtra("dateP" , -1);
            int monthP = intent.getIntExtra("monthP" , -1);
            int yearP = intent.getIntExtra("yearP" , -1);



            if(name=="0"){
                Toast.makeText(transaction_activity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else{
                createTransaction(0 , name, property , holder , bank , amount , modeOfPayment , date , time, dateP, monthP, yearP);

            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(transaction_activity.this , MainActivity.class));
        CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");
        finish();
    }

    @Override
    public void onListTransaction(int position) {
        Intent intent = new Intent(transaction_activity.this , particularTransactionActivity.class);
        Transactions transactions  = transactionDatabase.getTransactionDAO().getTransaction(transactionsList.get(position).getTransactionId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("transaction" , transactions);
        intent.putExtra("transaction" , bundle);
        intent.putExtra("position" , position);
        startActivity(intent);
        CustomIntent.customType(transaction_activity.this, "fadein-to-fadeout");
    }
}