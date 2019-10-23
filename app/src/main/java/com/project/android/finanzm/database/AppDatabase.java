package com.project.android.finanzm.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ProductCategories.class, Product.class, User.class, Taxe.class, Sales.class, SalesReceipt.class, AppConfig.class}, version = 1, exportSchema = false)
@TypeConverters({CatTypConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    public static volatile AppDatabase instance;
    //Make sure that two part of the application doesn't create two version of the database
    private static final Object LOCK = new Object();


    //Return instance of interface
    public abstract CategorieDao categorieDao();

    public abstract ProductDao productDao();

    public abstract UsersDao usersDao();

    public abstract TaxeDao taxesDao();

    public abstract SalesDao salesDao();

    public abstract ReceiptDao receiptDao();

    public abstract AppConfigDao appConfigDao();

    public abstract SuspendAndRetrievedDao suspendAndRetrievedDao();


    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return instance;
    }


}
