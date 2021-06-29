package com.example.simpleapp.HallAdmin;

public class HallAdminHelperClass {
    String fullname,email,password,designation, department, assignedhall,phoneno;

    public HallAdminHelperClass(){

    }

    public HallAdminHelperClass(String fullname, String email, String password, String designation, String department, String assignedhall, String phoneno) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.designation = designation;
        this.department = department;
        this.assignedhall = assignedhall;
        this.phoneno = phoneno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAssignedhall() {
        return assignedhall;
    }

    public void setAssignedhall(String assignedhall) {
        this.assignedhall = assignedhall;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
