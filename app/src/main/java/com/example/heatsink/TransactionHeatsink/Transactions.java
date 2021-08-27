package com.example.heatsink.TransactionHeatsink;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.heatsink.OwnersHeatsink.Owners;

import java.io.Serializable;


@Entity(tableName = "Transactions" )
public class Transactions implements Serializable {

    @ColumnInfo(name="transactionId")
    @PrimaryKey(autoGenerate =true)
    private long transactionId;


    @ColumnInfo(name="tenantName")
    private String tenantName;



    @ColumnInfo(name="tenantProperty")
    private String tenantProperty;

    @ColumnInfo(name="OwnerName")
    private String ownerName;

    @ColumnInfo(name="bankAccount")
    private String bankAccount;

    @ColumnInfo(name="Amount")
    private String Amount;

    @ColumnInfo(name="modeOfPayment")
    private String modeOfPayment;

    @ColumnInfo(name="AccountHolder")
    private String accountHolder;


    @ColumnInfo(name="transaction_date")
    private String date;

    @ColumnInfo(name="transaction_time")
    private String time;






    @ColumnInfo(name="dateInt")
    int dateInt;
    @ColumnInfo(name="monthInt")
    int monthInt;
    @ColumnInfo(name="yearInt")
    int yearInt;

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }


    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

    public int getMonthInt() {
        return monthInt;
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }

    public int getYearInt() {
        return yearInt;
    }

    public void setYearInt(int yearInt) {
        this.yearInt = yearInt;
    }

    public Transactions(String date, String time, long tenantId, String tenantName, String tenantProperty,
                        String ownerName , String bankAccount, String amount, String modeOfPayment , String accountHolder,
                        int d, int m, int y) {
        this.date = date;
        this.time = time;
        this.transactionId = tenantId;
        this.tenantName = tenantName;
        this.ownerName = ownerName;
        this.tenantProperty = tenantProperty;
        this.bankAccount = bankAccount;
        Amount = amount;
        this.modeOfPayment = modeOfPayment;
        this.accountHolder = accountHolder;
        dateInt = d;
        monthInt = m;
        yearInt = y;

    }

    public Transactions() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTransactionId(){
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantProperty() {
        return tenantProperty;
    }

    public void setTenantProperty(String tenantProperty) {
        this.tenantProperty = tenantProperty;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }
}