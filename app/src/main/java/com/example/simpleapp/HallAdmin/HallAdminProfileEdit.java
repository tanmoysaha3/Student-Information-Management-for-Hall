package com.example.simpleapp.HallAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.SuperAdminProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class HallAdminProfileEdit extends AppCompatActivity {

    TextView mFullName, mEmail,mdesignation,mdepartment,mphoneno;
    FirebaseDatabase rootNode;
    FirebaseAuth fAuth;
    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button updatebtn;
    ImageButton back_button;
    ImageView editprofileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_profile_edit);

        mFullName=findViewById(R.id.editnameField1);
        mEmail=findViewById(R.id.editemailField1);
        mdepartment=findViewById(R.id.editdepartmentField1);
        mdesignation=findViewById(R.id.editdesignationField1);
        mphoneno=findViewById(R.id.editphoneField1);
        updatebtn=findViewById(R.id.updatebtn);
        back_button=findViewById(R.id.back_button);
        editprofileImage=findViewById(R.id.editprofileImage);
        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();

        Intent intent = getIntent();
        String name = intent.getStringExtra("fullname");
        String department =intent.getStringExtra("department");
        String designation =intent.getStringExtra("designation");
        String email =intent.getStringExtra("email");
        String phone =intent.getStringExtra("phoneno");


        mFullName.setText(name);
        mEmail.setText(email);
        mphoneno.setText(phone);
        mdepartment.setText(department);
        mdesignation.setText(designation);

        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("halladmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(editprofileImage);
            }
        });

        editprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
                Toast.makeText(com.example.simpleapp.HallAdmin.HallAdminProfileEdit.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        editprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
                Toast.makeText(com.example.simpleapp.HallAdmin.HallAdminProfileEdit.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> edited=new HashMap<>();
                edited.put("fullname",mFullName.getText().toString());
                edited.put("department",mdepartment.getText().toString());
                edited.put("designation",mdesignation.getText().toString());
                edited.put("email",mEmail.getText().toString());
                edited.put("phoneno",mphoneno.getText().toString());

                databaseReference=FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");

                databaseReference.child(name)
                        .updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.simpleapp.HallAdmin.HallAdminProfileEdit.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SuperAdminProfile.class));

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                editprofileImage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);


            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef=storageReference.child("halladmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(com.example.simpleapp.HallAdmin.HallAdminProfileEdit.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(editprofileImage);


                        Map<String,Object> edited1=new HashMap<>();
                        edited1.put("Image",String.valueOf(uri));
                        edited1.put("fullname",mFullName.getText().toString());
                        edited1.put("department",mdepartment.getText().toString());
                        edited1.put("designation",mdesignation.getText().toString());
                        edited1.put("email",mEmail.getText().toString());
                        edited1.put("phoneno",mphoneno.getText().toString());

                        databaseReference=FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");

                        databaseReference.child(mFullName.getText().toString())
                                .updateChildren(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(com.example.simpleapp.HallAdmin.HallAdminProfileEdit.this, "Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SuperAdminProfile.class));

                            }
                        });

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