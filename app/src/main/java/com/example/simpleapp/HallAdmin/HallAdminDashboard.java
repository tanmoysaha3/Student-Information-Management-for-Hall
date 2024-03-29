package com.example.simpleapp.HallAdmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.simpleapp.HallAdmin.Create.HRoom1;
import com.example.simpleapp.HallAdmin.Create.HSeat;
import com.example.simpleapp.HallAdmin.StudentAssign.Assignstudent;
import com.example.simpleapp.Login;
import com.example.simpleapp.R;

import com.example.simpleapp.SuperAdmin.SuperAdminProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HallAdminDashboard extends AppCompatActivity implements View.OnClickListener {
    Button mlcreatehallofficials;
    Button logoutButton;
    CardView Roomcard,seatcard,assignseatcard,assignedseatcard,
            emptyseatcard,assignstudentcard,assignedstudentcard,emptystudentcard;

    ImageView mprofileImage;
    FirebaseUser user;
    FirebaseAuth fAuth;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_dashboard);

        mlcreatehallofficials=findViewById(R.id.createhallofficialsbutton);
        logoutButton=findViewById(R.id.logoutButton);
        Roomcard=findViewById(R.id.Roomcard);
        seatcard=findViewById(R.id.seatcard);
        assignseatcard=findViewById(R.id.assignseatcard);
        assignedseatcard=findViewById(R.id.assignedseatcard);
        emptyseatcard=findViewById(R.id.emptyseatcard);
        assignstudentcard=findViewById(R.id.assignstudentcard);
        assignedstudentcard=findViewById(R.id.assignedstudentcard);
        emptystudentcard=findViewById(R.id.emptystudentcard);

        mprofileImage=findViewById(R.id.profileImage);


        Roomcard.setOnClickListener(this);
        seatcard.setOnClickListener(this);
        assignseatcard.setOnClickListener(this);
        assignedseatcard.setOnClickListener(this);
        emptyseatcard.setOnClickListener(this);
        assignstudentcard.setOnClickListener(this);
        assignedstudentcard.setOnClickListener(this);
        emptystudentcard.setOnClickListener(this);



        mlcreatehallofficials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallOfficialRegister.class));
            }
        });


        mprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HallAdminProfile.class));
            }
        });



        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("halladmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mprofileImage);
            }
        });

    }

    public void halladminlogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), HallAdminLogin.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.Roomcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this, HRoom1.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.seatcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this, HSeat.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.assignseatcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this, Assignstudent.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.assignedseatcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this, Login.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.emptyseatcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this,Login.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.assignstudentcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this,Login.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.assignedstudentcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this,Login.class);
            startActivity(intent);
        }

        else if(v.getId()== R.id.emptystudentcard)
        {
            Intent intent = new Intent(HallAdminDashboard.this,Login.class);
            startActivity(intent);
        }

    }
}