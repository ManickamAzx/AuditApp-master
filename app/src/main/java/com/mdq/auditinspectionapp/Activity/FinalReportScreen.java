package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.mdq.auditinspectionapp.databinding.ActivityFinalReportScreenBindingImpl;
import com.mdq.auditinspectionapp.enums.MessageViewType;
import com.mdq.auditinspectionapp.enums.ViewType;

public class FinalReportScreen extends AppCompatActivity implements GetInspectionReportResponseInterface , GetProductionResponseInterface {

    TextView name;
    PreferenceManager preferenceManager;
    ActivityFinalReportScreenBinding activityFinalReportScreenBinding;
    String orderType,from,to,vendor,customer,Seasonname,piNo,SourceName,OrderStatus,BRAND,who;
    GetInspectionViewModel getInspectionViewModel;
    GetProductionReportViewModel getProductionReportViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFinalReportScreenBinding=ActivityFinalReportScreenBinding.inflate(getLayoutInflater());
        setContentView(activityFinalReportScreenBinding.getRoot());
        getInspectionViewModel=new GetInspectionViewModel(this,this);
        getProductionReportViewModel=new GetProductionReportViewModel(this,this);

        name=findViewById(R.id.name);
        name.setText(getPreferenceManager().getPrefUsername());

        Intent intent = getIntent();

        orderType=intent.getStringExtra("orderType");
        from=intent.getStringExtra("from");
        to=intent.getStringExtra("to");
        vendor=intent.getStringExtra("vendor");
        customer=intent.getStringExtra("customer");
        Seasonname=intent.getStringExtra("Seasonname");
        piNo=intent.getStringExtra("piNo");
        SourceName=intent.getStringExtra("SourceName");
        OrderStatus=intent.getStringExtra("OrderStatus");
        BRAND=intent.getStringExtra("BRAND");
        who=intent.getStringExtra("who");

        if(who.equals("production")){
            getProductionReportViewModel.setAuth("Bearer "+getPreferenceManager().getPrefToken());
            getProductionReportViewModel.setBrand(BRAND);
            getProductionReportViewModel.setCustomer(customer);
            getProductionReportViewModel.setInvoiceNo(piNo);
            getProductionReportViewModel.setOrderStatus(OrderStatus);
            getProductionReportViewModel.setOrderType(orderType);
            getProductionReportViewModel.setVendor(vendor);
            getProductionReportViewModel.setSeasonName(Seasonname);
            getProductionReportViewModel.setFrom(from);
            getProductionReportViewModel.setTo(to);

            getProductionReportViewModel.getProductionReportCall();

        }else  if (who.equals("inspection")){
            getInspectionViewModel.setAuth("Bearer "+getPreferenceManager().getPrefToken());
            getInspectionViewModel.setBrand(BRAND);
            getInspectionViewModel.setCustomer(customer);
            getInspectionViewModel.setInvoiceNo(piNo);
            getInspectionViewModel.setOrderStatus(OrderStatus);
            getInspectionViewModel.setOrderType(orderType);
            getInspectionViewModel.setVendor(vendor);
            getInspectionViewModel.setSeasonName(Seasonname);
            getInspectionViewModel.setFrom(from);
            getInspectionViewModel.setTo(to);

            getInspectionViewModel.getInspectionReportCall();
        }


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

    }

    @Override
    public void getProductionReportCall(GetProductionReportResponseModel getProductionReportResponseModel) {

    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }
}