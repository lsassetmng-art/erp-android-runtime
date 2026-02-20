package com.lsam.erp.runtime.signals;

import android.content.Context;
import android.content.SharedPreferences;

public final class SignalStore {

    private static final String PREF = "erp_runtime_signals";

    private SignalStore() {}

    public static void setPendingCount(Context ctx, String moduleKey, int count) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putInt("pending_" + moduleKey, Math.max(0, count)).apply();
    }

    public static int getPendingCount(Context ctx, String moduleKey) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sp.getInt("pending_" + moduleKey, 0);
    }
}