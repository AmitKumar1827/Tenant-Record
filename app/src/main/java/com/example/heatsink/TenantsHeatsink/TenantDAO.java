package com.example.heatsink.TenantsHeatsink;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heatsink.BankHeatsink.Banks;
import com.example.heatsink.PropertyHeatsink.Property;

import java.util.List;

@Dao
public interface TenantDAO {

    @Insert
    long addTenant(Tenants tenants);

    @Update
    int updateTenant(Tenants tenants);


    @Delete
    int deleteTenant(Tenants tenants);

    @Query("Select * from tenants")
    List<Tenants> getTenant();

    @Query("Select * from tenants where tenantId = :iid")
    Tenants getTenant(long iid);

    @Query("Update tenants SET tenantName = :name ," +
            "   tenantMobile=:mobile , tenantEmail =:email , tenantPropertyId =:idproperty , tenantIdProof =:idproof" +
            ", idProofName=:idProofName, tenantRent=:rent  where tenantId =:iid" )
    int updateTenant(long iid, String name ,String mobile , String email , String idproperty , String idproof ,String idProofName,String rent);

    @Query("Select * from tenants Order by tenantName ASC")
    List<Tenants> assendingTenant();

    @Query("Select * from tenants Order by tenantName Desc")
    List<Tenants> descTenant();

    @Query("Select * from tenants Order by tenantId desc")
    List<Tenants> recentTenants();




}
