package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.Screate.Hall;
import com.google.firebase.auth.FirebaseAuth;

public class MainDashboard extends AppCompatActivity implements View.OnClickListener {
    Button mlcreatehalladmin,logoutButton;
    CardView halladmincard, hallofficialcard, studentcard, createhallcard, createfloorcard, createroomcard,createall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        mlcreatehalladmin=findViewById(R.id.createhalladminbutton);
        logoutButton=findViewById(R.id.logoutButton);
        halladmincard=findViewById(R.id.halladmincard);
        hallofficialcard=findViewById(R.id.hallofficialcard);
        studentcard=findViewById(R.id.studentcard);
        createall=findViewById(R.id.createall);

        halladmincard.setOnClickListener(this);
        hallofficialcard.setOnClickListener(this);
        studentcard.setOnClickListener(this);
        createall.setOnClickListener(this);


        mlcreatehalladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallAdminCreate.class));
            }
        });




    }
    public void superlogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), SuperLogin.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.halladmincard)
        {
            Intent intent = new Intent(com.example.simpleapp.SuperAdmin.MainDashboard.this, com.example.simpleapp.SuperAdmin.SHallAdmin.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.hallofficialcard)
        {
            Intent intent = new Intent(com.example.simpleapp.SuperAdmin.MainDashboard.this,SHallOfficials.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.studentcard)
        {
            Intent intent = new Intent(com.example.simpleapp.SuperAdmin.MainDashboard.this, com.example.simpleapp.SuperAdmin.SStudent.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.createall)
        {
            Intent intent = new Intent(com.example.simpleapp.SuperAdmin.MainDashboard.this,Hall.class);
            startActivity(intent);
        }

    }

}