package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    TextView mName, mStudentId, mEmail, mPhone, mAddress, mdepartment, myear;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ImageButton mChangeProfileButton,back_button,home_button;
    FirebaseUser user;
    ImageView mProfileImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mName=findViewById(R.id.profileName);
        mStudentId=findViewById(R.id.profileStudentId);
        mdepartment=findViewById(R.id.profileDepartment);
        myear=findViewById(R.id.profileyear);
        mEmail=findViewById(R.id.profileEmail);
        mPhone=findViewById(R.id.profilePhone);
        mAddress=findViewById(R.id.profileAddress);
        back_button=findViewById(R.id.back_button);
        home_button=findViewById(R.id.HomeButton);
        mProfileImage=findViewById(R.id.profileImage);
        mChangeProfileButton=findViewById(R.id.changeProfileButton);

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

        userID=fAuth.getCurrentUser().getUid();
        user=fAuth.getCurrentUser();

        DocumentReference documentReference=fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mName.setText(documentSnapshot.getString("fullName"));
                mStudentId.setText(documentSnapshot.getString("studentID"));
                mEmail.setText(documentSnapshot.getString("email"));
                mPhone.setText(documentSnapshot.getString("phone"));
                mAddress.setText(documentSnapshot.getString("address"));
                mdepartment.setText(documentSnapshot.getString("department"));
                myear.setText(documentSnapshot.getString("year"));
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        mChangeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),EditProfile.class);
                i.putExtra("fullName",mName.getText().toString());
                i.putExtra("phone",mPhone.getText().toString());
                i.putExtra("address",mAddress.getText().toString());
                i.putExtra("department",mdepartment.getText().toString());
                i.putExtra("year",myear.getText().toString());
                i.putExtra("studentid",mStudentId.getText().toString());
                startActivity(i);
            }
        });
    }
}