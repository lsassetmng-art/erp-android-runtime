package com.lsam.erp.runtime.module;

import android.app.Activity;
import android.widget.Toast;

public final class DynamicInstallStub {

    private DynamicInstallStub() {}

    public static void requestInstall(Activity activity, ModuleDefinition def) {
        // No Play Core dependency here.
        // Future: integrate SplitInstallManager properly in a dedicated Phase.
        Toast.makeText(activity,
                "Install (stub): " + def.displayName + " / moduleName=" + def.moduleName,
                Toast.LENGTH_LONG).show();
    }
}