package com.project.android.finanzm.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.project.android.finanzm.R;

public class SalesEntryView extends ConstraintLayout {
    private TextView code;
    private TextView unitprice;
    private TextView price;
    private TextView desc;
    public SalesEntryView(Context context) {
        super(context);

        LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sv.inflate(R.layout.product_sale_item, this, true);

        initSalesEntry();
    }

    public SalesEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sv.inflate(R.layout.product_sale_item, this, true);

        initSalesEntry();
    }

    private void initSalesEntry(){
        this.code = (TextView) findViewById(R.id.item_code);
        this.price = (TextView) findViewById(R.id.item_price);
        this.desc = (TextView) findViewById(R.id.item_description);
    }
    public void createItem(String code, String price, String desc, String unitprice){
        this.code.setText(code);
        this.desc.setText(desc);
        this.price.setText(price);
    }

    public SalesEntryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
