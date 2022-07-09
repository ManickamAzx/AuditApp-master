
package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.mdq.auditinspectionapp.R;
import com.mdq.auditinspectionapp.Utils.PreferenceManager;
import com.mdq.auditinspectionapp.databinding.ActivityReportScreenBinding;

public class ReportScreen extends AppCompatActivity {

    ActivityReportScreenBinding activityReportScreenBinding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReportScreenBinding=ActivityReportScreenBinding.inflate(getLayoutInflater());
        setContentView(activityReportScreenBinding.getRoot());

        activityReportScreenBinding.welcomeText.setText("welcome "+getPreferenceManager().getPrefUsername());

        activityReportScreenBinding.CardProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportScreen.this,selectionForReport.class)
                        .putExtra("ffrom","production"));
            }
        });

        activityReportScreenBinding.inspections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportScreen.this,selectionForReport.class)
                        .putExtra("ffrom","inspection")
                        .putExtra("dpid",getPreferenceManager().getPrefDpid()));

            }
        });

        String url = "https://blogmedia.testbook.com/blog/wp-content/uploads/2022/03/best-4000-smart-question-bank-ssc-quantitative-aptitude-in-english-next-generation-smartbook-by-testbook-and-s-chand-870bfcbb.pdf";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("REPORT");
        request.setTitle("Audit");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "AuditReport.ext");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

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