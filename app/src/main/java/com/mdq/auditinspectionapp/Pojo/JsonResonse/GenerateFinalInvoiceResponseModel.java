package com.mdq.auditinspectionapp.Pojo.JsonResonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GenerateFinalInvoiceResponseModel implements Serializable {


    @SerializedName("response")
    @Expose
    public transient List<ResponseForFinalInvoice> response;

    public List<ResponseForFinalInvoice> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseForFinalInvoice> response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("status")
    @Expose
    public transient Integer status;
    @SerializedName("message")
    @Expose
    public transient  String message;
    @SerializedName("date")
    @Expose
    public  transient String date;

}
