package com.lsam.erp.runtime.inventory;

import android.app.Activity;
import android.content.Intent;   // ← これ追加
import android.os.Bundle;
import android.widget.TextView;

public class InventoryPlaceholderActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        // 仮：在庫アラート3件ある想定
        int pendingCount = 3;

        Intent intent = new Intent("com.lsam.erp.SIGNAL_UPDATE");
        intent.putExtra("module_key", "inventory");
        intent.putExtra("pending_count", pendingCount);
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("Inventory Module\n(not connected yet)");
        tv.setTextSize(18);
        tv.setPadding(40, 40, 40, 40);

        setContentView(tv);
    }
}