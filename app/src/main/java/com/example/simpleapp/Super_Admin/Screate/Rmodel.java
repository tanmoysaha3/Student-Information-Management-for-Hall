package com.example.simpleapp.Super_Admin.Screate;

public class Rmodel {

    String Roomid;
    String Roomname;
    String Hallname;
    String Floorname;
    String Floorid;

    public Rmodel(){

    }

    public Rmodel(String roomid, String roomname, String hallname, String floorname, String floorid) {
        Roomid = roomid;
        Roomname = roomname;
        Hallname = hallname;
        Floorname = floorname;
        Floorid = floorid;
    }

    public String getRoomid() {
        return Roomid;
    }

    public void setRoomid(String roomid) {
        Roomid = roomid;
    }

    public String getRoomname() {
        return Roomname;
    }

    public void setRoomname(String roomname) {
        Roomname = roomname;
    }

    public String getHallname() {
        return Hallname;
    }

    public void setHallname(String hallname) {
        Hallname = hallname;
    }

    public String getFloorname() {
        return Floorname;
    }

    public void setFloorname(String floorname) {
        Floorname = floorname;
    }

    public String getFloorid() {
        return Floorid;
    }

    public void setFloorid(String floorid) {
        Floorid = floorid;
    }
}
