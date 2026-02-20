package com.lsam.erp.runtime;

public final class FeatureFlags {

    private FeatureFlags() {}

    // Default ON (RemoteConfig can override at runtime)
    public static final boolean SALES_ENABLED = true;
    public static final boolean INVENTORY_ENABLED = true;
    public static final boolean AUDIT_ENABLED = true;
}