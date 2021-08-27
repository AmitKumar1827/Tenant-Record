package com.example.heatsink.OwnersHeatsink;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "owners")
public class Owners implements Serializable {

    @ColumnInfo(name="owners_id")
    @PrimaryKey(autoGenerate =true)
    private long id;

    @ColumnInfo(name="owners_name")
    private String name;

    @ColumnInfo(name="owners_mobile")
    private String mobile;

    @ColumnInfo(name="owners_email")
    private String email;

    @ColumnInfo(name="owners_address")
    private String address;

    @ColumnInfo(name="owners_idName")
    private String idName;

    @ColumnInfo(name="owners_idProof")
    private String idProof;



    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }



    @Ignore
    public Owners() {
    }

    public Owners(long id, String name, String mobile, String email, String address, String idProof, String idName) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.idProof = idProof;
        this.idName = idName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}