package com.project.android.finanzm.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"userId"},
        unique = true)})
public class User {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstname;
    private String name;
    @ColumnInfo(name = "userId")
    private int userId;
    private int pin;

    @Ignore
    public User() {
    }

    public User(int id, String firstname, String name, int userId, int pin) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.userId = userId;
        this.pin = pin;
    }

    @Ignore
    public User(String firstname, String name, int userId, int pin) {
        this.firstname = firstname;
        this.name = name;
        this.userId = userId;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}