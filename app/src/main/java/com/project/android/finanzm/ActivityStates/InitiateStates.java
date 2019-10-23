package com.project.android.finanzm.ActivityStates;

import com.project.android.finanzm.Interfaces.SalesActivityStates;
import com.project.android.finanzm.SalesActivity;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;

import java.util.concurrent.ExecutionException;

public class InitiateStates implements SalesActivityStates {


    private static final String TAG = "SALES / READY";

    SalesActivity activity;
    private boolean initialState;

    public InitiateStates(SalesActivity activity){
        this.activity = activity;
        this.activity.setUserViewMessage(TAG, "Select/Enter item code");
        initialState = true;
    }

    @Override
    public void VoidBtnClickede() {
        this.activity.changeState(new VoidStates(this.activity));

    }

    @Override
    public void enterBtnClicker() throws ExecutionException, InterruptedException {
        this.activity.addArticleToOpenReceipt();
    }

    @Override
    public void ProductSelected(Product g) {

    }

    @Override
    public void textEnteredToKeypad(String s) {
        if (initialState) {
            initialState = false;
            this.activity.cancel();
        }
        this.activity.insertUserTextToEditView(s);
    }

    @Override
    public void quantityBtnClicked(int value) {
        this.activity.setItemQuantity(value);
    }


    @Override
    public void SuspendandretrievedBtnClicked() {
        this.activity.changeState(new SuspendAndRetrieveStates(this.activity));

    }

    @Override
    public void CashPaymentBtnClicked() {

        if (initialState) {
            this.activity.notifyError("Action impossible, first sale an item!");
        }else {
            this.activity.initiateCashPayment();initialState = true;
        }
    }

    @Override
    public void CancelBtnClicked() {

        this.activity.cancel();
    }
}
