package com.mdq.auditinspectionapp.Pojo.JsonResonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductionReportResponseModel {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

    public List<ResponseForGetProductionReport> getDetails() {
        return details;
    }

    public void setDetails(List<ResponseForGetProductionReport> details) {
        this.details = details;
    }

    @SerializedName("details")
    @Expose
    public List<ResponseForGetProductionReport> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
