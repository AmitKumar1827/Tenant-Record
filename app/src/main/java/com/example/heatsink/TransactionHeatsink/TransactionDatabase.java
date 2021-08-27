package com.example.heatsink.TransactionHeatsink;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Transactions.class } ,exportSchema = true, version = 5)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDAO getTransactionDAO();
}
