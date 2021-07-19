package com.example.simpleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.simpleapp.HallAdmin.HallAdminDashboard;
import com.example.simpleapp.HallOfficials.HallOfficialsDashboard;
import com.example.simpleapp.Student.StudentLogin;
import com.example.simpleapp.SuperAdmin.MainDashboard;


public class MainActivity extends AppCompatActivity{
    @Override
    public void onBackPressed() {

    }

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);

    }

    public void ClickMenu(View view){
        //open drawer
        opendrawer(drawerLayout);
    }

    public static void opendrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closedrawer(drawerLayout);
    }

    public static void closedrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome (View view){
        recreate();
    }

    public void ClickA (View view){
        redirectActivity(this, MainDashboard.class);
    }

    public void ClickB(View view){
        redirectActivity(this, HallAdminDashboard.class);

    }

    public void ClickLogout(View view){
        redirectActivity(this, HallOfficialsDashboard.class);

    }

    public void ClickStudent(View view){
        redirectActivity(this, StudentLogin.class);

    }



    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent=new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closedrawer(drawerLayout);
    }

}