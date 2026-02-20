package com.lsam.erp.runtime.module;

import com.lsam.erp.runtime.FeatureFlags;

import com.lsam.erp.runtime.sales.SalesEntry;
import com.lsam.erp.runtime.sales.SalesPlaceholderActivity;

import com.lsam.erp.runtime.inventory.InventoryEntry;
import com.lsam.erp.runtime.inventory.InventoryPlaceholderActivity;

import com.lsam.erp.runtime.audit.AuditEntry;
import com.lsam.erp.runtime.audit.AuditPlaceholderActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleRegistry {

    private ModuleRegistry() {}

    private static final List<ModuleDefinition> modules;

    static {
        List<ModuleDefinition> list = new ArrayList<>();

        list.add(new ModuleDefinition(
                "sales",
                "Sales",
                SalesEntry.ACTION,
                SalesPlaceholderActivity.class,
                FeatureFlags.SALES_ENABLED,
                "sales",
                InstallMode.EXTERNAL_APP
        ));

        list.add(new ModuleDefinition(
                "inventory",
                "Inventory",
                InventoryEntry.ACTION,
                InventoryPlaceholderActivity.class,
                FeatureFlags.INVENTORY_ENABLED,
                "inventory",
                InstallMode.EXTERNAL_APP
        ));

        list.add(new ModuleDefinition(
                "audit",
                "Audit",
                AuditEntry.ACTION,
                AuditPlaceholderActivity.class,
                FeatureFlags.AUDIT_ENABLED,
                "audit",
                InstallMode.EXTERNAL_APP
        ));

        modules = Collections.unmodifiableList(list);
    }

    public static List<ModuleDefinition> list() {
        return modules;
    }

    public static ModuleDefinition findByKey(String key) {
        for (ModuleDefinition m : modules) {
            if (m.key.equals(key)) return m;
        }
        return null;
    }
}