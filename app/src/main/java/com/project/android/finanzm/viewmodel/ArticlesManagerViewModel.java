package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.SalesReceipt;
import com.project.android.finanzm.database.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticlesManagerViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mProducts = new MutableLiveData<>();
    private AppRepository mRepository;
    private float amount;

    public ArticlesManagerViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());

    }


    public LiveData<List<Product>> getAllProducts() {
        return mRepository.getAllProducts();
    }

    public LiveData<List<ProductCategories>> getAllCategories(){
        return mRepository.getAllCategories();
    }

    public void updateArticlesTables(Product p) {
        mRepository.insertNewArticles(p);
    }

    public void updateUsersTables(User p) {
        mRepository.insertNewUsers(p);
    }

    public void changeIsFirstStartConfig(String is_first_start, int value) {
        mRepository.updateAppConfig(is_first_start, value);
    }

    public void initData() {
        mRepository.addSampleProducts();
        mRepository.addSampleUser();
        mRepository.addSamplesCategories();
    }
}
