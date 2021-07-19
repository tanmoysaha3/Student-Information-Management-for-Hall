package com.example.simpleapp.SuperAdmin;

public class SHallAdminModel {

    String fullname,email,password,assignedhall;

    public SHallAdminModel(){

    }

    public SHallAdminModel(String fullname, String email, String password, String assignedhall) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.assignedhall = assignedhall;
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

    public String getAssignedhall() {
        return assignedhall;
    }

    public void setAssignedhall(String assignedhall) {
        this.assignedhall = assignedhall;
    }
}
