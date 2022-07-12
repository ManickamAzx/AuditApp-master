package com.mdq.auditinspectionapp.Pojo.JsonResonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateLoginResponseModel {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("data")
    @Expose
    public List<ResponseForLogin> data;

    @SerializedName("status")
    @Expose
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResponseForLogin> getData() {
        return data;
    }

    public void setData(List<ResponseForLogin> data) {
        this.data = data;
    }

    @SerializedName("message")
    @Expose
    public String message;


}

