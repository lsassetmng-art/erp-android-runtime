package com.lsam.erp.runtime.module;

public enum InstallMode {
    EXTERNAL_APP,     // separate installed app (ENTRY via Intent)
    DYNAMIC_FEATURE   // same app, install on demand (future)
}