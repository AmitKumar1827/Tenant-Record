package com.example.heatsink.PropertyHeatsink;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "property")
public class Property implements Serializable {

    @ColumnInfo(name="propertyId")
    @PrimaryKey(autoGenerate =true)
    private long PropertyId;

    @ColumnInfo(name="propertyName")
    private String PropertyName;

    @ColumnInfo(name="PropertyOwnerId")
    private String propertyOwnerName;

    @ColumnInfo(name="propertyAddress")
    private String PropertyAddress;







    public Property(String PropertyName , String Owner ,String propertyAddress, long propertyId) {
        PropertyAddress = propertyAddress;
        this.PropertyName = PropertyName;
        this.propertyOwnerName = Owner;
        PropertyId = propertyId;
    }

    public Property() {
    }

    public String getPropertyAddress() {
        return PropertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        PropertyAddress = propertyAddress;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getPropertyOwnerName() {
        return propertyOwnerName;
    }

    public void setPropertyOwnerName(String propertyOwnerName) {
        this.propertyOwnerName = propertyOwnerName;
    }

    public long getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(long propertyId) {
        PropertyId = propertyId;
    }
}