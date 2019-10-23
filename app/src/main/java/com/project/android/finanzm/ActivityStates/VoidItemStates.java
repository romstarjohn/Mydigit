package com.project.android.finanzm.ActivityStates;

import android.util.Log;

import com.project.android.finanzm.Interfaces.SalesActivityStates;
import com.project.android.finanzm.SalesActivity;

public class VoidItemStates extends VoidStates {

    private static final String TAG = "SALES / READY";

    private boolean initialState;

    public VoidItemStates(SalesActivity activity) {
        super(activity);
        this.activity.setUserViewMessage(TAG, "Select/Enter item code");
        initialState = true;
        Log.d("States", "Ringing state got started" );
    }

    @Override
    public void enterBtnClicker() {
        this.activity.addArticleToOpenInvoiceReceipt();
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
    public void CancelBtnClicked() {
        this.activity.cancel();
    }

    @Override
    public void CashPaymentBtnClicked() {
        this.activity.initiateCashPayment();
    }
}
