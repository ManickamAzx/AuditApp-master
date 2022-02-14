package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.FinalInvoiceResponseInterface;
import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.QCNameResponseInterface;
import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.QCResultResponseInterface;
import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.ShipModeResponseInterface;
import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.UpdateInspectionResponseInterface;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.ErrorBody;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateFinalInvoiceResponseModel;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateQCNameResponseModel;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateQCResultResponseModel;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateShipModeResponseModel;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateUpdateInspectionResponseModel;
import com.mdq.auditinspectionapp.R;
import com.mdq.auditinspectionapp.Utils.ApiClass;
import com.mdq.auditinspectionapp.ViewModel.FinalInvoiceViewModel;
import com.mdq.auditinspectionapp.ViewModel.QCNameRequestViewModel;
import com.mdq.auditinspectionapp.ViewModel.QCResultRequestViewModel;
import com.mdq.auditinspectionapp.ViewModel.ShipModeRequestViewModel;
import com.mdq.auditinspectionapp.ViewModel.UpdateInspectionRequestViewModel;
import com.mdq.auditinspectionapp.databinding.ActivityFinalInspectionUpdateBinding;
import com.mdq.auditinspectionapp.databinding.ActivityFinalProductionScreenBinding;
import com.mdq.auditinspectionapp.enums.MessageViewType;
import com.mdq.auditinspectionapp.enums.ViewType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FinalInspectionScreen extends AppCompatActivity implements FinalInvoiceResponseInterface, QCNameResponseInterface, QCResultResponseInterface, UpdateInspectionResponseInterface {
    LinearLayout back;
    ImageView datepicker;
    TextView textView;
    GenerateFinalInvoiceResponseModel generateFinalInvoiceRequestModel;
    ActivityFinalInspectionUpdateBinding ap;
    FinalInvoiceViewModel finalInvoiceViewModel;
    GenerateFinalInvoiceResponseModel generateFinalInvoiceResponseModel;
    String piNo;
    String from,to,orderStatus,SourceFlag;int SourceId,getId=0;
    ArrayAdapter<String> arrayAdapterQCResult,ArrayAdapterQCName;
    String[] QCResultArray,QCNameArray;
    int QCResultId=0,QCNameId=0;
    int dpid=0;
    QCResultRequestViewModel qcResultRequestViewModel;
    QCNameRequestViewModel qcNameRequestViewModel;
    UpdateInspectionRequestViewModel updateInspectionRequestViewModel;
    GenerateQCNameResponseModel generateQCNameResponseModel;
    GenerateQCResultResponseModel generateQCResultResponseModel;
    List<String> customerorderno=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap=ActivityFinalInspectionUpdateBinding.inflate(getLayoutInflater());
        setContentView(ap.getRoot());

        updateInspectionRequestViewModel=new UpdateInspectionRequestViewModel(getApplicationContext(),this);
        finalInvoiceViewModel=new FinalInvoiceViewModel(getApplicationContext(),this);
        back=findViewById(R.id.linearBack);
        datepicker=findViewById(R.id.date_picker);
        textView=findViewById(R.id.datetext);

        Intent intent=getIntent();
        dpid=intent.getIntExtra("dpid",0);

        ApiClass apiClass=new ApiClass();
        qcNameRequestViewModel=new QCNameRequestViewModel(getApplicationContext(),this);
        qcResultRequestViewModel=new QCResultRequestViewModel(getApplicationContext(),this);

        if(dpid!=0){
            qcNameRequestViewModel.setDptid(dpid);
            apiClass.QCNAME=apiClass.QCNAME+dpid;
            Log.i("wded",""+apiClass.QCNAME);
            qcNameRequestViewModel.generateQCNameRequest();
            qcResultRequestViewModel.generateQCResultRequest();
        }
        ap.QCNAME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ap.QCNAME.showDropDown();
            }
        });
        ap.QCRESULT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ap.QCRESULT.showDropDown();
            }
        });

        ap.QCRESULT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QCResultId=position;
            }
        });
        ap.QCNAME.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QCNameId=position;
            }
        });

        generateFinalInvoiceResponseModel=new GenerateFinalInvoiceResponseModel();
        //making status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setClick();

        if(getId==0){
            ap.PREV.setClickable(false);
        }

        else{
            ap.PREV.setClickable(true);
        }

        if(generateFinalInvoiceResponseModel.getResponse()!=null) {
            if (getId == generateFinalInvoiceResponseModel.getResponse().size()) {
                ap.NEXT.setClickable(false);
            } else {
                ap.NEXT.setClickable(true);
            }
        }

        ap.PREV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId=getId-1;
                FinalInvoice(generateFinalInvoiceResponseModel,2);
//                ap.PREV.setClickable(false);
            }
        });

        ap.NEXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId = getId + 1;
                FinalInvoice(generateFinalInvoiceResponseModel, 1);
