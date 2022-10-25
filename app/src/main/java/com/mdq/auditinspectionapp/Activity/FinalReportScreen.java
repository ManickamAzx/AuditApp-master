package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.GetInspectionReportResponseInterface;
import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.GetProductionResponseInterface;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.ErrorBody;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GetInspectionReportResponseModel;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GetProductionReportResponseModel;
import com.mdq.auditinspectionapp.R;
import com.mdq.auditinspectionapp.Utils.PreferenceManager;
import com.mdq.auditinspectionapp.ViewModel.GetInspectionViewModel;
import com.mdq.auditinspectionapp.ViewModel.GetProductionReportViewModel;
import com.mdq.auditinspectionapp.databinding.ActivityFinalReportScreenBinding;
import com.mdq.auditinspectionapp.enums.MessageViewType;
import com.mdq.auditinspectionapp.enums.ViewType;

public class FinalReportScreen extends AppCompatActivity implements GetInspectionReportResponseInterface, GetProductionResponseInterface {

    TextView name;
    PreferenceManager preferenceManager;
    ActivityFinalReportScreenBinding activityFinalReportScreenBinding;
    String orderType, from, to, vendor, customer, Seasonname, piNo, SourceName, OrderStatus, BRAND, who;
    GetInspectionViewModel getInspectionViewModel;
    GetProductionReportViewModel getProductionReportViewModel;
    boolean download = false;
    GetInspectionReportResponseModel getInspectionReportResponseModel;
    GetProductionReportResponseModel getProductionReportResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFinalReportScreenBinding = ActivityFinalReportScreenBinding.inflate(getLayoutInflater());
        setContentView(activityFinalReportScreenBinding.getRoot());
        getInspectionViewModel = new GetInspectionViewModel(this, this);
        getProductionReportViewModel = new GetProductionReportViewModel(this, this);

        name = findViewById(R.id.name);
        name.setText(getPreferenceManager().getPrefUsername());

        Intent intent = getIntent();

        orderType = intent.getStringExtra("orderType");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        vendor = intent.getStringExtra("vendor");
        customer = intent.getStringExtra("customer");
        Seasonname = intent.getStringExtra("Seasonname");
        piNo = intent.getStringExtra("piNo");
        SourceName = intent.getStringExtra("SourceName");
        OrderStatus = intent.getStringExtra("OrderStatus");
        BRAND = intent.getStringExtra("BRAND");
        who = intent.getStringExtra("who");

        if (who.equals("production")) {
            getProductionReportViewModel.setAuth("Bearer " + getPreferenceManager().getPrefToken());
            getProductionReportViewModel.setBrand(BRAND);
            getProductionReportViewModel.setCustomer(customer);
            getProductionReportViewModel.setInvoiceNo(piNo);
            getProductionReportViewModel.setOrderStatus(OrderStatus);
            getProductionReportViewModel.setOrderType(orderType);
            getProductionReportViewModel.setVendor(vendor);
            getProductionReportViewModel.setSeasonName(Seasonname);
            getProductionReportViewModel.setFrom(from);
            getProductionReportViewModel.setTo(to);
            getProductionReportViewModel.setDbname(getPreferenceManager().getPrefDbname());

            getProductionReportViewModel.getProductionReportCall();

        } else if (who.equals("inspection")) {
            getInspectionViewModel.setAuth("Bearer " + getPreferenceManager().getPrefToken());
            getInspectionViewModel.setBrand(BRAND);
            getInspectionViewModel.setCustomer(customer);
            getInspectionViewModel.setInvoiceNo(piNo);
            getInspectionViewModel.setOrderStatus(OrderStatus);
            getInspectionViewModel.setOrderType(orderType);
            getInspectionViewModel.setVendor(vendor);
            getInspectionViewModel.setSeasonName(Seasonname);
            getInspectionViewModel.setFrom(from);
            getInspectionViewModel.setTo(to);
            getInspectionViewModel.setDbname(getPreferenceManager().getPrefDbname());

            getInspectionViewModel.getInspectionReportCall();
        }
        activityFinalReportScreenBinding.SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (download) {
                    if (who.equals("inspection")) {
                        download(getInspectionReportResponseModel.getLink());
                    } else if (who.equals("production")) {
                        download(getProductionReportResponseModel.getLink());
                    }
                }
            }
        });
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

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void GetInspectionReportProcess(GetInspectionReportResponseModel getInspectionReportResponseModel) {

        if (!getInspectionReportResponseModel.getLink().isEmpty()) {
            download = true;
            this.getInspectionReportResponseModel = getInspectionReportResponseModel;
            activityFinalReportScreenBinding.textView22.setText("Data found");
            activityFinalReportScreenBinding.textView22.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.SAVE.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.fetching.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.progress.setVisibility(View.INVISIBLE);
        } else {
            activityFinalReportScreenBinding.textView22.setText("Data not found");
            activityFinalReportScreenBinding.textView22.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.SAVE.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.fetching.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getProductionReportCall(GetProductionReportResponseModel getProductionReportResponseModel) {

        if (!getProductionReportResponseModel.getLink().isEmpty()) {
            this.getProductionReportResponseModel = getProductionReportResponseModel;
            download = true;
            activityFinalReportScreenBinding.textView22.setText("Data found");
            activityFinalReportScreenBinding.textView22.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.SAVE.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.fetching.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.progress.setVisibility(View.INVISIBLE);

        } else {
            activityFinalReportScreenBinding.textView22.setText("Data not found");
            activityFinalReportScreenBinding.textView22.setVisibility(View.VISIBLE);
            activityFinalReportScreenBinding.SAVE.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.fetching.setVisibility(View.INVISIBLE);
            activityFinalReportScreenBinding.progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
    }

    private void download(String link) {
        String url = link.trim();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("REPORT");
        request.setTitle("Audit");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "AuditReport.pdf");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}