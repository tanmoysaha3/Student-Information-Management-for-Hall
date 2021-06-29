package com.example.simpleapp.Super_Admin;

public class SuperAdminHelperClass {
    public String fullname;
    String email;
    String password;
    String district;
    String presentaddress;
    String bloodgroup;
    String riligion;
    String phoneno;
    public String department;
    String rollno;
    String batch;
    public String year;
    String hallname;
    String floorname;
    String roomno;
    String seatno;

    public SuperAdminHelperClass(){

    }

    public SuperAdminHelperClass(String fullname, String email, String password, String district, String presentaddress, String bloodgroup, String riligion, String phoneno, String department, String rollno, String batch, String year, String hallname, String floorname, String roomno, String seatno) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.district = district;
        this.presentaddress = presentaddress;
        this.bloodgroup = bloodgroup;
        this.riligion = riligion;
        this.phoneno = phoneno;
        this.department = department;
        this.rollno = rollno;
        this.batch = batch;
        this.year = year;
        this.hallname = hallname;
        this.floorname = floorname;
        this.roomno = roomno;
        this.seatno = seatno;
    }

    public SuperAdminHelperClass(String hallname, String floorname, String roomname, String seatname, String rollno) {
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPresentaddress() {
        return presentaddress;
    }

    public void setPresentaddress(String presentaddress) {
        this.presentaddress = presentaddress;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getRiligion() {
        return riligion;
    }

    public void setRiligion(String riligion) {
        this.riligion = riligion;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getSeatno() {
        return seatno;
    }

    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }
}
