package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends AppCompatActivity {

    Button mAddSAdminButton, mSeeAllUser, mSeeStuDetailsButton, mCreateUniqueSeatButton, mSeeEmptySeatsButton,
            mAdminProfileButton, mSeeAssignedSeatsButton, mSeeAssignedStudentsButton, mSeeEmptyStudentsButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAdminProfileButton=findViewById(R.id.adminProfileButton);
        mCreateUniqueSeatButton=findViewById(R.id.createUniqueSeatButton);
        mSeeEmptySeatsButton=findViewById(R.id.seeEmptySeatsButton);
        mSeeAssignedSeatsButton=findViewById(R.id.seeAssignedSeatsButton);
        mSeeAssignedStudentsButton=findViewById(R.id.seeAssignedStudentsButton);
        mSeeEmptyStudentsButton=findViewById(R.id.seeEmptyStudentsButton);
        mSeeStuDetailsButton=findViewById(R.id.seeStuDetailsButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateUniqueSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddUniqueSeat.class));
            }
        });

        mSeeEmptySeatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeeEmptySeats.class));
            }
        });

        mSeeAssignedSeatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeeAssignedSeats.class));
            }
        });

        mSeeAssignedStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeeAssignedStudents.class));
            }
        });

        mSeeEmptyStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SeeEmptyStudents.class));
            }
        });

        mAdminProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminProfile.class));
            }
        });
    }

    public void logoutAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
        finish();
    }
}