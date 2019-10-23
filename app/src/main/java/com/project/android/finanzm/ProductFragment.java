package com.project.android.finanzm;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.android.finanzm.adapter.RVProductsAdapter;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.viewmodel.CategoriesViewModel;
import com.project.android.finanzm.viewmodel.ProductViewModel;
import com.project.android.finanzm.viewmodel.ShareViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements RVProductsAdapter.CategoriesViewHolder.OnGridItemsListener {

    private int spanCount;
    private List<Product> mProducts;
    private ProductViewModel model;
    private ShareViewModel shareViewModel;

    private int id;
    private RVProductsAdapter mAdapter;
    private RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle b = getArguments();
        this.id = b.getInt("CATEGORIES_ID");
        //this.spanCount = (int)free_height / 100;
    }

    public ProductFragment() {
        this.spanCount = 2;
        this.mProducts = new ArrayList<>();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onStart();

        View view = inflater.inflate(R.layout.categories_layout, container, false);


        RecyclerView rv = initRecyclerView((RecyclerView) view);


        return rv;
    }

    @NotNull
    protected RecyclerView initRecyclerView(RecyclerView view) {
        rv = view;
        GridLayoutManager glm = new GridLayoutManager(getActivity(), this.spanCount);
        rv.setLayoutManager(glm);
        return rv;
    }

    private void initViewModel() {

        final Observer<List<Product>> productsObserver = new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                mProducts.clear();
                mProducts.addAll(products);

                if (mAdapter == null) {

                    mAdapter = new RVProductsAdapter(mProducts, getActivity(), ProductFragment.this);
                    rv.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

            }
        };
        //mViewModel = ViewModelProviders.of(CategoriesFragment.class).get(CategoriesViewModel.class);
        model = ViewModelProviders.of((FragmentActivity) getActivity()).get(ProductViewModel.class);

        model.getAllProductsForCategories(id).observe(getViewLifeCycleOwner(), productsObserver);

        shareViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ShareViewModel.class);
    }


    @Override
    public void onItemSelected(int position) {

        Product p = this.mProducts.get(position);

        this.shareViewModel.selectProduct(p);
    }


    static class ViewLifecycleOwner implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        @Override
        public LifecycleRegistry getLifecycle() {
            return lifecycleRegistry;
        }
    }

    @Nullable
    private ViewLifecycleOwner viewLifecycleOwner;

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
        viewLifecycleOwner = new ViewLifecycleOwner();
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
