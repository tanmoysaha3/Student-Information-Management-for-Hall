package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.HallAdmin.HallAdminDashboard;
import com.example.simpleapp.HallAdmin.HallAdminLogin;
import com.example.simpleapp.HallOfficials.HallOfficialDashboard;
import com.example.simpleapp.HallOfficials.HallOfficialLogin;
import com.example.simpleapp.Students.StudentDashboard;
import com.example.simpleapp.Students.StudentLogin;
import com.example.simpleapp.Super_Admin.MainDashboard;
import com.example.simpleapp.Super_Admin.SuperLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        redirectActivity(this,MainDashboard.class);
    }

    public void ClickB(View view){
        redirectActivity(this,HallAdminDashboard.class);

    }

    public void ClickLogout(View view){
        redirectActivity(this,HallOfficialDashboard.class);

    }

    public void ClickStudent(View view){
        redirectActivity(this,StudentDashboard.class);

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