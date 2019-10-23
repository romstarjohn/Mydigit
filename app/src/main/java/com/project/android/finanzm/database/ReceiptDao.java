package com.project.android.finanzm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSalesReceipt(SalesReceipt sale);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSalesReceipt(List<SalesReceipt> products);

    @Delete
    void delete(SalesReceipt salesReceipt);

    @Query("SELECT * from SalesReceipt where id= :receipt_id")
    SalesReceipt getSalesReceiptByReceiptNumber(int receipt_id);

    @Query("SELECT * from SalesReceipt order by date desc")
    LiveData<List<SalesReceipt>> getAll();

    @Query("DELETE from SalesReceipt")
    int deleteAll();

    @Query("SELECT COUNT(*) from SalesReceipt")
    int getCount();

}
