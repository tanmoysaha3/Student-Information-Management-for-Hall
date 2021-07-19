package com.example.simpleapp.Student;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class StudentEditProfile1 extends AppCompatActivity {

    ImageView editprofileImage;
    EditText edit_district, edit_paddress,edit_riligion,edit_year,edit_phone;
    Button savestinfo;
    ImageButton back_button,goeditprofileImage;
    FirebaseDatabase rootNode;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile1);

        edit_district=findViewById(R.id.edit_district);
        edit_paddress=findViewById(R.id.edit_paddress);
        edit_riligion=findViewById(R.id.edit_riligion);
        edit_year=findViewById(R.id.edit_year);
        edit_phone=findViewById(R.id.edit_phone);
        editprofileImage=findViewById(R.id.editprofileImage);
        savestinfo=findViewById(R.id.savestinfo);
        back_button=findViewById(R.id.back_button);
        goeditprofileImage=findViewById(R.id.goeditprofileImage);

        Intent intent = getIntent();
        String id = intent.getStringExtra("rollno");
        String district =intent.getStringExtra("district");
        String presentaddress =intent.getStringExtra("presentaddress");
        String riligion =intent.getStringExtra("riligion");
        String phoneno =intent.getStringExtra("phoneno");
        String year =intent.getStringExtra("year");







        edit_district.setText(district);
        edit_paddress.setText(presentaddress);
        edit_riligion.setText(riligion);
        edit_phone.setText(phoneno);
        edit_year.setText(year);




        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(editprofileImage);
            }
        });

        goeditprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
                Toast.makeText(com.example.simpleapp.Student.StudentEditProfile1.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(com.example.simpleapp.Student.StudentEditProfile1.this, "Profile Image clicked", Toast.LENGTH_SHORT).show();
            }
        });


        savestinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> edited=new HashMap<>();
                edited.put("district",edit_district.getText().toString());
                edited.put("phoneno",edit_phone.getText().toString());
                edited.put("presentaddress",edit_paddress.getText().toString());
                edited.put("riligion",edit_riligion.getText().toString());
                edited.put("year",edit_year.getText().toString());

                databaseReference=FirebaseDatabase.getInstance().getReference("Student Accounts");

                databaseReference.child(id)
                .updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.simpleapp.Student.StudentEditProfile1.this, "Assigned", Toast.LENGTH_SHORT).show();
                        onBackPressed();

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
        final StorageReference fileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(com.example.simpleapp.Student.StudentEditProfile1.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(editprofileImage);
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