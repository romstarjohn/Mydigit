package com.project.android.finanzm.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "salesReceipt", indices = {@Index(value = {"id"},
        unique = true)})
public class SalesReceipt {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private float amount;
    private Date date;

    @Ignore
    public SalesReceipt(){

    }
    @Ignore
    public SalesReceipt(float amount, Date date) {
        this.amount = amount;
        this.date = date;
    }
    public SalesReceipt(int id, float amount, Date date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
