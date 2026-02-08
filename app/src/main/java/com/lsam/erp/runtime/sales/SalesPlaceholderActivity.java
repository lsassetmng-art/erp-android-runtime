package com.lsam.erp.runtime.sales;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SalesPlaceholderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("Sales Module\\n(not connected yet)");
        tv.setTextSize(18);
        tv.setPadding(40, 40, 40, 40);

        setContentView(tv);
    }
}
