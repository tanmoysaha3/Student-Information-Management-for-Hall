package com.example.simpleapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener*/ {
    TextView memailVerifyMsg;
    Button mProfileButton, mEmailVerifyResendButton;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    FirebaseUser user;
    ImageView mProfileImage;
    StorageReference storageReference;
    CardView mHall, mFloor, mRoom, mSeat, mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mHall=findViewById(R.id.hall);
        mFloor=findViewById(R.id.floor);
        mRoom=findViewById(R.id.room);
        mSeat=findViewById(R.id.seat);
        mStudent=findViewById(R.id.student);
        mProfileButton=findViewById(R.id.profilePageLink);
        memailVerifyMsg=findViewById(R.id.emailVerifyMsg);
        mEmailVerifyResendButton=findViewById(R.id.verifyEmailButton);
        mProfileImage=findViewById(R.id.profileImage);
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        final FirebaseUser user=fAuth.getCurrentUser();

        mHall.setOnClickListener(this);
        mFloor.setOnClickListener(this);
        mRoom.setOnClickListener(this);
        mSeat.setOnClickListener(this);
        mStudent.setOnClickListener(this);


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfileImage);
            }
        });

        if(!user.isEmailVerified()) {
            memailVerifyMsg.setVisibility(View.VISIBLE);
            mEmailVerifyResendButton.setVisibility(View.VISIBLE);

            mEmailVerifyResendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(),"Verification email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","onFailure: Email not sent."+e.getMessage());
                        }
                    });
                }
            });
        }

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Profile.class));
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.hall)
        {
            Intent intent = new Intent(MainActivity.this,Hall.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.floor)
        {
            Intent intent = new Intent(MainActivity.this,Floor.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.room)
        {
            Intent intent = new Intent(MainActivity.this,Room.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.seat)
        {
            Intent intent = new Intent(MainActivity.this,Seat.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.student)
        {
            Intent intent = new Intent(MainActivity.this,Student.class);
            startActivity(intent);
        }*/

    }
}