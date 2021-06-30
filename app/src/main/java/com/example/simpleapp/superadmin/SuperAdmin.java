package com.example.simpleapp.superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.admin.AdminLogin;
import com.example.simpleapp.admin.AdminProfile;
import com.example.simpleapp.superadmin.CreateFloor;
import com.example.simpleapp.superadmin.CreateHall;
import com.example.simpleapp.superadmin.CreateRoom;
import com.example.simpleapp.superadmin.Dashboard;
import com.example.simpleapp.superadmin.HallAdminAssign;
import com.example.simpleapp.superadmin.HallAdminRemove;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class SuperAdmin extends AppCompatActivity {

    Button mHallCreateButton, mFloorCreateButton, mRoomCreateButton, mHallAdminAssignButton, mDashboardButton,
            mHallAdminRemoveButton ;
    FloatingActionButton mSAdminProfileFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        mHallCreateButton=findViewById(R.id.hallCreateButton);
        mFloorCreateButton=findViewById(R.id.floorCreateButton);
        mRoomCreateButton=findViewById(R.id.roomCreateButton);
        mHallAdminAssignButton=findViewById(R.id.hallAdminAssignButton);
        mDashboardButton=findViewById(R.id.dashboardButton);
        mHallAdminRemoveButton=findViewById(R.id.hallAdminRemoveButton);
        mSAdminProfileFAB=findViewById(R.id.sAdminProfileFAB);

        mHallCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateHall.class));
            }
        });

        mFloorCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateFloor.class));
            }
        });

        mRoomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateRoom.class));
            }
        });

        mHallAdminAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallAdminAssign.class));
            }
        });

        mDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });

        mHallAdminRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallAdminRemove.class));
            }
        });

        mSAdminProfileFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminProfile.class));
            }
        });
    }

    public void logoutSuperAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
        finish();
    }
}