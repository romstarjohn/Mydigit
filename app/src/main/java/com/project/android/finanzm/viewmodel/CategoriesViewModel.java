package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.project.android.finanzm.R;
import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.Type;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private LiveData<List<ProductCategories>> allCategories;
    List<ProductCategories> categories;
    private AppRepository mRepository;
    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
    }

    public LiveData<List<ProductCategories>> getAllCategories(){

        return mRepository.getAllCategories();
    }

 /*   public List<ProductCategories> getCategories() {

        categories.clear();
        categories.add(new ProductCategories("Food", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        return categories;
    }

    public List<ProductCategories> getProducts() {

        categories.clear();
        categories.add(new ProductCategories("Product", R.drawable.ic_launcher_background, Type.PRODUCT));
        categories.add(new ProductCategories("Product 1", R.drawable.ic_launcher_background, Type.PRODUCT));
        categories.add(new ProductCategories("Product 2", R.drawable.ic_launcher_background, Type.PRODUCT));
        categories.add(new ProductCategories("Product 3", R.drawable.ic_launcher_background, Type.PRODUCT));
        return categories;

    }*/
}
