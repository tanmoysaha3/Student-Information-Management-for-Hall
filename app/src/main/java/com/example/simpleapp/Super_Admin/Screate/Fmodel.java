package com.example.simpleapp.Super_Admin.Screate;

public class Fmodel {
    String Floorid;
    String Floorname;
    String Hallname;

    public Fmodel(){

    }

    public Fmodel(String floorid, String floorname, String hallname) {
        Floorid = floorid;
        Floorname = floorname;
        Hallname = hallname;
    }

    public String getFloorid() {
        return Floorid;
    }

    public void setFloorid(String floorid) {
        Floorid = floorid;
    }

    public String getFloorname() {
        return Floorname;
    }

    public void setFloorname(String floorname) {
        Floorname = floorname;
    }

    public String getHallname() {
        return Hallname;
    }

    public void setHallname(String hallname) {
        Hallname = hallname;
    }
}
