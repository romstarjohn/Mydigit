package com.project.android.finanzm.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Sales")
public class Sales {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int code;
    private Date date;
    private int receipt_id;

    @Ignore
    public Sales(){

    }
    @Ignore
    public Sales(int code, Date date, int receipt_id) {
        this.id = id;
        this.code = code;
        this.receipt_id = receipt_id;
        boolean isValid;
    }

    public Sales(int id, int code, Date date, int receipt_id) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.receipt_id = receipt_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }
}
