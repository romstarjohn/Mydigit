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
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllArticles(List<Product> products);

    @Delete
    void deleteArticle(Product griditem);

    @Query("SELECT * from ARTICLES where product_code = :code")
    Product getArticleByCode(int code);

    @Query("SELECT * from ARTICLES order by id desc")
    LiveData<List<Product>> getAllProducts();

    @Query("DELETE from ARTICLES")
    int deleteAll();

    @Query("SELECT COUNT(*) from ARTICLES")
    int getCount();

    @Query("SELECT * FROM ARTICLES WHERE categories_id = :id ")
    LiveData<List<Product>> getArticleByCategories(int id);
}
