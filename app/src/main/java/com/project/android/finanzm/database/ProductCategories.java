package com.project.android.finanzm.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Bundle;

import com.project.android.finanzm.R;

@Entity(tableName = "categories", indices = {@Index(value = {"desc"},
        unique = true)})
public class ProductCategories {
    private static final int IMAGE_RESSOURCE = R.drawable.logo;
    private static final String DESCRIPTION = String.valueOf(R.string.app_name);


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String desc;
    private int imageRessource;



    @Ignore
    public ProductCategories() {
        }

    @Ignore
    public ProductCategories(String desc) {
            this.desc = desc;
        }

    public ProductCategories(int id, String desc, int imageRessource) {
            this.id = id;
            this.desc = desc;
            this.imageRessource = imageRessource;

        }

    @Ignore
    public ProductCategories(String desc, int imageRessource) {
            this.desc = desc;
            this.imageRessource = imageRessource;

        }

    public int getImageRessource() {
        return imageRessource;
    }

    public void setImageRessource(int imageRessource) {
        this.imageRessource = imageRessource;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Griditem{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", imageRessource=" + imageRessource +
                '}';
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putInt(String.valueOf(IMAGE_RESSOURCE), this.imageRessource);
        b.putString(DESCRIPTION, this.desc);
        return b;
    }

}
