package com.project.android.finanzm.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "taxe" , indices = {@Index(value = {"taxe_type"},
        unique = true)})
public class Taxe {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "taxe_type")
    private String type;

    private float value;

    @Ignore
    public Taxe(){

    }
    public Taxe(int id, String type, float value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    @Ignore
    public Taxe( String type, float value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
