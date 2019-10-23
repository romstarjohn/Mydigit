package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.User;
import com.project.android.finanzm.services.StoreTransferServices;
import com.project.android.finanzm.utility.Constants;
import com.project.android.finanzm.utility.NetworkUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> error;
    private boolean transferSuccess;
    List<Product> mProducts;
    private AppRepository mRepository;
    private StoreTransferServices storeTransferServices;


    public MainViewModel(@NonNull Application application) {
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

        storeTransferServices = retrofit.create(StoreTransferServices.class);
    }


    public void startStoreInit(){

        storeTransferServices.getProductList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()){
                    showError(NetworkUtil.onServerResponseError(response));
                    return;
                }

                if(response.body() != null){
                    if(mProducts == null)
                        mProducts = new ArrayList<>();

                    mProducts.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                showError(t.getLocalizedMessage());
            }
        });


    }

    private void postTillUsers(final List<User> mUsers){
        if(mUsers == null){
            return;
        }

        storeTransferServices.postTillUsers(mUsers).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, @NotNull Response<List<User>> response) {

                if(!response.isSuccessful()){
                    showError(NetworkUtil.onServerResponseError(response));
                }

                List<User> receivedlist = response.body();
                if (receivedlist.equals(mUsers)){
                    transferSuccess = true;
                }else {
                    showError("Transfert failled");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                showError(t.getLocalizedMessage());
            }
        });

    }

    private void showError(String message) {
        Log.i("Clien", message);
        error.postValue(message);
    }

    public void saveData() {
    }
}
