package com.lsam.erp.runtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsam.erp.runtime.module.InstallMode;
import com.lsam.erp.runtime.module.ModuleDefinition;
import com.lsam.erp.runtime.module.ModuleRegistry;
import com.lsam.erp.runtime.module.RemoteConfig;
import com.lsam.erp.runtime.ui.ModuleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ModuleAdapter adapter;

    private final BroadcastReceiver signalsChanged = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (adapter != null) adapter.refreshBadges();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.modulesRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<ModuleDefinition> modules = applyRemoteOverrides(ModuleRegistry.list());
        adapter = new ModuleAdapter(this, modules);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(signalsChanged, new IntentFilter("com.lsam.erp.runtime.SIGNALS_CHANGED"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(signalsChanged);
    }

    private List<ModuleDefinition> applyRemoteOverrides(List<ModuleDefinition> base) {
        Map<String, RemoteConfig.Override> overrides = RemoteConfig.loadOverrides(this);
        List<ModuleDefinition> out = new ArrayList<>();

        for (ModuleDefinition m : base) {
            RemoteConfig.Override ov = overrides.get(m.key);

            String displayName = (ov != null && ov.displayName != null) ? ov.displayName : m.displayName;
            boolean enabled = (ov != null && ov.enabled != null) ? ov.enabled : m.enabled;
            String moduleName = (ov != null && ov.moduleName != null) ? ov.moduleName : m.moduleName;
            InstallMode installMode = (ov != null && ov.installMode != null) ? ov.installMode : m.installMode;

            out.add(new ModuleDefinition(
                    m.key,
                    displayName,
                    m.action,
                    m.placeholder,
                    enabled,
                    moduleName,
                    installMode
            ));
        }

        return out;
    }
}