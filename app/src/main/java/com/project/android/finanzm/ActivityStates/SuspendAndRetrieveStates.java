package com.project.android.finanzm.ActivityStates;

import android.util.Log;

import com.project.android.finanzm.Interfaces.SalesActivityStates;
import com.project.android.finanzm.SalesActivity;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;

public class SuspendAndRetrieveStates  implements SalesActivityStates {

    SalesActivity activity;

    private static final String TAG = "SUSPEND/RETRIEVE";
    private static final String CONFIRM_MSG = "Enter = YES , CANCEL = NO";
    private static final String ERROR_UNKOWN_ACTION = "Unknow action, hit CANCEL";


    SuspendAndRetrieveStates(SalesActivity activity) {
        this.activity = activity;


        this.activity.setUserViewMessage(TAG +"?", CONFIRM_MSG);
        Log.d("States", "Ringing state got started" );
    }

    @Override
    public void VoidBtnClickede() {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void enterBtnClicker() {
        this.activity.suspendAndRetrieveReceipt();
    }

    @Override
    public void ProductSelected(Product g) {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void textEnteredToKeypad(String s) {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void quantityBtnClicked(int value) {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void SuspendandretrievedBtnClicked() {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void CashPaymentBtnClicked() {
        this.activity.notifyError(ERROR_UNKOWN_ACTION);
    }

    @Override
    public void CancelBtnClicked() {
        this.activity.changeState(new RingingStates(this.activity));
    }
}