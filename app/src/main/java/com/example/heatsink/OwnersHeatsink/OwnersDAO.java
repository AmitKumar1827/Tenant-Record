package com.example.heatsink.OwnersHeatsink;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heatsink.OwnersHeatsink.Owners;

import java.util.List;

@Dao
public interface OwnersDAO {

    @Insert
    long addOwners(Owners owners);

    @Update
    int updateOwners(Owners owners);


    @Delete
    int deleteOwners(Owners owners);

    @Query("Select * from owners")
    List<Owners> getOwners();

    @Query("Select * from owners where owners_id = :iid")
    Owners getOwners(long iid);

    @Query("Update owners SET owners_name = :Name ," +
            " owners_mobile =:mobile , owners_email=:email " +
            ", owners_address =:address ,  owners_idProof =:idProof, owners_idName =:idName where owners_id =:iid" )
    int updateOwners(long iid, String Name , String mobile , String email, String address, String idProof, String idName);

    @Query("Select * from owners Order by owners_name ASC")
    List<Owners> assendingOwners();

    @Query("Select * from owners Order by owners_name Desc")
    List<Owners> descOwners();

    @Query("Select * from owners Order by owners_id desc")
    List<Owners> recentOwners();





}
