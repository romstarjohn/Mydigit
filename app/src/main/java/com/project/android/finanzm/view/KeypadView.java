package com.project.android.finanzm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.project.android.finanzm.R;

public class KeypadView extends GridLayout {

    private TextView code;
    private TextView unitprice;
    private TextView price;
    private TextView desc;
    private OnKeyPadClickListerner onKeyPadClickListerner;

    public KeypadView(Context context) {
        super(context);

        LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sv.inflate(R.layout.keypad, this, true);

        initSalesEntry();
    }

    public KeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater sv = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sv.inflate(R.layout.keypad, this, true);

        initSalesEntry();
    }

    private void initSalesEntry() {


        GridLayout g = (GridLayout) this.getChildAt(0);
        Button btn = new Button(getContext());
        for(int i = 0; i < g.getChildCount();i++){

            if(g.getChildAt(i) instanceof Button)
               btn = (Button) g.getChildAt(i);






            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                    switch (v.getId()){
                        case R.id.btn_enter:
                            //do this
                            break;
                        case R.id.btn_quantity:
                            //do this
                            break;
                        case R.id.btn_suspend:
                            //do this
                            break;
                        case R.id.btn_void:
                            //do this
                            break;
                        default:
                            //insert value
                            Button b = (Button)v;
                            String text = b.getText().toString();
                            Log.d("Btn", "Got clicked");
                            Toast t;
                            t = (Toast) Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
                            t.show();

                            //addProductCode(text);

                            break;


                    }

                }
            });
        }
    }


    //Creating Listener Interface
    public interface OnKeyPadClickListerner{
        void onEvent();
    }

    public void setCustomEventListener(OnKeyPadClickListerner eventListener){
        onKeyPadClickListerner = eventListener;
    }

    private void addProductCode(String text) {
        EditText editText= (EditText)findViewById(R.id.userEntry);
        String s = String.valueOf(editText.getText());
        s = s + text;
        editText.setText(s);
    }


    public KeypadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
