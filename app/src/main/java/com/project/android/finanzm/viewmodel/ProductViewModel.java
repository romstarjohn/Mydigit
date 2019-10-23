package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.project.android.finanzm.R;
import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.Type;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private LiveData<List<Product>> allProducts;
    List<Product> categories;
    private AppRepository mRepository;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
    }


    public LiveData<List<Product>> getAllProductsForCategories(int id){
        if(id == 0){
            return mRepository.getAllProducts();
        }
        return  mRepository.getProductByCategories(id);
    }

    /*public List<ProductCategories> getCategories() {

        categories.clear();
        categories.add(new ProductCategories("Food", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        categories.add(new ProductCategories("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        return categories;
    }*/

}
