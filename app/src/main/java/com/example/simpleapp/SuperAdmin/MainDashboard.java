package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.simpleapp.MainActivity;
import com.example.simpleapp.R;
import com.example.simpleapp.Student.StudentProfile;
import com.example.simpleapp.SuperAdmin.Screate.Hall;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user;

    SliderView sliderView;
    int[] images = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,};

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

        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


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