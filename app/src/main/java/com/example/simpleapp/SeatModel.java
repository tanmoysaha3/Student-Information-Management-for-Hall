package com.example.simpleapp;

public class SeatModel {

    String Unique_Seat_Id;

    SeatModel(){

    }
    SeatModel(String uniqueSeatId){
        this.Unique_Seat_Id=uniqueSeatId;
    }

    public String getUniqueSeatId() {
        return Unique_Seat_Id;
    }

    public void setUniqueSeatId(String uniqueSeatId) {
        this.Unique_Seat_Id = uniqueSeatId;
    }
}
