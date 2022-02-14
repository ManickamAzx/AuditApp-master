package com.mdq.auditinspectionapp.Pojo.JsonResonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseForInvoice {

    @SerializedName("invoidNo")
    @Expose
    public String invoidNo;

    public String getInvoidNo() {
        return invoidNo;
    }

    public void setInvoidNo(String invoidNo) {
        this.invoidNo = invoidNo;
    }

    public String getInvoidDate() {
        return invoidDate;
    }

    public void setInvoidDate(String invoidDate) {
        this.invoidDate = invoidDate;
    }

    @SerializedName("invoidDate")
    @Expose
    public String invoidDate;

}
