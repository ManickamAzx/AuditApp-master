package com.mdq.auditinspectionapp.Utils;

public class ApiClass {

    public ApiClass(){ }

//    public static final String BASE_URL = "http://13.233.58.19/my-api/";
    public static final String BASE_URL = "https://demo.azonix.in:10555/auditapp/";
    public static final String LOGIN = "account/login";
    public static final String SOURCE = "master/get-sources";
    public static final String SEASON = "master/get-seasons";
    public static final String BRAND = "master/get-brands";
    public static final String SUPPLIER = "master/get-suppliers";
    public static final String INVOICE = "invoice/get-invoice-list";
    public static final String FINALINVOICE = "invoice/get-invoice";
    public static final String SHIPMODE= "invoice/get-shipment-modes";
    public static final String QCRESULT= "invoice/get-qcresult-list";
    public static String QCNAME= "invoice/get-qcname-list/";
    public static String UPDATEPRODUCTION= "invoice/update-productiondetails";
    public static String UPDATEINSPECTION= "invoice/update-inspectiondetails";


}
