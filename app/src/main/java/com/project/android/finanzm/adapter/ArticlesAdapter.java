package com.project.android.finanzm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.android.finanzm.R;
import com.project.android.finanzm.database.Product;

import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<Product> {

    private int resource;
    private Context context;
    private List<Product> objects;



    public ArticlesAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConstraintLayout c;

        Product item = this.objects.get(position);

        String code = "" + item.getArticleCode();
        String price = "" + item.getPrice();
        String desc = item.getDescription();
        String cat_id = "" + item.getCategories_id();
        String imageRessource = "" + item.getImageRessources();
        String unitprice = "1 x " + String.valueOf(item.getPrice());

        if (convertView == null) {
            c = new ConstraintLayout(getContext());
            LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sv.inflate(resource, c, true);

        } else {
            c = (ConstraintLayout) convertView;
        }

        TextView codeView = (TextView) c.findViewById(R.id.code);
        TextView priceView = (TextView) c.findViewById(R.id.price);
        TextView descView = (TextView) c.findViewById(R.id.description);
        ImageView img = c.findViewById(R.id.articles_images);
        TextView cat = c.findViewById(R.id.categories);

        codeView.setText(code);
        descView.setText(desc);
        priceView.setText(price);
        img.setImageResource(R.drawable.logo);
        cat.setText(cat_id);
        //return super.getView(position, convertView, parent);
        return c;
    }
}
