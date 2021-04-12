package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HallAdmin extends AppCompatActivity {

    Button mAddHallAdminButton, mSeeAllUser, mCreateHallButton, mCreateFloorButton, mCreateRoomButton,
            mCreateSeatButton, mSeeStuDetailsButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
=======

public class HallAdmin extends AppCompatActivity {

    Button mSeatCreateButton, mOfficialAssignButton, mSeatAssignButton, mSeeEmptyStudentsButton;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin);

<<<<<<< HEAD

        mCreateHallButton=findViewById(R.id.createHallButton);
        mCreateFloorButton=findViewById(R.id.createFloorButton);
        mCreateRoomButton=findViewById(R.id.createRoomButton);
        mCreateSeatButton=findViewById(R.id.createSeatButton);
        mSeeStuDetailsButton=findViewById(R.id.seeStuDetailsButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateHallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddHall.class));
            }
        });

        mCreateFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddFloor.class));
            }
        });

        mCreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddRoom.class));
            }
        });

        mCreateSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddSeat.class));
            }
        });

        mSeeStuDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeeStuDetails.class));
            }
        });

    }

    public void logoutHallAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

=======
        mSeatCreateButton=findViewById(R.id.seatCreateButton);
        mOfficialAssignButton=findViewById(R.id.officialAssignButton);
        mSeatAssignButton=findViewById(R.id.seatAssignButton);
        mSeeEmptyStudentsButton=findViewById(R.id.seeEmptyStudentsButton);

        mSeatCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateSeat.class));
            }
        });

        mOfficialAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OfficialAssign.class));
            }
        });

        mSeatAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeatAssign.class));
            }
        });
    }

    public void logoutAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
        finish();
    }
>>>>>>> origin/master
}