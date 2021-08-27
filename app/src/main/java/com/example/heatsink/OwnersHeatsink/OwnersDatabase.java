package com.example.heatsink.OwnersHeatsink;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.OwnersDAO;


@Database(entities = {Owners.class } ,exportSchema = true, version = 5)
public abstract class OwnersDatabase extends RoomDatabase {

    public abstract OwnersDAO getOwnersDAO();
    public static final String NAME = "OwnersDatabase";
}
