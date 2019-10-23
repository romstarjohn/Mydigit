package com.project.android.finanzm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesItem(Sales sale);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSalesItem(List<Sales> products);

    @Delete
    void delete(Sales sale);

    @Query("SELECT * from Sales where receipt_id= :receipt_id")
    Sales getSalesItemsByReceiptNumber(int receipt_id);

    @Query("SELECT * from SALES order by date desc")
    LiveData<List<Sales>> getAll();

    @Query("DELETE from SALES")
    int deleteAll();

    @Query("SELECT COUNT(*) from SALES")
    int getCount();

}
