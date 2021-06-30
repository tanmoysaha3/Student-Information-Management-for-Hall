package com.example.simpleapp.adapter;

public class ModelStudent {
    String StudentID;
    String Full_Name;
    String Address;
    String Unique_Seat_Id;

    ModelStudent(){

    }
    ModelStudent(String studentID, String full_Name, String address, String unique_Seat_Id){
        this.StudentID=studentID;
        this.Full_Name=full_Name;
        this.Address=address;
        this.Unique_Seat_Id=unique_Seat_Id;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUnique_Seat_Id() {
        return Unique_Seat_Id;
    }

    public void setUnique_Seat_Id(String unique_Seat_Id) {
        Unique_Seat_Id = unique_Seat_Id;
    }
}
