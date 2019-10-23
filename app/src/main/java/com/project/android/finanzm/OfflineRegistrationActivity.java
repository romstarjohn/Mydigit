package com.project.android.finanzm;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.project.android.finanzm.Interfaces.Helper;
import com.project.android.finanzm.viewmodel.ArticlesManagerViewModel;

public class OfflineRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    ArticlesManagerViewModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_configure);
        initViewModel();

        Button btn = findViewById(R.id.login);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login :
                submitForm();
                break;
        }

    }

    private void submitForm() {
        if(validateUserEntry()){
            try {


                model.changeIsFirstStartConfig("is_first_start", 1);
                // Delete before lunching the app
                model.initData();
            }catch (Exception e){
                e.printStackTrace();
            }
            Helper.setConfigValue(this, "is_first_start");
            Log.i("this", "res"+Helper.getConfigValue(this, "is_first_start"));
            Intent intent = new Intent(OfflineRegistrationActivity.this, ManagerActivity.class);
            startActivity(intent);
        }else{

        }
    }

    private boolean validateUserEntry() {

        return true;
    }

    private void initViewModel() {
        model = ViewModelProviders.of(this)
                .get(ArticlesManagerViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

