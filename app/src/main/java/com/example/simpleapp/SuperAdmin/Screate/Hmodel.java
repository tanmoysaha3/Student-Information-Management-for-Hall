package com.example.simpleapp.SuperAdmin.Screate;

public class Hmodel {
    String Hallid;
    String Hallname;
    String Halltype;
    String Assignedhalladmin;
    String Assignedhallofficials;

    public Hmodel(){

    }

    public Hmodel(String hallid, String hallname, String halltype, String assignedhalladmin, String assignedhallofficials) {
        Hallid = hallid;
        Hallname = hallname;
        Halltype = halltype;
        Assignedhalladmin = assignedhalladmin;
        Assignedhallofficials = assignedhallofficials;
    }

    public String getHallid() {
        return Hallid;
    }

    public void setHallid(String hallid) {
        Hallid = hallid;
    }

    public String getHallname() {
        return Hallname;
    }

    public void setHallname(String hallname) {
        Hallname = hallname;
    }

    public String getHalltype() {
        return Halltype;
    }

    public void setHalltype(String halltype) {
        Halltype = halltype;
    }

    public String getAssignedhalladmin() {
        return Assignedhalladmin;
    }

    public void setAssignedhalladmin(String assignedhalladmin) {
        Assignedhalladmin = assignedhalladmin;
    }

    public String getAssignedhallofficials() {
        return Assignedhallofficials;
    }

    public void setAssignedhallofficials(String assignedhallofficials) {
        Assignedhallofficials = assignedhallofficials;
    }
}
