package com.project.android.finanzm.database;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Date toDate(long date){
        return new Date((long) date);
    }

    @TypeConverter
    public static long getDate(Date date){


        return 5;
    }
}
