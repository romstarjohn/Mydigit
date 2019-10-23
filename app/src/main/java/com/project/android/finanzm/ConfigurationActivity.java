package com.project.android.finanzm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.project.android.finanzm.database.User;
import com.project.android.finanzm.services.StoreInitService;
import com.project.android.finanzm.utility.NetworkUtil;
import com.project.android.finanzm.utility.StoreInitData;
import com.project.android.finanzm.viewmodel.LoginViewModel;
import com.project.android.finanzm.viewmodel.MainViewModel;

import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class ConfigurationActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;

    private MainViewModel mainViewModel;
    private UserLoginTask mAuthTask = null;

    private Button mAutoConfig;
    private Button mConfig;
    private View mProgressView;
    private StoreInitData storeInitData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Force landscape orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initLoginViewModel();
        // Setting the keyboard on tje layout
        setContentView(R.layout.activity_configure);


        mAutoConfig = (Button) findViewById(R.id.auto_configure);
        mConfig = (Button) findViewById(R.id.configure);

        mProgressView = findViewById(R.id.progressBarLoginView);


        mAutoConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAutoConfiguration();
            }
        });


        mConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOfflineRegistration();
            }
        });


    }

    private void startOfflineRegistration() {
        Intent intent;
        intent = new Intent(ConfigurationActivity.this, OfflineRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void startAutoConfiguration() {
        StoreInitService storeInitService = StoreInitService.retrofit.create(StoreInitService.class);

        Call<StoreInitData> call = storeInitService.getAllStoresData();
        call.enqueue(new Callback<StoreInitData>() {
            @Override
            public void onResponse(Call<StoreInitData> call, Response<StoreInitData> response) {
                if (!response.isSuccessful()){
                    showError(NetworkUtil.onServerResponseError(response));
                    return;
                }

                if(response.body() != null){
                    if(storeInitData == null)
                        storeInitData = new StoreInitData();

                    storeInitData = response.body();
                    displayStatus(mainViewModel.saveData());
                }
            }

            @Override
            public void onFailure(Call<StoreInitData> call, Throwable t) {

                showError(t.getLocalizedMessage());
            }
        });
    }


    private void showError(String onServerResponseError) {
        Toast.makeText(this, onServerResponseError, Toast.LENGTH_LONG).show();
    }


    private void initLoginViewModel() {
        loginViewModel = ViewModelProviders.of(this)
                    .get(LoginViewModel.class);
        mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
    }


    private void displayStatus(void saveData) {
    }




    private boolean isPasswordValid(int password) {
        return password > 999;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {


        Log.d("Authentification", "Authentification is started");


    }





    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserLoginTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            User user ;

            Log.d("startAuthentification", "Authentification is started in Background");
            try {
                //Toast.makeText(this, "No User available "+ user.getUserId() + " pin "+ user.getPin(), Toast.LENGTH_LONG);
                mainViewModel.startStoreInit();
                // Simulate network access.

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            mProgressView.setVisibility(View.GONE);

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mProgressView.setVisibility(View.GONE);
        }
    }
}

