package com.project.android.finanzm;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.finanzm.adapter.ArticlesAdapter;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.viewmodel.ArticlesManagerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ManageArticlesFragment extends Fragment implements View.OnClickListener{

    private ArrayList<Product> mProduct;
    private TextView companyName;
    private ArticlesAdapter mAdapter;
    private ArticlesManagerViewModel model;

    private int resID;
    private ListView mLinear;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        Bundle b = getArguments();
        mProduct.add(new Product(b));
        Log.d("Enter", "Got clicked, Sending "+ 400 + " to fragment" );

    }

    public ManageArticlesFragment() {
        mProduct = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onStart();

        View view = inflater.inflate(R.layout.articlemanager_fragment, container, false);

        mLinear = (ListView) view.findViewById(R.id.article_view);




        // Getting item ID
        resID = R.layout.articles_items;

        //initViewModel();
        //mProduct.add(new com.project.android.finanzm.dummy.Product(1200, "Dummi product 1", (float) 2.00));
        //mProduct.add(new com.project.android.finanzm.dummy.Product(1201, "Dummi product 2", (float) 5.00));
       // mProduct.add(new com.project.android.finanzm.dummy.Product(1202, "Dummi product 3", (float) 3.00));
       // mProduct.add(new com.project.android.finanzm.dummy.Product(1203, "Dummi product 4", (float) 4.00));
       // mProduct.add(new com.project.android.finanzm.dummy.Product(1204, "Dummi product 5", (float) 5.50));

      //  Log.d("ArrayList count", String.valueOf(mProduct.size()));

        /*
        // use a linear layout manager
        for(Product item : mProduct){
            SalesEntryView v = new SalesEntryView(this);
            v.createItem("" + item.getArticleCode(),  String.format("%s €", item.getPrice()), item.getDescription(), "1x " + item.getPrice());
            mLinear.addView(v);
        }
        */


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
    }
    /*
     * Initialise the View Model
     * Set the adapter
     * and observe the mutable dataobject
     */
    protected void initViewModel() {
        final Observer<List<Product>> receipt_observer = new Observer<List<Product>>() {
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
        };

        model = ViewModelProviders.of((FragmentActivity) getActivity()).get(ArticlesManagerViewModel.class);

        model.getAllProducts().observe(getViewLifeCycleOwner(), receipt_observer);
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

    }

    @Override
    public void onClick(View v) {

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
