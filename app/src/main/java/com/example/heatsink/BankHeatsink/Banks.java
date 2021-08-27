package com.example.heatsink.BankHeatsink;




import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "banks")
public class Banks implements Serializable {

    @ColumnInfo(name="bankId")
    @PrimaryKey(autoGenerate =true)
    private long AccountId;

    @ColumnInfo(name="bankName")
    private String bankName;


    @ColumnInfo(name="bankAccountNumber")
    private String bankAccountNumber;

    @ColumnInfo(name="bankCodeIFSC")
    private String codeIFSC;

    @ColumnInfo(name="bankAccountHolder")
    private String accountHolder;





    public Banks(String bankName, String bankAccountNumber, String codeIFSC, String accountHolder, long accountId) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.codeIFSC = codeIFSC;
        this.accountHolder = accountHolder;
        AccountId = accountId;
    }

    public Banks() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCodeIFSC() {
        return codeIFSC;
    }

    public void setCodeIFSC(String codeIFSC) {
        this.codeIFSC = codeIFSC;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }
}