package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.Sales;
import com.project.android.finanzm.database.SalesReceipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SalesViewModel extends AndroidViewModel {
    private List<Product> mProducts;
    private AppRepository repository;
    MutableLiveData<Product> product = new MutableLiveData<>();
    Executor executor = Executors.newSingleThreadExecutor();
    private Product article;
    private float amount;

    public Product getArticle() {
        return article;
    }

    public void setArticle(Product article) {
        this.article = article;
    }

    public SalesViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
        mProducts = new ArrayList<>();
        repository.initProductList();
    }

    public int isCategoriesEmpty(ProductCategories categories) {

        int count = repository.getCategoriesCount();
        return count;
    }


    public void registerSalesItem(Product g) {
        mProducts.add(g);
        addAmount(g.getPrice());
    }

    public void voidReceipt() {
        mProducts.clear();
    }

    public boolean startPayment() {

        if(mProducts != null) {
            repository.addSales(productToSalesItem(mProducts, generateReceipt()));
            setAmount(0);
        }
        else
            return false;

        return true;
    }

    private long generateReceipt() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return repository.generateReceipt(new SalesReceipt(getAmount(), new Date(System.currentTimeMillis())));
    }

    private void setAmount(float value){
        this.amount = value;
    }

    private void addAmount(float value){
        this.amount += value;
    }
    public float getAmount() {
        return amount;
    }

    // Converter Methodes
    //Convert Product to Sales item
    public static List<Sales> productToSalesItem(List<Product> products, long receipt_id){
        Log.i("SalesViewModel  ", "Receipt id : " + receipt_id + " created succesfully");
        if (products == null)
            return null;
        List<Sales> sales = new ArrayList<>();
        for (Product product:products
        ) {
            sales.add(new Sales(product.getArticleCode(), new Date(), (int)receipt_id));
        }

        return sales;
    }

    public void suspendReceipt() {
        repository.addSuspendedReceipt(productToSalesItem(mProducts, generateReceipt()));
        setAmount(0);
    }


    public Product getProductByArticleCode(final int code) throws ExecutionException, InterruptedException {

        Callable<Product> callable = new Callable<Product>() {
            @Override
            public Product call() throws Exception {
                return repository.getProductByProductCode(code);
            }
        };

        Future<Product> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }
}
