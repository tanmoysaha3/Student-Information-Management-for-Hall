package com.example.simpleapp.Super_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.simpleapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SuperStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_student);

        BottomNavigationView bottomNavigationView = findViewById(R.id.sbottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.students);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.halladmins:
                        startActivity(new Intent(getApplicationContext(), SuperHallAdmin.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.students:
                        return true;

                    case R.id.personals:
                        startActivity(new Intent(getApplicationContext(), MainDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}