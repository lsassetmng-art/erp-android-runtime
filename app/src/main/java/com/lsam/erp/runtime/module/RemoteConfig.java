package com.lsam.erp.runtime.module;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class RemoteConfig {

    private RemoteConfig() {}

    public static final class Override {
        public final String displayName;
        public final Boolean enabled;
        public final String moduleName;
        public final InstallMode installMode;

        public Override(String displayName, Boolean enabled, String moduleName, InstallMode installMode) {
            this.displayName = displayName;
            this.enabled = enabled;
            this.moduleName = moduleName;
            this.installMode = installMode;
        }
    }

    public static Map<String, Override> loadOverrides(Context context) {
        Map<String, Override> map = new HashMap<>();

        try {
            InputStream is = context.getAssets().open("modules.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            JSONObject root = new JSONObject(sb.toString());
            JSONArray modules = root.optJSONArray("modules");
            if (modules == null) return map;

            for (int i = 0; i < modules.length(); i++) {
                JSONObject m = modules.getJSONObject(i);
                String key = m.optString("key", "");
                if (key.isEmpty()) continue;

                String displayName = m.has("displayName") ? m.optString("displayName") : null;
                Boolean enabled = m.has("enabled") ? m.optBoolean("enabled") : null;
                String moduleName = m.has("moduleName") ? m.optString("moduleName") : null;

                InstallMode installMode = null;
                if (m.has("installMode")) {
                    String s = m.optString("installMode", "");
                    if ("DYNAMIC_FEATURE".equalsIgnoreCase(s)) installMode = InstallMode.DYNAMIC_FEATURE;
                    else if ("EXTERNAL_APP".equalsIgnoreCase(s)) installMode = InstallMode.EXTERNAL_APP;
                }

                map.put(key, new Override(displayName, enabled, moduleName, installMode));
            }

        } catch (Exception ignored) {
            // If missing or broken, fall back to compile-time defaults.
        }

        return map;
    }
}