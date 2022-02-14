package com.mdq.auditinspectionapp.Pojo.JsonResonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateUpdateProductionResponseModel {

    @SerializedName("response")
    @Expose
    public String response;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("date")
    @Expose
    public String date;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
