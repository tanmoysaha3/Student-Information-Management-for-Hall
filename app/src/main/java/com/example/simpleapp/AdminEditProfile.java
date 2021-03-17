package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminEditProfile extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText mProfilePosition, mProfilePhone, mProfileAddr, mProfileDOB, mProfileDept;
    ImageView mProfileImageV;
    Button mUpdateProfileButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_profile);

        Intent data = getIntent();
        String position=data.getStringExtra("Position");
        String phone = data.getStringExtra("Phone");
        String address = data.getStringExtra("Address");
        String dOB = data.getStringExtra("Date of Birth");
        String dept =data.getStringExtra("Department");

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();
        String x=user.getEmail();
        String documentID=x.substring(0,x.indexOf("@"));
        storageReference= FirebaseStorage.getInstance().getReference();

        mProfilePosition=findViewById(R.id.changePosition);
        mProfilePhone=findViewById(R.id.changePhone);
        mProfileDOB=findViewById(R.id.changeDOB);
        mProfileAddr=findViewById(R.id.changeAddress);
        mProfileDept=findViewById(R.id.changeDept);
        mProfileImageV=findViewById(R.id.changePImage);
        mUpdateProfileButton=findViewById(R.id.updateProfileButton);

        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfileImageV);
            }
        });

        mProfileImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
                Toast.makeText(AdminEditProfile.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProfileDOB.getText().toString().isEmpty() || mProfilePhone.getText().toString().isEmpty()) {
                    Toast.makeText(AdminEditProfile.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docRef=fStore.collection("Verified Admins").document(documentID);
                Map<String,Object> edited =new HashMap<>();
                edited.put("Position",mProfilePosition.getText().toString());
                edited.put("Phone",mProfilePhone.getText().toString());
                edited.put("Date of Birth",mProfileDOB.getText().toString());
                edited.put("Address",mProfileAddr.getText().toString());
                edited.put("Department",mProfileDept.getText().toString());
                docRef.update((edited)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminEditProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminProfile.class));
                        finish();
                    }
                });
            }
        });

        mProfilePosition.setText(position);
        mProfilePhone.setText(phone);
        mProfileAddr.setText(address);
        mProfileDOB.setText(dOB);
        mProfileDept.setText(dept);


        Log.d(TAG,"onCreate: " + dOB + " " + phone);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                mProfileImageV.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(mProfileImageV);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}