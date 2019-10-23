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
public interface CategorieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategorie(ProductCategories categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategorie(List<ProductCategories> categories);

    @Delete
    void deleteCategorie(ProductCategories categories);

    @Query("SELECT * from CATEGORIES where id= :id")
    ProductCategories getCategorieById(int id);

    @Query("SELECT * from CATEGORIES order by id desc")
    LiveData<List<ProductCategories>> getAll();

    @Query("DELETE from CATEGORIES")
    int deleteAll();

    @Query("SELECT COUNT(*) from CATEGORIES")
    int getCount();

}
