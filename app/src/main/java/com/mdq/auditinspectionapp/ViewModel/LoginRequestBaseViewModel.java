package com.mdq.auditinspectionapp.ViewModel;

public class LoginRequestBaseViewModel {
    private String emp_id;
    private String password;

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String user_id) {
        this.emp_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
