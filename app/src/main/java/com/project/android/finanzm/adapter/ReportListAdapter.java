package com.project.android.finanzm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.android.finanzm.R;
import com.project.android.finanzm.database.SalesReceipt;

import java.util.List;

public class ReportListAdapter extends ArrayAdapter<SalesReceipt> {

    private int resource;
    private Context context;
    private List<SalesReceipt> objects;



    public ReportListAdapter(@NonNull Context context, int resource, @NonNull List<SalesReceipt> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConstraintLayout c;

        SalesReceipt item = this.objects.get(position);

        String code = "" + item.getId();
        String price = "" + item.getAmount();
        String desc = item.getDate()+ "";

        if (convertView == null) {
            c = new ConstraintLayout(getContext());
            LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sv.inflate(resource, c, true);

        } else {
            c = (ConstraintLayout) convertView;
        }

        TextView codeView = (TextView) c.findViewById(R.id.item_code);
        TextView priceView = (TextView) c.findViewById(R.id.item_price);
        TextView descView = (TextView) c.findViewById(R.id.item_description);

        codeView.setText(code);
        descView.setText(desc);
        priceView.setText(price);
        //return super.getView(position, convertView, parent);
        return c;
    }
}
