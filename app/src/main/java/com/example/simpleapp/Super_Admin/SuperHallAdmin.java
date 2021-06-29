package com.example.simpleapp.Super_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.Screate.Floor;
import com.example.simpleapp.Super_Admin.Screate.Hall;
import com.example.simpleapp.Super_Admin.Screate.Room;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SuperHallAdmin extends AppCompatActivity  {
    Button mcardcreatehall, mcardcreatefloor, mcardcreateroom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_hall_admin);

        mcardcreatehall=findViewById(R.id.gotocreatehall);
        mcardcreatefloor=findViewById(R.id.gotocreatefloor);
        mcardcreateroom=findViewById(R.id.gotocreateroom);

        mcardcreatehall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Hall.class));
            }
        });

        mcardcreatefloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Floor.class));
            }
        });

        mcardcreateroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Room.class));
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.sbottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.halladmins);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.personals:
                        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.halladmins:
                        return true;

                    case R.id.students:
                        startActivity(new Intent(getApplicationContext(), SuperStudent.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }


}