package com.project.android.finanzm.services;

import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoreTransferServices {

    @GET("/api/products")
    Call<List<Product>> getProductList();

    @GET("/api/users")
    Call<List<User>> getTillUsers();

    // Provide a specifics user
    @GET("/api/users/(id)")
    Call<List<User>> getTillUsersById(@Path("id") String id);

    @POST("/api/users")
    Call<List<User>> postTillUsers(@Body List<User> mUser);
}
