package com.lsam.erp.runtime;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.content.pm.PackageManager;

import com.lsam.erp.runtime.FeatureFlags;
import com.lsam.erp.runtime.sales.SalesEntry;
import com.lsam.erp.runtime.sales.SalesPlaceholderActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openSales() {
        Intent intent = new Intent(SalesEntry.ACTION);

        PackageManager pm = getPackageManager();
        if (FeatureFlags.SALES_ENABLED &&
                intent.resolveActivity(pm) != null) {
            startActivity(intent);
        } else {
            startActivity(new Intent(this, SalesPlaceholderActivity.class));
        }
    }

}