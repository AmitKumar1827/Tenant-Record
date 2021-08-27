package com.example.heatsink.BankHeatsink;


import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.heatsink.OwnersHeatsink.OwnersDAO;


@Database(entities = {Banks.class },exportSchema = true , version = 5)
public abstract class BanksDatabase extends RoomDatabase {

    public abstract BanksDAO getBanksDAO();




}

