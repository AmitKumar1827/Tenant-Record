package com.example.heatsink.PropertyHeatsink;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.OwnersHeatsink.OwnersDAO;


@Database(entities = {Property.class },exportSchema = true , version = 5)
public abstract class PropertyDatabase extends RoomDatabase {

    public abstract PropertyDAO getPropertyDAO();
}
