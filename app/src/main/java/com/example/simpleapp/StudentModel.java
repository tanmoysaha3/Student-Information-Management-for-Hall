package com.example.simpleapp;

public class StudentModel {
    String StudentID;

    StudentModel(){

    }
    StudentModel(String studentID){
        this.StudentID=studentID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
}
