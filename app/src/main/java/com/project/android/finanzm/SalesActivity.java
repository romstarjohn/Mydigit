package com.project.android.finanzm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import com.project.android.finanzm.ActivityStates.RingingStates;
import com.project.android.finanzm.Interfaces.SalesActivityStates;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.services.MyJobService;
import com.project.android.finanzm.viewmodel.ReceiptViewModel;
import com.project.android.finanzm.viewmodel.SalesViewModel;
import com.project.android.finanzm.viewmodel.ShareViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class SalesActivity extends AppCompatActivity
{

    private ReceiptFragment receiptFragment;
    private CategoriesFragment categoriesFragment;
    private ShareViewModel shareViewModel;
    private ReceiptViewModel rViewModel;
    private SalesViewModel model;
    private SalesActivityStates state;
    private int quantity;
    private ProductFragment productFragment;
    private TextView companyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sales);
        // Force landscape orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.categoriesFragment = new CategoriesFragment();
        //categoriesFragment.setArguments(savedInstanceState);


        companyName = findViewById(R.id.name_company);
        companyName.setText("MystikoolMovil");
        companyName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        companyName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        companyName.setTypeface(Typeface.DEFAULT);

        companyName.setLayoutParams(params);
        //SetQuantityToDefaultValue
        initQuantity();

        initViewModel();
        initCategoriesFragment();


        shareViewModel.getSelectedCategories().observe(this, new Observer<ProductCategories>() {
            @Override
            public void onChanged(@Nullable ProductCategories categories) {
                if(model.isCategoriesEmpty(categories) == 0){
                    notifyError("There are no categories defined");
                }else{
                    notifyError(categories.getId() +" ");
                    initProductFragmen(categories.getId());
                }
            }
        });


        shareViewModel.getSelectedProducts().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {

                Log.i("SalesActivity", "Product is empty at " + product.getArticleCode() + " , "+product.getDescription());

                if(product == null){
                    Toast.makeText(getApplicationContext(), "Article is empty", Toast.LENGTH_LONG).show();
                }else {
                    ProductSelected(product);
                }
            }
        });


        try {
            handelUserEvent();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Initialise activity sale
        this.state = new RingingStates(this);
        hideGoBackButton();
    }

    private void showCategoriesFragment() {
        if(categoriesFragment.isAdded()){
            getFragmentManager().beginTransaction().show(categoriesFragment);

        }else{
            categoriesFragment = new CategoriesFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment3,categoriesFragment)
                    .commit();
        }

    }

    private void initCategoriesFragment() {
        //Adding Fragment to activity
        if(receiptFragment != null) {

        }
        getFragmentManager().beginTransaction()
                .add(R.id.fragment3, categoriesFragment)
                .commit();
    }

    private void initProductFragmen(int val) {

        Log.i("SalesActivity", "Categories " + val);

        Bundle b = new Bundle();
        b.putInt("CATEGORIES_ID", val);
        productFragment = new ProductFragment();
        productFragment.setArguments(b);
        //getFragmentManager().beginTransaction()
          //      .remove(categoriesFragment)
            //    .commit();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment3, productFragment)
                .commit();

        showGoBackButton();
    }

    private void handelUserEvent() throws ExecutionException, InterruptedException{
        //Fetching Keypad
        GridLayout gL = findViewById(R.id.keypad);
        //Fetching linear layout
        LinearLayout ll = findViewById(R.id.other_btn_container);


        //Handel user action Button event
        for (int i = 0; i < ll.getChildCount(); i++) {
            Button btn = (Button) ll.getChildAt(i);

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_enter:
                            try {
                                enterBtnClicker();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case R.id.btn_quantity:
                            String s = getUserEntryToString();
                            int value = Integer.parseInt(s);
                            if(0<value && value<100){
                                quantityBtnClicked(value);
                            }else
                            {
                                notifyError("Unknow action!");
                                cancel();
                            }

                            break;
                        case R.id.btn_suspend:
                            SuspendandretrievedBtnClicked();
                            break;
                        case R.id.btn_void:
                            VoidBtnClicked();
                            break;
                        case R.id.btn_cancel:
                            CancelBtnClicked();
                            break;
                        case R.id.btn_barPayment:
                            CashPaymentBtnClicked();
                            break;
                        default:
                            break;

                    }
                }
            });
        }

        //Manage Event for the Keypad
        for (int i = 0; i < gL.getChildCount(); i++){
            Button btn = (Button) gL.getChildAt(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button bi = (Button)v;
                    String texti = bi.getText().toString();
                    textEnteredToKeypad(texti);
                }
            });
        }
    }

    private void initQuantity() {
        this.quantity = 1;
    }


    public void runSchedulerCode(){
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(1001, new ComponentName(this, MyJobService.class))
                .setMinimumLatency(0)
                .setPeriodic(50000)
                .build();

        jobScheduler.schedule(jobInfo);
    }
    //These Fonction handel Activity states by user action

    public void VoidBtnClicked() {
        this.state.VoidBtnClickede();
    }

    public void enterBtnClicker() throws ExecutionException, InterruptedException{
        this.state.enterBtnClicker();
    }


    private void quantityBtnClicked(int value) {
        this.state.quantityBtnClicked(value);
    }

    public void ProductSelected(Product g) {
        this.state.ProductSelected(g);
    }


    public void textEnteredToKeypad(String s) {
        this.state.textEnteredToKeypad(s);
    }




    public void SuspendandretrievedBtnClicked() {
        this.state.SuspendandretrievedBtnClicked();
    }


    public void CashPaymentBtnClicked() {
        this.state.CashPaymentBtnClicked();
    }


    public void CancelBtnClicked() {
        this.state.CancelBtnClicked();
    }



    public void initiateCashPayment() {
        Toast.makeText(this, "PaymentInitiate "+ this.state.getClass(), Toast.LENGTH_LONG);

        if(model.startPayment()){
            rViewModel.voidSales();
            cancel();
            actualiseTotalDisplay();
        }else {

            notifyError("Please sale an article first");

        }

    }

    private void initViewModel() {
        rViewModel = ViewModelProviders.of(this)
                .get(ReceiptViewModel.class);
        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        model = ViewModelProviders.of(this).get(SalesViewModel.class);

        //CategoriesViewModel cViewModel = ViewModelProviders.of(this.categoriesFragment).get(CategoriesViewModel.class);
    }



    //Validate user entries
    public void addArticleToOpenReceipt() throws ExecutionException, InterruptedException {

        String s = getUserEntryToString();

        if(!s.isEmpty()) {

            int code = Integer.parseInt(s);

        Product newArticle = model.getProductByArticleCode(code);



            if (newArticle != null) {
                addArticleToOpenReceipt(newArticle);
            } else {

                notifyError("Unkown article!");
                cancel();
            }
        }
    }

    @NotNull
    protected String getUserEntryToString() {
        TextView editText = findViewById(R.id.userEntry);
        return String.valueOf(editText.getText());
    }


    //Insert user Entry to the ed
    public void insertUserTextToEditView(String text) {
        TextView editText= (TextView)findViewById(R.id.userEntry);
        String s = String.valueOf(editText.getText());
        s = s + text;
        editText.setText(s);
    }


    public void updateSalesWizard(String text){
        TextView editText= (TextView)findViewById(R.id.sales_wizard);
        String s = String.valueOf(editText.getText());
        s = s + text;
        editText.setText(s);
    }




    //Empty the textview component that displays user entries
    public void cancel(){
        TextView editText= findViewById(R.id.userEntry);
        String s = "";

        editText.setText(s);
    }

    //Change actual state of the activity
    public void changeState(SalesActivityStates state) {
        this.state = state;
    }


    // Update The wizard at the UI
    public void setUserViewMessage(String tag, String msg) {
        TextView TextView1 = (TextView) findViewById(R.id.sales_wizard);
        TextView1.setText(tag);

        TextView TextView2 = (TextView) findViewById(R.id.userEntry);
        TextView2.setText(msg);
    }

    public void notifyError(String errorUnkownAction) {
        Toast.makeText(this, errorUnkownAction, Toast.LENGTH_SHORT).show();
    }

    public void addArticleToOpenInvoiceReceipt() {
        //addArticleToOpenReceipt();
    }


    // Handel the receipt voiding prozess
    public void voidReceipt() {
        this.rViewModel.voidSales();
        this.model.voidReceipt();
        actualiseTotalDisplay();

    }



    public void setItemQuantity(int value) {
        updateSalesWizard(value + "x");
        this.quantity = value;
    }

    // Manage the suspend and retrieve event
    public void suspendAndRetrieveReceipt() {
        model.suspendReceipt();
        rViewModel.voidSales();
        actualiseTotalDisplay();
        changeState(new RingingStates(this));

    }

    public void addArticleToOpenReceipt(Product g) {
        this.model.registerSalesItem(g);
        this.rViewModel.addArticles(g);
        cancel();
        actualiseTotalDisplay();
    }

    public void switchTocategorieFragment(View view) {
        initCategoriesFragment();
        getFragmentManager().beginTransaction()
                .remove(productFragment)
                .commit();
        showCategoriesFragment();
        //addArticleToOpenReceipt(product);
        hideGoBackButton();
    }

    public void logOut(View view) {
        Intent intent;
        intent = new Intent(SalesActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    void hideGoBackButton(){
        Button button = findViewById(R.id.return_product);
        button.setVisibility(View.GONE);
    }
    void showGoBackButton(){
        Button button = findViewById(R.id.return_product);
        button.setVisibility(View.VISIBLE);
    }

    void actualiseTotalDisplay(){
        TextView textView = findViewById(R.id.total_value);
        textView.setText(model.getAmount() + "");
    }

    public void loginAsAdmin(View view) {
        Intent intent = new Intent(SalesActivity.this, ManagerActivity.class);
        startActivity(intent);
    }
}
