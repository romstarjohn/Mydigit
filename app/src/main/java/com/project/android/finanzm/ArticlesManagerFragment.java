package com.project.android.finanzm;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.finanzm.adapter.ArticlesAdapter;
import com.project.android.finanzm.database.MyPreference;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductCategories;
import com.project.android.finanzm.viewmodel.ArticlesManagerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticlesManagerFragment extends Fragment implements View.OnClickListener{

    private ArrayList<Product> mProduct;
    private ArrayList<ProductCategories> mProductCategories;
    private TextView companyName;
    private ArticlesAdapter mAdapter;
    private ArticlesManagerViewModel model;
    private ArrayAdapter<String> mDataAdapter;
    private Spinner mSpinner;
    private int resID;
    private ListView mLinear;
    private int pos;

    private EditText code;
    private EditText description;
    private EditText price;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
    }


    public ArticlesManagerFragment() {
        mProduct = new ArrayList<>();
        mProductCategories = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onStart();

        View view = inflater.inflate(R.layout.articles_manager_fragment, container, false);

        mLinear = (ListView) view.findViewById(R.id.article_view);


        resID = R.layout.articles_items;

        mSpinner = view.findViewById(R.id.categories_spinner);


        Button btn = view.findViewById(R.id.save_article);
        btn.setOnClickListener(this);

         code = view.findViewById(R.id.product_code1);
         description = view.findViewById(R.id.product_description1);
         price = view.findViewById(R.id.product_price1);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
    }



    private void addItemOnSpinner() {

        final Observer<List<ProductCategories>> receipt_observer = new Observer<List<ProductCategories>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategories> categories) {
                mProductCategories.clear();
                mProductCategories.addAll(categories);

                List<String> list = new ArrayList<>();
                if(categories.size() != 0){
                    for (ProductCategories categorie:categories
                    ) {
                        list.add(categorie.getDesc());
                    }
                }

                if(mDataAdapter == null) {
                    mDataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, list);
                    mDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(mDataAdapter);
                }else{
                    mDataAdapter.notifyDataSetChanged();
                }
            }
        };


        model.getAllCategories().observe(getViewLifeCycleOwner(), receipt_observer);

    }

    /*
    * Handle Spinner item Select event
    *
    */
    public void addListenerOnSpinnerItemSelection() {
        Spinner spinner = (Spinner) getView().findViewById(R.id.categories_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Item Selected" + position, Toast.LENGTH_LONG).show();
                setPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void setPosition(int position){
        pos = position;
    }

    public int getPosition(){
        return pos;
    }
    /*
     * Initialise the View Model
     * Set the adapter
     * and observe the mutable dataobject
     */
    protected void initViewModel() {
       /* final Observer<List<Product>> receipt_observer = new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {

                if (products.size() == 0){
                    Toast.makeText(getActivity(), "Empty", Toast.LENGTH_LONG).show();
                }else
                {

                    Toast.makeText(getActivity(), "Size : "+products.size(), Toast.LENGTH_LONG).show();
                }
                mProduct.clear();
                mProduct.addAll(products);
                if(mAdapter == null){

                    mAdapter = new ArticlesAdapter(getActivity(), resID, mProduct);
                    mLinear.setAdapter(mAdapter);

                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };*/

        model = ViewModelProviders.of((FragmentActivity) getActivity()).get(ArticlesManagerViewModel.class);
        addItemOnSpinner();
        //model.getAllProducts().observe(getViewLifeCycleOwner(), receipt_observer);
    }
    /*
     *
     */
    public void modifyBtnClicked(){

    }

    /*
    *
    */
    public void addBtnClicked(){
        Toast.makeText(getActivity(), "got clicked", Toast.LENGTH_LONG).show();
    }

    /*
     *
     */
    public void saveBtnClicked(){
        try {


            if (validateFormData()) {
                Product p = formToArticle();
                model.updateArticlesTables(p);
                clearInsertForm();
                MyPreference.fisrtEnd(getActivity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearInsertForm() {
        TextView code = getView().findViewById(R.id.product_code1);
        code.setText("");
        TextView description = getView().findViewById(R.id.product_description1);
        description.setText("");
        TextView price = getView().findViewById(R.id.product_price1);
        price.setText("");
    }

    private Product formToArticle() {



        Editable s = code.getText();
        Toast.makeText(getActivity(), "text " + s.toString(), Toast.LENGTH_LONG).show();
        int sCode = Integer.parseInt(s.toString());
        String sDesc =  description.getText().toString();
        Float sPrice =   Float.valueOf(price.getText().toString());

 //       return new Product(sCode,sDesc,sPrice, R.id.imageLogo,getPosition());

        return new Product(sCode, sDesc, sPrice, R.id.imageLogo, getPosition());
    }

    private boolean validateFormData() {
            return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.save_article:
                saveBtnClicked();
                break;
        }
    }


    static class ViewLifecycleOwner implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        @Override
        public LifecycleRegistry getLifecycle() {
            return lifecycleRegistry;
        }
    }

    @Nullable
    private CategoriesFragment.ViewLifecycleOwner viewLifecycleOwner;

    /**
     * @return the Lifecycle owner of the current view hierarchy,
     * or null if there is no current view hierarchy.
     */
    @Nullable
    public LifecycleOwner getViewLifeCycleOwner() {
        return viewLifecycleOwner;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewLifecycleOwner = new CategoriesFragment.ViewLifecycleOwner();
        viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_START);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        }
    }

    @Override
    public void onPause() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            viewLifecycleOwner = null;
        }
        super.onDestroyView();
    }
}
