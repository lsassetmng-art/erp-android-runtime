package com.lsam.erp.runtime.signals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ErpSignalReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.lsam.erp.SIGNAL_UPDATE";
    public static final String EXTRA_MODULE_KEY = "module_key";
    public static final String EXTRA_PENDING_COUNT = "pending_count";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        if (!ACTION.equals(intent.getAction())) return;

        String key = intent.getStringExtra(EXTRA_MODULE_KEY);
        int count = intent.getIntExtra(EXTRA_PENDING_COUNT, 0);
        if (key == null || key.isEmpty()) return;

        SignalStore.setPendingCount(context, key, count);

        // notify UI (local broadcast-like without dependency)
        Intent changed = new Intent("com.lsam.erp.runtime.SIGNALS_CHANGED");
        context.sendBroadcast(changed);
    }
}