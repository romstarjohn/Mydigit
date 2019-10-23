package com.project.android.finanzm.Interfaces;

import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;

import java.util.concurrent.ExecutionException;

public interface SalesActivityStates {


    void VoidBtnClickede();
    void enterBtnClicker() throws ExecutionException, InterruptedException;
    void ProductSelected(Product g);
    void textEnteredToKeypad(String s);
    void quantityBtnClicked(int value);
    void SuspendandretrievedBtnClicked();
    void CashPaymentBtnClicked();
    void CancelBtnClicked();

}
