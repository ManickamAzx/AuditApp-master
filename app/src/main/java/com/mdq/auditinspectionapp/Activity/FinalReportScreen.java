package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdq.auditinspectionapp.R;
import com.mdq.auditinspectionapp.Utils.PreferenceManager;
import com.mdq.auditinspectionapp.databinding.ActivityFinalReportScreenBinding;
import com.mdq.auditinspectionapp.databinding.ActivityFinalReportScreenBindingImpl;

public class FinalReportScreen extends AppCompatActivity {

    TextView name;
    PreferenceManager preferenceManager;
    ActivityFinalReportScreenBinding activityFinalReportScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFinalReportScreenBinding=ActivityFinalReportScreenBinding.inflate(getLayoutInflater());
        setContentView(activityFinalReportScreenBinding.getRoot());

        name=findViewById(R.id.name);
        name.setText(getPreferenceManager().getPrefUsername());

        activityFinalReportScreenBinding.Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityFinalReportScreenBinding.Backarraow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Timing out for fetch data", Toast.LENGTH_SHORT).show();
            }
        },8000);

    }

    /**
     * @return
     * @brief initializing the preferenceManager from shared preference for local use in this activity
     */
    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = PreferenceManager.getInstance();
            preferenceManager.initialize(getApplicationContext());
        }
        return preferenceManager;
    }
}