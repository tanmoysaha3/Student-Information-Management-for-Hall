package com.example.simpleapp.HallAdmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HallAdminProfile extends AppCompatActivity {

    TextView mFullName, mEmail,mdesignation,mdepartment,mphoneno;
    FirebaseDatabase rootNode;
    FirebaseAuth fAuth;
    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button updatebtn;
    ImageButton back_button;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_profile);


        mFullName=findViewById(R.id.nameField1);
        mEmail=findViewById(R.id.emailField1);
        mdepartment=findViewById(R.id.departmentField1);
        mdesignation=findViewById(R.id.designationField1);
        mphoneno=findViewById(R.id.phoneField1);
        updatebtn=findViewById(R.id.updatebtn);
        back_button=findViewById(R.id.back_button);
        profileImage=findViewById(R.id.profileImage);
        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("halladmin/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), com.example.simpleapp.HallAdmin.HallAdminProfileEdit.class);
                i.putExtra("fullname",mFullName.getText().toString());
                i.putExtra("department",mdepartment.getText().toString());
                i.putExtra("designation",mdesignation.getText().toString());
                i.putExtra("email",mEmail.getText().toString());
                i.putExtra("phoneno",mphoneno.getText().toString());
                startActivity(i);

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot ds : datasnapshot.getChildren()){
                    String name=""+ds.child("fullname").getValue();
                    String department=""+ds.child("department").getValue();
                    String designation=""+ds.child("designation").getValue();
                    String email=""+ds.child("email").getValue();
                    String phoneno=""+ds.child("phoneno").getValue();



                    mFullName.setText(name);
                    mEmail.setText(email);
                    mphoneno.setText(phoneno);
                    mdepartment.setText(department);
                    mdesignation.setText(designation);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}