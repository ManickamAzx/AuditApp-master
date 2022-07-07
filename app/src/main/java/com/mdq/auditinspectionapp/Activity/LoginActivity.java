package com.mdq.auditinspectionapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdq.auditinspectionapp.Interfaces.ViewResponceInterface.LoginResponseInterface;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.ErrorBody;
import com.mdq.auditinspectionapp.Pojo.JsonResonse.GenerateLoginResponseModel;
import com.mdq.auditinspectionapp.R;
import com.mdq.auditinspectionapp.Utils.PreferenceManager;
import com.mdq.auditinspectionapp.ViewModel.LoginRequestViewModel;
import com.mdq.auditinspectionapp.databinding.ActivityMainBinding;
import com.mdq.auditinspectionapp.enums.MessageViewType;
import com.mdq.auditinspectionapp.enums.ViewType;

public class LoginActivity extends AppCompatActivity implements LoginResponseInterface {

    TextView forgot;
    CardView cardView;
    ActivityMainBinding activityMainBinding;
    LoginRequestViewModel loginRequestViewModel;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterForDB;
    PreferenceManager preferenceManager;
    String[] DBList=new String[]{"WMTEST","FAImpex","FAINEW2019","WindmillLive"};
    int i=0,dpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        autoCompleteTextView=findViewById(R.id.autoComplete);
        //making status bar color as transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        forgot=findViewById(R.id.Forgot);
        cardView=findViewById(R.id.cardView);
        forgot.setPaintFlags(forgot.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        adapterForDB=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,DBList);
        autoCompleteTextView.setText(adapterForDB.getItem(0).toString());
        autoCompleteTextView.setAdapter(adapterForDB);

        activityMainBinding.CardForHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    i=1;
                }else{
                    i=0;
                }
            }
        });
        loginRequestViewModel=new LoginRequestViewModel(getApplicationContext(),this);
        activityMainBinding.logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid,password;
                userid=activityMainBinding.UserId.getText().toString();
                if(i==1){
                    Toast.makeText(getApplicationContext(), "To be done.", Toast.LENGTH_SHORT).show();
                }else {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                            .getSystemService(Context.CONNECTIVITY_SERVICE);

                    if ((connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                            || (connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                            .getState() == NetworkInfo.State.CONNECTED)) {
                    if (!activityMainBinding.UserId.getText().toString().isEmpty() && !activityMainBinding.password.getText().toString().isEmpty()) {
                        loginRequestViewModel.setEmp_id(activityMainBinding.UserId.getText().toString());
                        loginRequestViewModel.setPassword(activityMainBinding.password.getText().toString());
                        loginRequestViewModel.generateLoginRequest();
                        activityMainBinding.Progress.bringToFront();
                        activityMainBinding.Progress.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Require all fields", Toast.LENGTH_SHORT).show();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "This App Require Internet", Toast.LENGTH_SHORT).show();
                    }
                }
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
    public void generateLoginProcessed(GenerateLoginResponseModel generateLoginResponseModel) {
        String msg="Logged in successfully";
        String message=generateLoginResponseModel.getMessage().toString();
        activityMainBinding.Progress.setVisibility(View.GONE);
       if(message.equals(msg)){
           dpid= Integer.parseInt(generateLoginResponseModel.getData().get(0).getDepartmentId());
               getPreferenceManager().setPrefToken(generateLoginResponseModel.getToken());
               Intent intent=new Intent(LoginActivity.this, welcomeSaibhavani.class);
               intent.putExtra("name",generateLoginResponseModel.getData().get(0).getName());
               intent.putExtra("dpid",dpid);
               startActivity(intent);
               finishAffinity();
               preferenceManager.setPrefUsername(generateLoginResponseModel.getData().get(0).getName());
               preferenceManager.setPrefDpid(Integer.parseInt(generateLoginResponseModel.getData().get(0).getDepartmentId().toString()));
               Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
        activityMainBinding.Progress.setVisibility(View.GONE);

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