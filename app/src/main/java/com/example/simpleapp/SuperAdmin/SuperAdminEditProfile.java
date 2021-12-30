package com.example.simpleapp.SuperAdmin;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
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

public class SuperAdminEditProfile extends AppCompatActivity {

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
        setContentView(R.layout.activity_super_admin_edit_profile);

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
        String name = intent.getStringExtra("Name");
        String department =intent.getStringExtra("Department");
        String designation =intent.getStringExtra("Designation");
        String email =intent.getStringExtra("Email");
        String phone =intent.getStringExtra("Phone");


        mFullName.setText(name);
        mEmail.setText(email);
        mphoneno.setText(phone);
        mdepartment.setText(department);
        mdesignation.setText(designation);

        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("superadmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
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
                Toast.makeText(com.example.simpleapp.SuperAdmin.SuperAdminEditProfile.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(com.example.simpleapp.SuperAdmin.SuperAdminEditProfile.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> edited=new HashMap<>();
                edited.put("Name",mFullName.getText().toString());
                edited.put("Department",mdepartment.getText().toString());
                edited.put("Designation",mdesignation.getText().toString());
                edited.put("Email",mEmail.getText().toString());
                edited.put("Phone",mphoneno.getText().toString());

                databaseReference=FirebaseDatabase.getInstance().getReference("Super Admin");

                databaseReference.child(name)
                        .updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.simpleapp.SuperAdmin.SuperAdminEditProfile.this, "Updated", Toast.LENGTH_SHORT).show();
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
        final StorageReference fileRef=storageReference.child("superadmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(com.example.simpleapp.SuperAdmin.SuperAdminEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(editprofileImage);


                        Map<String,Object> edited1=new HashMap<>();
                        edited1.put("Image",String.valueOf(uri));
                        edited1.put("Name",mFullName.getText().toString());
                        edited1.put("Department",mdepartment.getText().toString());
                        edited1.put("Designation",mdesignation.getText().toString());
                        edited1.put("Email",mEmail.getText().toString());
                        edited1.put("Phone",mphoneno.getText().toString());

                        databaseReference=FirebaseDatabase.getInstance().getReference("Super Admin");

                        databaseReference.child(mFullName.getText().toString())
                                .updateChildren(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(com.example.simpleapp.SuperAdmin.SuperAdminEditProfile.this, "Updated", Toast.LENGTH_SHORT).show();
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