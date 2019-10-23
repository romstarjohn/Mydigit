package com.project.android.finanzm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AppConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategorie(AppConfig appConfig);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategorie(List<AppConfig> appConfigs);

    @Delete
    void deleteCategorie(AppConfig appConfig);

    @Query("SELECT * from Appconfig where description= :desc")
    AppConfig getAppConfigByDescription(String desc);

    @Query("SELECT * from Appconfig order by id desc")
    List<AppConfig> getAll();

    @Query("DELETE from Appconfig")
    int deleteAll();

    @Query("SELECT COUNT(*) from CATEGORIES")
    int getCount();

}