//                        ap.NEXT.setClickable(false);
            }
        });

        ap.SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ap.datetext.getText().toString()!=null) {
                    customerorderno = Collections.singletonList(generateFinalInvoiceResponseModel.getResponse().get(getId).getCustOrderNo());
                    updateInspectionRequestViewModel.inspectionDate = ap.datetext.getText().toString();
                    updateInspectionRequestViewModel.qcRemarks = ap.QCREMARKS.getText().toString();
                    updateInspectionRequestViewModel.customerOrderNos = customerorderno;
                    updateInspectionRequestViewModel.pgmCode = generateFinalInvoiceResponseModel.getResponse().get(getId).getPgmCode();
                    updateInspectionRequestViewModel.result = ap.QCRESULT.getText().toString();
                    updateInspectionRequestViewModel.sourceFlag = generateFinalInvoiceResponseModel.getResponse().get(getId).getSourceFlag();
                    updateInspectionRequestViewModel.sourceId = generateFinalInvoiceResponseModel.getResponse().get(getId).getSourceId();
                    updateInspectionRequestViewModel.sourceId = generateFinalInvoiceResponseModel.getResponse().get(getId).getSourceId();
                    updateInspectionRequestViewModel.styleId = Integer.parseInt(generateFinalInvoiceResponseModel.getResponse().get(getId).getStyleId());
                    updateInspectionRequestViewModel.qcBy = generateQCNameResponseModel.getResponse().get(QCNameId).getEmpNo();
                    updateInspectionRequestViewModel.generateUpdateInspectionRequest();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Select Inspection Date", Toast.LENGTH_SHORT).show();
                }
            }
        });


        piNo=intent.getStringExtra("piNo");
        from=intent.getStringExtra("from");
        to=intent.getStringExtra("to");
        SourceId=intent.getIntExtra("SourceId",0);
        SourceFlag=intent.getStringExtra("SourceFlag");
        orderStatus=intent.getStringExtra("OrderStatus");
        if(!piNo.isEmpty() && !from.isEmpty() && !to.isEmpty() && !SourceFlag.isEmpty() && SourceId!=0 && !orderStatus.isEmpty()){

            finalInvoiceViewModel.setPiNo(piNo);
            finalInvoiceViewModel.setFrom(from);
            finalInvoiceViewModel.setTo(to);
            finalInvoiceViewModel.setSourceFlag(SourceFlag);
            finalInvoiceViewModel.setSourceId(SourceId);
            finalInvoiceViewModel.setOrderStatus(orderStatus);
            finalInvoiceViewModel.generateFinalVoiceRequest();
            ap.outForeUntil.setText(to);
            ap.piNo.setText(piNo);

        }
    }

    private void setClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            Calendar calendar=Calendar.getInstance();
            int years=calendar.get(Calendar.YEAR);
            int months=calendar.get(Calendar.MONTH);
            int days=calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog=new DatePickerDialog(FinalInspectionScreen.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month=month+1;
                    String mm= String.valueOf(month);
                    String dd= String.valueOf(dayOfMonth);
                    if(mm.length()==1){
                        mm="0"+mm;
                    }if(dd.length()==1){
                        dd="0"+dd;
                    }
                    String dates=year+"-"+mm+"-"+dd;
                    ap.datetext.setText(dates);
                }
            },years,months,days);
            int positiveColor = ContextCompat.getColor(FinalInspectionScreen.this, R.color.black);
            int negativeColor = ContextCompat.getColor(FinalInspectionScreen.this, R.color.black);

                    datePickerDialog.show();
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(positiveColor);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(negativeColor);

        }
        });
    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, String errorMessage) {

    }

    @Override
    public void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage) {

    }

    @Override
    public void generateFinalInvoiceProcessed(GenerateFinalInvoiceResponseModel generateFinalInvoiceResponseModel) {

        ap.PREV.setClickable(true);
        ap.NEXT.setClickable(true);
        if(generateFinalInvoiceResponseModel!=null){
            FinalInvoice(generateFinalInvoiceResponseModel,0);
            this.generateFinalInvoiceResponseModel=generateFinalInvoiceResponseModel;
        }
    }

    private void FinalInvoice(GenerateFinalInvoiceResponseModel generateFinalInvoiceResponseModel,int i) {

        if(i==1){
            Log.i("i",""+i);
            ap.NEXT.setClickable(true);
            if (ap.NEXT.isClickable() == true) {
                Log.i("MyButton", "Clickable is true");

            }

        }
        else if(i==2){
            Log.i("i",""+i);
            ap.PREV.setClickable(true);
        }

        try {
            if (!generateFinalInvoiceResponseModel.getResponse().isEmpty()) {
                if (!generateFinalInvoiceResponseModel.getResponse().get(getId).getBuyerEtd().isEmpty()) {
                    ap.piDate.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getBuyerEtd());
                }else
                {
                    ap.piDate.setText("NO DATA");
                }
//        ap.piDate.setText("FAI21220037HAR");
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getBuyerPo() != null) {
                    ap.BUYER.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getBuyerPo());
                }else{
                    ap.Buyer.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getbrandId() != null) {
                    ap.Buyer.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getbrandId());
                }else{
                    ap.Buyer.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getOrderQty() != null) {

                    ap.ORDERQTY.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getOrderQty());
                }else{
                    ap.ORDERQTY.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getBalance() != null) {

                    ap.BALANCE.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getBalance());
                }else{
                    ap.BALANCE.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getVendorDelDate() != null) {

                    ap.VENDORDELDATE.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getVendorDelDate());
                }else{
                    ap.VENDORDELDATE.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getForecastDelDate() != null) {

                    ap.FORECASTDELDATE.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getForecastDelDate());

                }else{
                    ap.FORECASTDELDATE.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getCityName() != null) {

                    ap.CITY.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getCityName());
                }else{
                    ap.CITY.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getDespatchModeId() != null) {

                    ap.dispatch.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getDespatchModeId());
                }else{
                    ap.dispatch.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getRemarks() != null) {
                    ap.REMARKS.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getRemarks());
                }else{
                    ap.REMARKS.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getInspectionDate() != null) {

                    ap.datetext.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getInspectionDate());
                }else{
                    ap.datetext.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getQcBy() != null) {

//                    ap.QCNAME.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getQcBy());
                }else{
//                    ap.QCNAME.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getQcRemarks() != null) {

                    ap.QCREMARKS.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getQcRemarks());
                }else{
                    ap.QCREMARKS.setText("NO DATA");
                }
                if (generateFinalInvoiceResponseModel.getResponse().get(getId).getStyleName() != null) {

                    ap.STYLENAME.setText(generateFinalInvoiceResponseModel.getResponse().get(getId).getStyleName());

                }else {
                    ap.STYLENAME.setText("NO DATA");
                }
            }else{
                logout();
            }
        }
        catch (Exception e){

        }
    }

    private void logout() {
        Dialog dialoglogout = new Dialog(this, R.style.dialog_center);
        dialoglogout.setCanceledOnTouchOutside(false);
        dialoglogout.setContentView(R.layout.logout);
        dialoglogout.show();
        TextView textView23 = dialoglogout.findViewById(R.id.textView23);


        DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent KEvent) {
                int keyaction = KEvent.getAction();

                if(keyaction == KeyEvent.ACTION_DOWN)
                {
                    Intent intent=new Intent(getApplicationContext(), TransactionProductUpdate.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        };
        dialoglogout.setOnKeyListener(keylistener );

        textView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoglogout.dismiss();

                Intent intent=new Intent(getApplicationContext(), TransactionProductUpdate.class);
                startActivity(intent);
                finish();
            }

        });

    }

    @Override
    public void generateQCNameProcessed(GenerateQCNameResponseModel generateQCNameResponseModel) {

        if(!generateQCNameResponseModel.getResponse().isEmpty()){
            this.generateQCNameResponseModel=generateQCNameResponseModel;
            QCNameArray=new String[generateQCNameResponseModel.getResponse().size()];
            for (int i=0;i<generateQCNameResponseModel.getResponse().size();i++){
                QCNameArray[i]=generateQCNameResponseModel.getResponse().get(i).getEmpNo().trim()+generateQCNameResponseModel.getResponse().get(i).getEmpName().trim();
            }

            ArrayAdapterQCName=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,QCNameArray);
            ap.QCNAME.setText(QCNameArray[0]);
            ap.QCNAME.setAdapter(ArrayAdapterQCName);
        }
        else{
            ap.QCNAME.setText("NO DATA");

        }
    }

    @Override
    public void generateQCResultProcessed(GenerateQCResultResponseModel generateQCResultResponseModel) {
        if(generateQCResultResponseModel.getResponse()!=null){
            this.generateQCResultResponseModel =generateQCResultResponseModel;
            QCResultArray=new String[generateQCResultResponseModel.getResponse().size()];
            for (int i=0;i<generateQCResultResponseModel.getResponse().size();i++) {
                QCResultArray[i] = generateQCResultResponseModel.getResponse().get(i).getResultName().trim();
            }

            arrayAdapterQCResult=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,QCResultArray);
            ap.QCRESULT.setText(QCResultArray[0]);
            ap.QCRESULT.setAdapter(arrayAdapterQCResult);

        }
    }

    @Override
    public void generateUpdateInspectionProcessed(GenerateUpdateInspectionResponseModel generateUpdateInspectionResponseModel) {
        Toast.makeText(getApplicationContext(), ""+generateUpdateInspectionResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {

    }

}
