package com.project.android.finanzm.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Bundle;

import com.project.android.finanzm.R;

@Entity(tableName = "articles", indices = {@Index(value = {"product_code"},
        unique = true)})
public class Product {
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "Price";
    public static final String ARTICLECODE = "Article Code";
    public static final int QUANTITY = 1;
    public static final String IMAGE_RESSOURCE = "" + R.drawable.logo;
    private static final String CATEGORIE_ID = "1";


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name ="product_code")
    protected int articleCode;
    protected String description;
    protected float price;
    protected int imageRessources;
    protected int categories_id;


    @Ignore
    public Product() {
    }

    @Ignore
    public Product(int articleCode, String description, float price, int imageRessources, int categories_id) {
        this.articleCode = articleCode;
        this.description = description;
        this.price = price;
        this.imageRessources = imageRessources;
        this.categories_id = categories_id;
    }

    public Product(int id, int articleCode, String description, float price, int imageRessources, int categories_id) {
        this.id = id;
        this.articleCode = articleCode;
        this.description = description;
        this.price = price;
        this.imageRessources = imageRessources;
        this.categories_id = categories_id;
    }



    @Ignore
    public Product(Bundle b) {
        if(null != b) {
            this.description = b.getString(DESCRIPTION);
            this.articleCode = b.getInt(ARTICLECODE);
            this.price = b.getFloat(PRICE);
            this.imageRessources = b.getInt(IMAGE_RESSOURCE);
            this.categories_id = b.getInt(CATEGORIE_ID);
        }
    }

    public Bundle toBundle(int quantity){
        Bundle b = new Bundle();
        b.putString(DESCRIPTION, this.description);
        b.putFloat(PRICE, this.price);
        b.putInt(ARTICLECODE, this.articleCode);
        b.putInt(IMAGE_RESSOURCE, R.drawable.logo);
        b.putInt(CATEGORIE_ID, this.categories_id);
        return b;
    }

    public void setArticleCode(int articleCode) {
        this.articleCode = articleCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getArticleCode() {
        return articleCode;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageRessources() {
        return imageRessources;
    }

    public void setImageRessources(int imageRessources) {
        this.imageRessources = imageRessources;
    }
    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

}
