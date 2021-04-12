package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SuperAdmin extends AppCompatActivity {

    Button mHallCreateButton, mFloorCreateButton, mRoomCreateButton, mHallAdminAssignButton, mDashboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        mHallCreateButton=findViewById(R.id.hallCreateButton);
        mFloorCreateButton=findViewById(R.id.floorCreateButton);
        mRoomCreateButton=findViewById(R.id.roomCreateButton);
        mHallAdminAssignButton=findViewById(R.id.hallAdminAssignButton);
        mDashboardButton=findViewById(R.id.dashboardButton);

        mHallCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateHall.class));
            }
        });

        mFloorCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateFloor.class));
            }
        });

        mRoomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateRoom.class));
            }
        });

        mHallAdminAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HallAdminAssign.class));
            }
        });
    }

    public void logoutSuperAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
        finish();
    }
}