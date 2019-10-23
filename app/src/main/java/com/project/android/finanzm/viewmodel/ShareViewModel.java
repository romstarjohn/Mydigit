package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.Taxe;

public class ShareViewModel extends AndroidViewModel {

    public final MutableLiveData<ProductCategories> selected = new MutableLiveData<>();
    private final MutableLiveData<Product> selectedProduct = new MutableLiveData<>();

    public ShareViewModel(@NonNull Application application) {
        super(application);
    }

    public void selectCategories(ProductCategories categories){
        selected.setValue(categories);
    }

    public LiveData<ProductCategories> getSelectedCategories(){
        return this.selected;
    }


    public void selectProduct(Product product) {
        selectedProduct.setValue(product);
    }

    public LiveData<Product> getSelectedProducts(){
        return selectedProduct;
    }
}
