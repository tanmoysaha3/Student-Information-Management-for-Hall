package com.example.simpleapp.HallAdmin;

public class Smodel {

    String Seatid;
    String Seatname,assignedstudentid;
    String Hallname;
    String Floorname;
    String Roomname;
    String Roomid;
    String Floorid;

    public Smodel(){

    }

    public Smodel(String seatid, String seatname, String assignedstudentid, String hallname, String floorname, String roomname, String roomid, String floorid) {
        Seatid = seatid;
        Seatname = seatname;
        this.assignedstudentid = assignedstudentid;
        Hallname = hallname;
        Floorname = floorname;
        Roomname = roomname;
        Roomid = roomid;
        Floorid = floorid;
    }

    public String getSeatid() {
        return Seatid;
    }

    public void setSeatid(String seatid) {
        Seatid = seatid;
    }

    public String getSeatname() {
        return Seatname;
    }

    public void setSeatname(String seatname) {
        Seatname = seatname;
    }

    public String getAssignedstudentid() {
        return assignedstudentid;
    }

    public void setAssignedstudentid(String assignedstudentid) {
        this.assignedstudentid = assignedstudentid;
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

    public String getRoomname() {
        return Roomname;
    }

    public void setRoomname(String roomname) {
        Roomname = roomname;
    }

    public String getRoomid() {
        return Roomid;
    }

    public void setRoomid(String roomid) {
        Roomid = roomid;
    }

    public String getFloorid() {
        return Floorid;
    }

    public void setFloorid(String floorid) {
        Floorid = floorid;
    }
}