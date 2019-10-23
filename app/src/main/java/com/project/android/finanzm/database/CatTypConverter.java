package com.project.android.finanzm.database;


import android.arch.persistence.room.TypeConverter;

public class CatTypConverter {

    @TypeConverter
    public static Type toType(int i){
        return Type.valueOf(i);
    }
    @TypeConverter
    public static int getType(Type type){
        return type.getValue();
    }
}
