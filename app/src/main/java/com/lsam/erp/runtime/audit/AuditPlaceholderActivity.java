package com.lsam.erp.runtime.audit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AuditPlaceholderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("Audit Module\n(not connected yet)");
        tv.setTextSize(18);
        tv.setPadding(40, 40, 40, 40);

        setContentView(tv);
    }
}
