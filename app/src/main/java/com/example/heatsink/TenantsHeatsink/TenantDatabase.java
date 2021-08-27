package com.example.heatsink.TenantsHeatsink;

import androidx.room.Database;
import androidx.room.RoomDatabase;





@Database(entities = {Tenants.class } ,exportSchema = true ,version = 5 )
public abstract class TenantDatabase extends RoomDatabase {

    public abstract TenantDAO getTenantDAO();
}
