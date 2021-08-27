package com.example.heatsink.BankHeatsink;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heatsink.OwnersHeatsink.Owners;

import java.util.List;

@Dao
public interface BanksDAO {

    @Insert
    long addBanks(Banks banks);

    @Update
    int updateBanks(Banks banks);


    @Delete
    int deleteBanks(Banks banks);

    @Query("Select * from banks")
    List<Banks> getBanks();

    @Query("Select * from banks where bankId = :iid")
    Banks getBanks(long iid);

    @Query("Update banks SET bankName = :bank ," +
            "  bankAccountNumber =:Account , bankCodeIFSC=:ifsc " +
            ", bankAccountHolder =:holder  where bankId =:iid" )
    int updateBanks(long iid, String bank ,String Account , String ifsc, String holder);

    @Query("Select * from banks Order by bankName ASC")
    List<Banks> assendingBank();

    @Query("Select * from banks Order by bankName Desc")
    List<Banks> descBank();

    @Query("Select * from banks Order by bankId desc")
    List<Banks> recentBank();








}
