package com.project.android.finanzm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.finanzm.Interfaces.Helper;
import com.project.android.finanzm.database.AppConfig;
import com.project.android.finanzm.database.MyPreference;
import com.project.android.finanzm.database.User;
import com.project.android.finanzm.viewmodel.LoginViewModel;
import com.project.android.finanzm.viewmodel.MainViewModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;

    private MainViewModel mainViewModel;
    /**

     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.

    private EditText mPasswordView;
    private EditText mUserIdView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Force landscape orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initLoginViewModel();

        String isFirstStart = "is_first_start";


        if(loginViewModel.getAppConfigByDescription(isFirstStart) == null ){
            Intent itent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(itent);
        }else{

        // Setting the keyboard on tje layout
        setContentView(R.layout.activity_login);
/*
            //LinearLayout ln = (LinearLayout) findViewById(R.id.linearLayout);
            //ln.addView(new KeypadView(this));
        RecyclerView rv = (RecyclerView) findViewById(R.id.categorielayout);
        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(),5);

        rv.setLayoutManager(glm);
        ArrayList<Griditem> gItems = new ArrayList<>();
        gItems.add(new Griditem("Food", R.drawable.logo, Type.CATEGORIES));
        gItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));

        RVAdapter adapter = new RVAdapter(gItems);
        rv.setAdapter(adapter);

*/


        mUserIdView = (EditText) findViewById(R.id.userId);

        mPasswordView = (EditText) findViewById(R.id.userPin);

        mProgressView = findViewById(R.id.progressBarLoginView);


       /* mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

       // Button mSignInButton = (Button) findViewById(R.id.login);
        //mSignInButton.setOnClickListener(new OnClickListener() {
          //  @Override
       //    public void onClick(View view) {
      //          attemptLogin();
       //     }
       // });


        //Button mRegisterButton = (Button) findViewById(R.id.login_reset);
        /*mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });*/


        GridLayout gL = (GridLayout) findViewById(R.id.loginPad);


        for (int i = 0; i < gL.getChildCount(); i++) {
            Button btn = (Button) gL.getChildAt(i);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cancel:
                            cancel();
                            break;
                        case R.id.login:
                            EditText Etxt = (EditText) getCurrentFocus();
                            if (R.id.userPin != Etxt.getId()) {
                                Log.d("startAuthentification", "Authentification is started" + Etxt.getId() + " : " + R.id.password);

                                EditText ed = (EditText) findViewById(R.id.userPin);
                                ed.requestFocus();
                                ed.setSelection(ed.getText().length());
                            } else {
                                attemptLogin();
                            }
                            break;
                        default:
                            Button bi = (Button) v;
                            String text = bi.getText().toString();

                            insertUserTextToEditView(text);
                            break;
                    }

                }
            });

            }
        }
    }


    private void initLoginViewModel() {
        loginViewModel = ViewModelProviders.of(this)
                    .get(LoginViewModel.class);
        mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
    }

    private void attemptRegister() {

    }

    private void insertUserTextToEditView(String text) {
        EditText editText= (EditText)getCurrentFocus();
        String s = String.valueOf(editText.getText());
        s = s + text;
        editText.setText(s);
        editText.setSelection(s.length());
    }

    public void cancel(){
        EditText editText= (EditText)getCurrentFocus();
        String s = "";

        editText.setText(s);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        boolean loginSuccess = false;

        if (mAuthTask != null) {
            return;
        }




        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        int userId = Integer.parseInt(mUserIdView.getText().toString());
        int password = Integer.parseInt(mPasswordView.getText().toString());
        Log.d("startAuthentification", "Authentification is started"+ userId + " : "+ password);
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (password != 0 && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (userId == 0) {
            mUserIdView.setError(getString(R.string.error_field_required));
            focusView = mUserIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgressView.setVisibility(View.VISIBLE);
            //mAuthTask = new UserLoginTask(userId, password);
            //mAuthTask.execute((Void) null);
            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute();

        }



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

        private final int mUserId;
        private final int mPassword;

        UserLoginTask(int userId, int password) {
            mUserId = userId;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            User user ;
            // TODO: attempt authentication against a network service.
            Log.d("startAuthentification", "Authentification is started in Background");
            try {

                user = loginViewModel.getUserByUserID(mUserId);
                //Toast.makeText(this, "No User available "+ user.getUserId() + " pin "+ user.getPin(), Toast.LENGTH_LONG);
                mainViewModel.startStoreInit();


                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            if(user != null) {

                //Toast.makeText(getApplicationContext(), " User available "+ user.getUserId() + " pin "+ user.getPin(), Toast.LENGTH_LONG).show();
                    if (user.getUserId() == (mUserId) && user.getPin() == mPassword) {
                        Log.d("Logid", "Started Loggin");
                        // Account exists, return true if the password matches.

                        return true;
                    }
                    Log.d("Logid", "Logging Failled, Password incorrect");

            }else
                Log.d("Logid", "Logging Failled, No such user");


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            mProgressView.setVisibility(View.GONE);
            if (success) {
                Log.d("Test", "Login success!");
                Intent intent;
                intent = new Intent(LoginActivity.this, SalesActivity.class);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                EditText editText= (EditText)getCurrentFocus();
                String s = "";

                editText.setText(s);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

