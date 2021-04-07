package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Seat extends AppCompatActivity {

    EditText mSeatHallId, mSeatFloorNo, mSeatRoomNo, mSeatId;
    Button mCreateNewSeatButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        mSeatHallId=findViewById(R.id.seatHallId);
        mSeatFloorNo=findViewById(R.id.seatFloorNo);
        mSeatRoomNo=findViewById(R.id.seatRoomNo);
        mSeatId=findViewById(R.id.seatId);
        mCreateNewSeatButton=findViewById(R.id.createNewSeatButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateNewSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hallId=mSeatHallId.getText().toString();
                String floorNo=mSeatFloorNo.getText().toString();
                String roomNo=mSeatRoomNo.getText().toString();
                String seatId=mSeatId.getText().toString();

                DocumentReference documentReference=fStore.collection("Hall").document(hallId).collection("Floors").document(floorNo)
                        .collection("Rooms").document(roomNo).collection("Seats").document(seatId);
                Map<String,Object> user = new HashMap<>();
                user.put("Date", Timestamp.now());

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + userID);
                        startActivity(new Intent(getApplicationContext(),Seat.class));
                    }
                });
            }
        });
    }
}