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
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<User> users);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * from USERS where userId= :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * from USERS order by id desc")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from USERS")
    int deleteAll();

    @Query("SELECT COUNT(*) from USERS")
    int getCount();

}
