package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HallAdmin extends AppCompatActivity {

    Button mSeatCreateButton, mOfficialAssignButton, mSeatAssignButton, mSeeEmptyStudentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin);

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
}