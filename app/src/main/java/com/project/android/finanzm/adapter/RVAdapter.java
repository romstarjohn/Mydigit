package com.project.android.finanzm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.finanzm.R;
import com.project.android.finanzm.database.ProductCategories;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CategoriesViewHolder> {
    private final List<ProductCategories> items;
    private final Context mContext ;

    private CategoriesViewHolder.OnGridItemsListener onGridItemsListener;

    public RVAdapter(List<ProductCategories> items, Context context, CategoriesViewHolder.OnGridItemsListener onGridItemsListener){
        this.items = items;
        mContext = context;
        this.onGridItemsListener = onGridItemsListener;
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_grid_item,viewGroup, false);
        CategoriesViewHolder cat_vh = new CategoriesViewHolder(v, onGridItemsListener);

        return cat_vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i) {

        Log.i("ADAPTER", "id = " + items.get(i).getId());
        categoriesViewHolder.categoriesName.setText(items.get(i).getDesc());
        categoriesViewHolder.categoriesPhoto.setImageResource(items.get(i).getImageRessource());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    // Manage the View
    public static class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoriesPhoto;
        TextView categoriesName;
        private OnGridItemsListener onGridItemsListener;
        CardView cv;
        public CategoriesViewHolder(@NonNull View itemView, OnGridItemsListener onGridItemsListener) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.categoriegrid);
            categoriesName = (TextView) itemView.findViewById(R.id.grid_Item_decription);
            categoriesPhoto = (ImageView) itemView.findViewById(R.id.grid_Item_imageView);
            this.onGridItemsListener = onGridItemsListener;
            itemView.setOnClickListener(this);

        }

        public void onClick(View view){
            Toast.makeText(view.getContext(), "position = " + getAdapterPosition(), Toast.LENGTH_LONG).show();
            this.onGridItemsListener.onItemSelected(getAdapterPosition());
        }


        public interface OnGridItemsListener{
            public void onItemSelected(int position);
        }


    }
}
