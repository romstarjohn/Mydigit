package com.project.android.finanzm.services;

import com.project.android.finanzm.utility.Constants;
import com.project.android.finanzm.utility.StoreInitData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface StoreInitService {

    Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/backupapi/storeinit") //http://localhost:8080/backupapi/storeinit
    Call<StoreInitData> getAllStoresData();
}
