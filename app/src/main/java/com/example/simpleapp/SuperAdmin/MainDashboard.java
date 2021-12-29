package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.simpleapp.MainActivity;
import com.example.simpleapp.R;
import com.example.simpleapp.Student.StudentProfile;
import com.example.simpleapp.SuperAdmin.Screate.Hall;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

public class MainDashboard extends AppCompatActivity implements View.OnClickListener {
    Button mlcreatehalladmin,logoutButton,helpbutton1;
    CardView halladmincard, hallofficialcard, studentcard,createall;
    FirebaseAuth fAuth;
    ImageView mainprofileImage;
    StorageReference storageReference;
    TextView countstudent;
    DatabaseReference databaseReference;
    FirebaseUser user;
    int countstudents;



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
        helpbutton1=findViewById(R.id.helpbutton1);
        mainprofileImage= findViewById(R.id.profileImage);
        countstudent= findViewById(R.id.countstudent);
        

        halladmincard.setOnClickListener(this);
        hallofficialcard.setOnClickListener(this);
        studentcard.setOnClickListener(this);
        createall.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance().getReference("Student Accounts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    countstudents = (int) snapshot.getChildrenCount();
                    countstudent.setText(Integer.toString(countstudents));
                }
                else {
                    countstudent.setText(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mlcreatehalladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallAdminCreate.class));
            }
        });





        mainprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SuperAdminProfile.class));
            }
        });



        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("superadmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mainprofileImage);
            }
        });




    }
    public void superlogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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