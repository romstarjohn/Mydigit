package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.services.StoreInitService;
import com.project.android.finanzm.utility.Constants;
import com.project.android.finanzm.utility.NetworkUtil;
import com.project.android.finanzm.utility.StoreInitData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigurationViewModel extends AndroidViewModel {

    private StoreInitService storeInitService;

    private LiveData<List<Product>> allProducts;
    StoreInitData storeInitData;
    private AppRepository mRepository;
    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication().getApplicationContext());
        initAnonService();
        
    }


    public LiveData<List<Product>> getAllProductsForCategories(int id){
        if(id == 0){
            return mRepository.getAllProducts();
        }
        return  mRepository.getProductByCategories(id);
    }

    // Configure Retrofit Instance
    public void initAnonService(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        storeInitService = retrofit.create(StoreInitService.class);
    }


    public void startStoreInit(){

        storeInitService.getAllStoresData().enqueue(new Callback<StoreInitData>() {
            @Override
            public void onResponse(Call<StoreInitData> call, Response<StoreInitData> response) {
                if (!response.isSuccessful()){
                    showError(NetworkUtil.onServerResponseError(response));
                    return;
                }

                if(response.body() != null){
                    if(storeInitData == null)
                        storeInitData = new StoreInitData();

                    storeInitData = response.body();
                }
            }

            @Override
            public void onFailure(Call<StoreInitData> call, Throwable t) {
                showError(t.getLocalizedMessage());
            }
        });

    }

    private void showError(String message) {

    }
}
