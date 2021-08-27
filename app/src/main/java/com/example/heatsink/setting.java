package com.example.heatsink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteAbortException;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.BankHeatsink.BanksDatabase;
import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDatabase;
import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.PropertyDatabase;
import com.example.heatsink.TenantsHeatsink.TenantDatabase;
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TransactionHeatsink.TransactionDatabase;
import com.example.heatsink.TransactionHeatsink.Transactions;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;


public class setting extends AppCompatActivity {
Toolbar toolbar;
    OwnersDatabase ownersDatabase;
    BanksDatabase banksDatabase;
    TenantDatabase tenantDatabase;
    TransactionDatabase transactionDatabase;
    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/Export";


    LinearLayout export, backup,restore, aboutUs , privacyPolicy , tc, helpandsupport;
    PropertyDatabase propertyDatabase;
    public static final int STORAGE_REQUEST_CODE_BACKUP = 1;
    public static final int STORAGE_REQUEST_CODE_RESTORE = 2;
    public static final int STORAGE_REQUEST_CODE_export= 3;
    private String[] storagePermission;
    TextView name, email;
    Button editName;

    boolean boys = false;

    ArrayList<Owners> list = new ArrayList<>();
    boolean isOwner = false;
    int[] count = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.toolbar);
        final ImageView setting = toolbar.findViewById(R.id.setting);
        ImageView home = toolbar.findViewById(R.id.homesetting);
        setting.setAlpha(0);
        export = findViewById(R.id.export);
        backup = findViewById(R.id.backup);
        restore = findViewById(R.id.restore);
        aboutUs = findViewById(R.id.AboutUs);
        helpandsupport = findViewById(R.id.helpAndSupport);
        editName = findViewById(R.id.editName);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        name = findViewById(R.id.namesetting);
        email = findViewById(R.id.emailsetting);


        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.heatsink.setting.this , editdetail.class).addCategory("editName"));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();
            }
        });

        SharedPreferences settings = getSharedPreferences("user", MODE_PRIVATE);
        String name1 = settings.getString("name", "UserName");
        String email1 = settings.getString("email" , "1234567890");

        name.setText(name1);
        email.setText(email1);


        privacyPolicy = findViewById(R.id.privacyPolicy);
        tc= findViewById(R.id.TermsAndCondition);


        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setting.this , privacyPolicy.class).addCategory("tc"));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setting.this , privacyPolicy.class).addCategory("pp"));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();

            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setting.this, aboutUsactivity.class));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();
            }
        });

        helpandsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setting.this, helpandsupport1.class));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();
            }
        });







        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};







        TextView title = toolbar.findViewById(R.id.Title);
        title.setText("Setting");









        ownersDatabase = Room.databaseBuilder(com.example.heatsink.setting.this,OwnersDatabase.class , "owners").allowMainThreadQueries().build();

        transactionDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TransactionDatabase.class , "Transactions").allowMainThreadQueries().build();
        propertyDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        tenantDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        banksDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , BanksDatabase.class , "banks").allowMainThreadQueries().build();

        list.addAll(ownersDatabase.getOwnersDAO().getOwners());
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {

                    if (ActivityCompat.checkSelfPermission(com.example.heatsink.setting.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(com.example.heatsink.setting.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // this will request for permission from the user if not yet granted
                        ActivityCompat.requestPermissions(com.example.heatsink.setting.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        boolean value = TableExport();
                        if(value){
                            Toast.makeText(setting.this, "Tables Exported", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }


        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(setting.this, backupactivity.class));
                CustomIntent.customType(setting.this, "fadein-to-fadeout");
                finish();

            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkStoragePermission()){
                    boys = true;
                    importCVV();
                }else{
                    requestStoragePermitRestore();
                }
            }
        });





        if(getIntent().hasCategory("backup")){
            if(checkStoragePermission()){
                ExportCVV();
            }else{
                requestStoragePermitBackup();
            }

        }







    }
    public void check(long id){
        isOwner = false;
        for(Owners i : list){
            if(i.getId() == id){
                isOwner= true;
            }
        }

    }

    private void importCVV() {
        ownersDatabase = Room.databaseBuilder(com.example.heatsink.setting.this,OwnersDatabase.class , "owners").allowMainThreadQueries().build();

        transactionDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TransactionDatabase.class , "Transactions").allowMainThreadQueries().build();
        propertyDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        tenantDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        banksDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , BanksDatabase.class , "banks").allowMainThreadQueries().build();

        String fileImportPathOwners = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup/ownersBackup.csv";
        File csvFileOwner = new File(fileImportPathOwners);

        int yes=0 , no=0;
        if(csvFileOwner.exists()){
            try {
                CSVReader csvOwner = new CSVReader(new FileReader(csvFileOwner.getAbsolutePath()));
                String[] newline;
                while( (newline = csvOwner.readNext() )!= null){
                    String id = newline[0];
                    String name = newline[1];
                    String mobile = newline[2];
                    String email = newline[3];
                    String address = newline[4];
                    String idName = newline[5];
                    String idProod = newline[6];

                    long iid = Long.parseLong(id);

                    check(iid);
                    if(isOwner){}
                       else {
                        long iiid = ownersDatabase.getOwnersDAO().addOwners(new Owners(iid, name, mobile, email, address, idProod, idName));
                        ownersDatabase.close();
                        System.out.println(iiid);
                    }


                }
             yes++;


            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            no++;
        }
        String fileImportPathBank = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup/banksBackup.csv";
        File csvFileBank = new File(fileImportPathBank);
        if(csvFileBank.exists()){
            try {
                CSVReader csvBank = new CSVReader(new FileReader(csvFileBank.getAbsolutePath()));
                String[] newline;
                while( (newline = csvBank.readNext() )!= null){
                    String id = newline[0];
                    String name = newline[1];
                    String accountNo = newline[2];
                    String ifsc = newline[3];
                    String holder = newline[4];


                    long iid = Long.parseLong(id);


                    long iiid =  banksDatabase.getBanksDAO().addBanks(new Banks(name,accountNo,ifsc, holder, iid));
                    banksDatabase.close();


                }
                yes++;


            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            no++;
        }

        String fileImportPathProperty = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup/propertyBackup.csv";
        File csvFileProperty = new File(fileImportPathProperty);
        if(csvFileProperty.exists()){
            try {
                CSVReader csvProperty = new CSVReader(new FileReader(csvFileProperty.getAbsolutePath()));
                String[] newline;
                while( (newline = csvProperty.readNext() )!= null){
                    String id = newline[0];
                    String name = newline[1];
                    String address = newline[2];
                    String owners = newline[3];

                    long iid = Long.parseLong(id);


                    long iiid =  propertyDatabase.getPropertyDAO().addProperty(new Property(name,owners,address,iid));
                    propertyDatabase.close();
                }
                yes++;

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            no++;
        }


        String fileImportPathTenant = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup/tenantsBackup.csv";
        File csvFileTenant = new File(fileImportPathTenant);
        if(csvFileTenant.exists()){
            try {
                CSVReader csvTenant = new CSVReader(new FileReader(csvFileTenant.getAbsolutePath()));
                String[] newline;
                while( (newline = csvTenant.readNext() )!= null){
                    String id = newline[0];
                    String name = newline[1];
                    String mobile = newline[2];
                    String email = newline[3];
                    String propertyId = newline[4];
                    String idName = newline[5];
                    String idProod = newline[6];
                    String rent = newline[7];

                    long iid = Long.parseLong(id);


                    long iiid =  tenantDatabase.getTenantDAO().addTenant(new Tenants(name,propertyId,iid,mobile,email,idProod,idName,rent));
tenantDatabase.close();

                }
                yes++;


            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            no++;
        }

        String fileImportPathTransaction = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup/transactionsBackup.csv";
        File csvFileTransaction = new File(fileImportPathTransaction);
        if(csvFileTransaction.exists()){
            try {
                CSVReader csvTransaction = new CSVReader(new FileReader(csvFileTransaction.getAbsolutePath()));
                String[] newline;
                while( (newline = csvTransaction.readNext() )!= null){
                    String id = newline[0];
                    String date = newline[1];
                    String time = newline[2];
                    String nameT = newline[3];
                    String nameO = newline[4];
                    String propertyTenant = newline[5];
                    String bankAccount = newline[6];
                    String holder = newline[7];
                    String mop = newline[8];
                    String amount = newline[9];
                    String date1 = newline[10];
                    String month1 = newline[11];
                    String year1 = newline[12];

                    int date11 = Integer.parseInt(date1);
                    int month11 = Integer.parseInt(month1);
                    int year11 = Integer.parseInt(year1);
                    long iid = Long.parseLong(id);


                    long iiid =  transactionDatabase.getTransactionDAO().addTransaction(new Transactions(date,time, iid,nameT,
                                                                        propertyTenant, nameO ,bankAccount,amount,mop ,holder,date11,month11,year11));
                    transactionDatabase.close();

                }
                yes++;



            }catch (Exception e){

                e.printStackTrace();
            }


        }else{
            no++;

        }
        if(yes>0){
            Toast.makeText(this, "Backup Restored", Toast.LENGTH_SHORT).show();
        }else if(no>0){
            Toast.makeText(this, "Backup not found", Toast.LENGTH_SHORT).show();
        }

    }

    private void ExportCVV() {
        ownersDatabase = Room.databaseBuilder(com.example.heatsink.setting.this,OwnersDatabase.class , "owners").allowMainThreadQueries().build();

        transactionDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TransactionDatabase.class , "Transactions").allowMainThreadQueries().build();
        propertyDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , PropertyDatabase.class , "property").allowMainThreadQueries().build();
        tenantDatabase = Room.databaseBuilder(com.example.heatsink.setting.this, TenantDatabase.class , "tenants").allowMainThreadQueries().build();
        banksDatabase = Room.databaseBuilder(com.example.heatsink.setting.this , BanksDatabase.class , "banks").allowMainThreadQueries().build();


        int count = 0;

        String directory_path1 = Environment.getExternalStorageDirectory().getPath() + "/HeatSink/backup";
        File file1 = new File(directory_path1 );
        boolean isFolderCreated = false;
        if (!file1.exists()) {
             file1.mkdirs();
        }

        String backUpCSV = "ownersBackup.csv";

        String filepathNameOwner = file1.toString() + "/" + backUpCSV;


        ArrayList<Owners> ownersArrayList = new ArrayList<>();
        ownersArrayList.clear();
        ownersArrayList.addAll(ownersDatabase.getOwnersDAO().getOwners());
        ownersDatabase.close();

        try{
            FileWriter fw = new FileWriter(filepathNameOwner);

            for(int i=0; i<ownersArrayList.size() ; i++){
                fw.append("" + ownersArrayList.get(i).getId());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getName());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getMobile());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getEmail());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getAddress());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getIdName());
                fw.append(",");
                fw.append("" + ownersArrayList.get(i).getIdProof());
                fw.append("\n");

            }
            count++;
            fw.flush();
            fw.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        String backUpCSVProperty = "propertyBackup.csv";
        String filepathNamePrperty = file1.toString() + "/" + backUpCSVProperty;

        ArrayList<Property> propertyArrayList = new ArrayList<>();
        propertyArrayList.clear();
        propertyArrayList.addAll(propertyDatabase.getPropertyDAO().getProperty());
        propertyDatabase.close();
        try{
            FileWriter fw = new FileWriter(filepathNamePrperty);
            for(int i=0; i<propertyArrayList.size() ; i++){
                fw.append("" + propertyArrayList.get(i).getPropertyId());
                fw.append(",");
                fw.append("" + propertyArrayList.get(i).getPropertyName());
                fw.append(",");
                fw.append("" + propertyArrayList.get(i).getPropertyAddress());
                fw.append(",");
                fw.append("" + propertyArrayList.get(i).getPropertyOwnerName());
                fw.append("\n");



            }
            count++;

            fw.flush();
            fw.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        String backUpCSVBank = "banksBackup.csv";
        String filepathNameBank = file1.toString() + "/" + backUpCSVBank;

        ArrayList<Banks> banksArrayList = new ArrayList<>();
        banksArrayList.clear();
        banksArrayList.addAll(banksDatabase.getBanksDAO().getBanks());
        banksDatabase.close();
        try{
            FileWriter fw = new FileWriter(filepathNameBank);
            for(int i=0; i<banksArrayList.size() ; i++){
                fw.append("" + banksArrayList.get(i).getAccountId());
                fw.append(",");
                fw.append("" + banksArrayList.get(i).getBankName());
                fw.append(",");
                fw.append("" + banksArrayList.get(i).getBankAccountNumber());
                fw.append(",");
                fw.append("" + banksArrayList.get(i).getCodeIFSC());
                fw.append(",");
                fw.append("" + banksArrayList.get(i).getAccountHolder());
                fw.append("\n");
            }
            count++;

            fw.flush();
            fw.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        String backUpCSVTenant = "tenantsBackup.csv";
        String filepathNameTenants = file1.toString() + "/" + backUpCSVTenant;

        ArrayList<Tenants> tenantsArrayList = new ArrayList<>();
        tenantsArrayList.clear();
        tenantsArrayList.addAll(tenantDatabase.getTenantDAO().getTenant());
tenantDatabase.close();
        try{
            FileWriter fw = new FileWriter(filepathNameTenants);
            for(int i=0; i<tenantsArrayList.size() ; i++){
                fw.append("" + tenantsArrayList.get(i).getTenantId());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantName());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantMobile());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantEmail());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantPropertyId());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getIdProofName());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantIdProof());
                fw.append(",");
                fw.append("" + tenantsArrayList.get(i).getTenantRent());
                fw.append("\n");
            }
            count++;

            fw.flush();
            fw.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        String backUpCSVTransaction = "transactionsBackup.csv";
        String filepathNameTransaction = file1.toString() + "/" + backUpCSVTransaction;

        ArrayList<Transactions> transactionsArrayList = new ArrayList<>();
        transactionsArrayList.clear();
        transactionsArrayList.addAll(transactionDatabase.getTransactionDAO().getTransaction());

        try{
            FileWriter fw = new FileWriter(filepathNameTransaction);
            for(int i=0; i<transactionsArrayList.size() ; i++){
                fw.append("" + transactionsArrayList.get(i).getTransactionId());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getDate());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getTime());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getTenantName());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getOwnerName());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getTenantProperty());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getBankAccount());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getAccountHolder());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getModeOfPayment());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getAmount());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getDateInt());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getMonthInt());
                fw.append(",");
                fw.append("" + transactionsArrayList.get(i).getYearInt());
                fw.append("\n");
            }
            count++;
            fw.flush();
            fw.close();

            if(count ==5 ){
                Toast.makeText(this, "Backup Created", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            e.printStackTrace();

        }


    }


    public boolean TableExport(){

        final File file = new File(directory_path);   //check if there any file in this directory path
        if (!file.exists()) {
            file.mkdirs();
        }


        final int[] count1 = {0};
        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "owners", directory_path);
        sqliteToExcel.exportAllTables("OwnersTable.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
//                Snackbar snackBar = Snackbar .make(view, "Sucessfully Exported", Snackbar.LENGTH_LONG);
//                snackBar.setBackgroundTint(Color.WHITE);
//                snackBar.setTextColor(Color.BLACK);
//                snackBar.show();
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "banks", directory_path);
        sqliteToExcel.exportAllTables("Bank Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "property", directory_path);
        sqliteToExcel.exportAllTables("Property Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "Transactions", directory_path);
        sqliteToExcel.exportAllTables("Transaction Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });


        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "tenants", directory_path);
        sqliteToExcel.exportAllTables("Tenant Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });


//        Toast.makeText(setting.this, "Successfully Exported", Toast.LENGTH_SHORT).show();

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "owners", directory_path);
        sqliteToExcel.exportAllTables("OwnersTable.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
//                Snackbar snackBar = Snackbar .make(view, "Sucessfully Exported", Snackbar.LENGTH_LONG);
//                snackBar.setBackgroundTint(Color.WHITE);
//                snackBar.setTextColor(Color.BLACK);
//                snackBar.show();
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "banks", directory_path);
        sqliteToExcel.exportAllTables("Bank Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "property", directory_path);
        sqliteToExcel.exportAllTables("Property Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "Transactions", directory_path);
        sqliteToExcel.exportAllTables("Transaction Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });


        sqliteToExcel = new SQLiteToExcel(com.example.heatsink.setting.this, "tenants", directory_path);
        sqliteToExcel.exportAllTables("Tenant Table.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                count[0]++;
            }

            @Override
            public void onError(Exception e) {
                count1[0]++;

            }
        });

        ownersDatabase.close();
        propertyDatabase.close();
        tenantDatabase.close();
        transactionDatabase.close();
        banksDatabase.close();


        return  true;

    }




    private boolean checkStoragePermission(){
        Boolean result = ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    void requestStoragePermitBackup(){
        ActivityCompat.requestPermissions(this, storagePermission , STORAGE_REQUEST_CODE_BACKUP);
    }
    void requestStoragePermitRestore(){
        ActivityCompat.requestPermissions(this, storagePermission , STORAGE_REQUEST_CODE_RESTORE);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case STORAGE_REQUEST_CODE_BACKUP: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    boys = true;
//                    ExportCVV();
                }else{
                    Toast.makeText(this, "Storage permission required please enable it in settings", Toast.LENGTH_SHORT).show();
                }

            } break;

            case STORAGE_REQUEST_CODE_RESTORE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    importCVV();

                }else{
                    Toast.makeText(this, "Storage permission required please enable it in settings", Toast.LENGTH_SHORT).show();
                }

            } break;



        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(setting.this, MainActivity.class));
        CustomIntent.customType(setting.this, "fadein-to-fadeout");
        finish();
    }
}