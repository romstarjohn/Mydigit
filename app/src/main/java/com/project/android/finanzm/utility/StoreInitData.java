package com.project.android.finanzm.utility;

import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.database.Taxe;
import com.project.android.finanzm.database.User;

import java.util.List;

public class StoreInitData {

    private List<Product> myProducts;
    private List<ProductCategories> myCategories;
    private List<Taxe> myTaxes;
    private List<User> myUsers;

    public StoreInitData() {
    }

    public List<Product> getMyProducts() {
        return myProducts;
    }

    public void setMyProducts(List<Product> myProducts) {
        this.myProducts = myProducts;
    }

    public List<ProductCategories> getMyCategories() {
        return myCategories;
    }

    public void setMyCategories(List<ProductCategories> myCategories) {
        this.myCategories = myCategories;
    }

    public List<Taxe> getMyTaxes() {
        return myTaxes;
    }

    public void setMyTaxes(List<Taxe> myTaxes) {
        this.myTaxes = myTaxes;
    }

    public List<User> getMyUsers() {
        return myUsers;
    }

    public void setMyUsers(List<User> myUsers) {
        this.myUsers = myUsers;
    }
}
