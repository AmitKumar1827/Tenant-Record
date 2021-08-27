package com.example.heatsink.PropertyHeatsink;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.OwnersHeatsink.Owners;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Insert
    long addProperty(Property property);

    @Update
    int updateProperty(Property property);


    @Delete
    int deleteProperty(Property property);

    @Query("Select * from property")
    List<Property> getProperty();

    @Query("Select * from property where propertyId = :iid")
    Property getProperty(long iid);

    @Query("Update property SET PropertyOwnerId = :owner , PropertyName=:name ," +
            "  propertyAddress =:address   where propertyId =:iid" )
    int updateProperty(long iid, String name ,String address,String owner);

    @Query("Select * from property Order by propertyName ASC")
    List<Property> assendingProperty();

    @Query("Select * from property Order by propertyName Desc")
    List<Property> descProperty();

    @Query("Select * from property Order by propertyId desc")
    List<Property> recentProperty();






}
