package com.lsam.erp.runtime.module;

public class ModuleDefinition {

    public final String key;
    public final String displayName;
    public final String action;
    public final Class<?> placeholder;
    public final boolean enabled;

    // Phase5 ready
    public final String moduleName;     // dynamic-feature module id (future)
    public final InstallMode installMode;

    public ModuleDefinition(
            String key,
            String displayName,
            String action,
            Class<?> placeholder,
            boolean enabled,
            String moduleName,
            InstallMode installMode
    ) {
        this.key = key;
        this.displayName = displayName;
        this.action = action;
        this.placeholder = placeholder;
        this.enabled = enabled;
        this.moduleName = moduleName;
        this.installMode = installMode;
    }
}