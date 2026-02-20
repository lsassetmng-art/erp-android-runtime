package com.lsam.erp.runtime.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsam.erp.runtime.R;
import com.lsam.erp.runtime.module.DynamicInstallStub;
import com.lsam.erp.runtime.module.InstallMode;
import com.lsam.erp.runtime.module.ModuleDefinition;
import com.lsam.erp.runtime.signals.SignalStore;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.VH> {

    private final Activity activity;
    private final List<ModuleDefinition> modules;

    public ModuleAdapter(Activity activity, List<ModuleDefinition> modules) {
        this.activity = activity;
        this.modules = modules;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ModuleDefinition m = modules.get(position);

        h.title.setText(m.displayName);
        h.subtitle.setText(m.enabled ? "Enabled" : "Disabled");

        int pending = SignalStore.getPendingCount(activity, m.key);
        h.badge.setText(String.valueOf(pending));
        h.badge.setVisibility(pending > 0 ? View.VISIBLE : View.GONE);

        boolean resolvable = false;
        if (m.enabled) {
            Intent intent = new Intent(m.action);
            PackageManager pm = activity.getPackageManager();
            resolvable = (intent.resolveActivity(pm) != null);
        }

        if (!m.enabled) {
            h.action.setEnabled(false);
            h.action.setText("Disabled");
        } else if (m.installMode == InstallMode.DYNAMIC_FEATURE) {
            // For dynamic feature, we assume "not installed" until real implementation.
            h.action.setEnabled(true);
            h.action.setText(resolvable ? "Open" : "Install");
        } else {
            // EXTERNAL_APP
            h.action.setEnabled(true);
            h.action.setText(resolvable ? "Open" : "Open (Fallback)");
        }

        h.action.setOnClickListener(v -> {
            if (!m.enabled) return;

            Intent intent = new Intent(m.action);
            PackageManager pm = activity.getPackageManager();

            if (intent.resolveActivity(pm) != null) {
                activity.startActivity(intent);
                return;
            }

            if (m.installMode == InstallMode.DYNAMIC_FEATURE) {
                DynamicInstallStub.requestInstall(activity, m);
                return;
            }

            activity.startActivity(new Intent(activity, m.placeholder));
        });
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public void refreshBadges() {
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        TextView badge;
        Button action;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modTitle);
            subtitle = itemView.findViewById(R.id.modSubtitle);
            badge = itemView.findViewById(R.id.modBadge);
            action = itemView.findViewById(R.id.modAction);
        }
    }
}