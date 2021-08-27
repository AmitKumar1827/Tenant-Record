package com.example.heatsink.TenantsHeatsink;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "tenants")
public class Tenants implements Serializable {

    @ColumnInfo(name="tenantId")
    @PrimaryKey(autoGenerate =true)
    private long tenantId;

    @ColumnInfo(name="tenantName")
    private String tenantName;


    @ColumnInfo(name="tenantMobile")
    private String tenantMobile;

    @ColumnInfo(name="tenantEmail")
    private String tenantEmail;


    @ColumnInfo(name="tenantRent")
    private String tenantRent;


    @ColumnInfo(name="tenantPropertyId")
    private String tenantPropertyId;




    @ColumnInfo(name="idProofName")
    private String idProofName;

    @ColumnInfo(name="tenantIdProof")
    private String tenantIdProof;



    public String getTenantRent() {
        return tenantRent;
    }

    public void setTenantRent(String tenantRent) {
        this.tenantRent = tenantRent;
    }

    public Tenants(String tenantName, String tenantPropertyId, long tenantId, String tenantMobile, String tenantEmail, String tenantIdProof, String idProofName, String rent) {
        this.tenantName = tenantName;
        this.tenantPropertyId = tenantPropertyId;
        this.tenantId = tenantId;
        this.tenantMobile = tenantMobile;
        this.tenantEmail = tenantEmail;
        this.tenantIdProof = tenantIdProof;
        this.idProofName = idProofName;
        tenantRent = rent;
    }

    public Tenants() {
    }

    public String getIdProofName() {
        return idProofName;
    }

    public void setIdProofName(String idProofName) {
        this.idProofName = idProofName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantPropertyId() {
        return tenantPropertyId;
    }

    public void setTenantPropertyId(String tenantPropertyId) {
        this.tenantPropertyId = tenantPropertyId;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantMobile() {
        return tenantMobile;
    }

    public void setTenantMobile(String tenantMobile) {
        this.tenantMobile = tenantMobile;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public String getTenantIdProof() {
        return tenantIdProof;
    }

    public void setTenantIdProof(String tenantIdProof) {
        this.tenantIdProof = tenantIdProof;
    }
}