package com.example.simpleapp.Student;

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

public class StudentProfile extends AppCompatActivity {

    TextView mFullName, mEmail,mrollno,mdistrict, mpresentaddress, mbloodgroup, mriligion, mphoneno, mdepartment, mbatch, myear, mhallname, mfloorname, mroomno, mseatno;
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
        setContentView(R.layout.activity_student_profile);

        mFullName=findViewById(R.id.nameField1);
        mEmail=findViewById(R.id.emailField1);
        mrollno=findViewById(R.id.rollField1);
        mdistrict=findViewById(R.id.districtField1);
        mpresentaddress=findViewById(R.id.presentaddressField1);
        mbloodgroup=findViewById(R.id.bloodField1);
        mriligion=findViewById(R.id.religionField1);
        mphoneno=findViewById(R.id.phoneField1);
        mdepartment=findViewById(R.id.departmentField1);
        mbatch=findViewById(R.id.batchField1);
        myear=findViewById(R.id.yearField1);
        mhallname=findViewById(R.id.hallnameField1);
        mfloorname=findViewById(R.id.floorField1);
        mroomno=findViewById(R.id.roomField1);
        mseatno=findViewById(R.id.seatField1);
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
        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), com.example.simpleapp.Student.StudentEditProfile1.class);
                i.putExtra("rollno",mrollno.getText().toString());
                i.putExtra("district",mdistrict.getText().toString());
                i.putExtra("phoneno",mphoneno.getText().toString());
                i.putExtra("presentaddress",mpresentaddress.getText().toString());
                i.putExtra("riligion",mriligion.getText().toString());
                i.putExtra("year",myear.getText().toString());
                startActivity(i);

            }
        });



        databaseReference=FirebaseDatabase.getInstance().getReference("Student Accounts");

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot ds : datasnapshot.getChildren()){
                    String name=""+ds.child("fullname").getValue();
                    String district=""+ds.child("district").getValue();
                    String email=""+ds.child("email").getValue();
                    String floorname=""+ds.child("floorname").getValue();
                    String batch=""+ds.child("batch").getValue();
                    String bloodgroup=""+ds.child("bloodgroup").getValue();
                    String department=""+ds.child("department").getValue();
                    String hallname=""+ds.child("hallname").getValue();
                    String phoneno=""+ds.child("phoneno").getValue();
                    String presentaddress=""+ds.child("presentaddress").getValue();
                    String riligion=""+ds.child("riligion").getValue();
                    String rollno=""+ds.child("rollno").getValue();
                    String roomno=""+ds.child("roomno").getValue();
                    String seatno=""+ds.child("seatno").getValue();
                    String year=""+ds.child("year").getValue();

                    mFullName.setText(name);
                    mEmail.setText(email);
                    mrollno.setText(rollno);
                    mdistrict.setText(district);
                    mpresentaddress.setText(presentaddress);
                    mbloodgroup.setText(bloodgroup);
                    mriligion.setText(riligion);
                    mphoneno.setText(phoneno);
                    mdepartment.setText(department);
                    mbatch.setText(batch);
                    myear.setText(year);
                    mhallname.setText(hallname);
                    mfloorname.setText(floorname);
                    mroomno.setText(roomno);
                    mseatno.setText(seatno);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}