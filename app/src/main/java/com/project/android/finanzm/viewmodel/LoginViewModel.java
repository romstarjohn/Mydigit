package com.project.android.finanzm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.project.android.finanzm.database.AppConfig;
import com.project.android.finanzm.database.AppRepository;
import com.project.android.finanzm.database.MyPreference;
import com.project.android.finanzm.database.User;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LiveData<List<User>> mUser;
    AppRepository repository;

    public LoginViewModel( Application application) {
        super(application);


        repository = AppRepository.getInstance(application.getApplicationContext());

    }

    public User getUserByUserID(int userId){

        return repository.getUsersById(userId);
                //return user;

    }

    private LiveData<List<User>> getAllUsers(){
        return repository.getAllUsers();
    }

    public AppConfig getAppConfigByDescription(String isFirstStart) {
        return repository.getAppConfigByDescription(isFirstStart);
    }
}
