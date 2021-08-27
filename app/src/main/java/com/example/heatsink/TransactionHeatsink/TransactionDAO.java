package com.example.heatsink.TransactionHeatsink;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heatsink.TenantsHeatsink.Tenants;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Insert
    long addTransaction(Transactions transaction);

    @Update
    int updateTransaction(Transactions transaction);


    @Delete
    int deleteTransaction(Transactions transaction);

    @Query("Select * from Transactions ")
    List<Transactions> getTransaction();

    @Query("Select * from Transactions where transactionId = :iid")
    Transactions getTransaction(long iid);

    @Query("Update transactions SET tenantName = :TenantName ," +
            "   tenantProperty=:propertyName , OwnerName =:OwnerId , AccountHolder =:holder , " +
            "bankAccount =:bank, Amount =:amount, modeOfPayment =:mop, " +
            "transaction_date =:date, transaction_time =:time, dateInt =:d , monthInt=:m , yearInt=:y  where transactionId =:iid" )
    int updateTransaction(long iid, String TenantName ,String propertyName ,String OwnerId ,
                     String holder ,String bank ,String amount ,String mop,String date,String time , int d , int m, int y);

    @Query("Select * from transactions Order by tenantName ASC")
    List<Transactions> assendingTransaction();

    @Query("Select * from transactions Order by tenantName Desc")
    List<Transactions> descTransaction();

    @Query("Select * from transactions Order by transactionId desc")
    List<Transactions> recenttransaction();

    @Query("Select * from transactions Order by Amount desc")
    List<Transactions> descamount();

    @Query("Select * from transactions Order by Amount asc")
    List<Transactions> ascamount();










}
