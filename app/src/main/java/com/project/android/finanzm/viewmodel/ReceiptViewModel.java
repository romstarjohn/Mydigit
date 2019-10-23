package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.Sales;
import com.project.android.finanzm.database.SalesReceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReceiptViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mProducts = new MutableLiveData<>();
    private AppRepository mRepository;
    private float amount;

    public ReceiptViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());

    }

    //Create a new receipt
    protected long generateReceipt(){
        return mRepository.generateReceipt(new SalesReceipt(getAmount(), new Date()));
    }








    float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount += amount;
    }

    public void emptyReceipt(){
        mProducts.setValue(null);
    }

    public void addArticles(Product p) {
        List<Product> products = new ArrayList<>();
        if(mProducts.getValue() == null){

        }else {
            products = mProducts.getValue();
        }
        //products = mProducts.getValue();
        products.add(p);
        setAmount(p.getPrice());

        mProducts.setValue(products);
    }

    public LiveData<List<Product>> getAllRingedArticles() {
        return mProducts;
    }

    public void voidSales() {
        List<Product> products = new ArrayList<>();
        mProducts.setValue(products);
        amount = (float) 0.0;

    }
}
