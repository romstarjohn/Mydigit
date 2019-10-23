package com.project.android.finanzm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaxeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTaxeItem(Taxe sale);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTaxeItem(List<Taxe> taxes);

    @Delete
    void delete(Taxe sale);

    @Query("SELECT * from Taxe where taxe_type = :type")
    Taxe getTaxeItemsByReceiptNumber(String type);

    @Query("SELECT * from Taxe order by id desc")
    LiveData<List<Taxe>> getAll();

    @Query("DELETE from Taxe")
    int deleteAll();

    @Query("SELECT COUNT(*) from Taxe")
    int getCount();

}
