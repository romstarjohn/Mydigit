package com.project.android.finanzm.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Appconfig", indices = {@Index(value = {"description"},
        unique = true)})
public class AppConfig {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int value;

    @Ignore
    public AppConfig(){

    }

    public AppConfig(int id, String description, int value) {
        this.id = id;
        this.description = description;
        this.value = value;
    }

    @Ignore
    public AppConfig(String description, int i) {
        this.description = description;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
