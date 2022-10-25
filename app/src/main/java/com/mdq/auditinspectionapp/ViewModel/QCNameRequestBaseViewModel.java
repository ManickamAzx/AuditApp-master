package com.mdq.auditinspectionapp.ViewModel;

public class QCNameRequestBaseViewModel {

    private int dptid;

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    private String Auth;

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    private String dbname;

    public int getDptid() {
        return dptid;
    }

    public void setDptid(int dptid) {
        this.dptid = dptid;
    }
}
