package com.example.simpleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class SeeStudentDetails extends AppCompatActivity {

    TextView mStudentName, mStudentId, mStudentEmail, mStudentPhone, mStudentAddress, mStudentDOB, mStudentDept;
    String x;
    Button mFinalAssignStudentButton, mAssignStudentButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_student_details);

        Intent data = getIntent();
        String vStudentId = data.getStringExtra("Student Id");
        String uSeatId=data.getStringExtra("Unique_Seat_Id");

        mStudentName=findViewById(R.id.showStudentName);
        mStudentId=findViewById(R.id.showStudentId);
        mStudentEmail=findViewById(R.id.showStudentEmail);
        mStudentPhone=findViewById(R.id.showStudentPhone);
        mStudentAddress=findViewById(R.id.showStudentAddress);
        mStudentDOB=findViewById(R.id.showStudentDOB);
        mStudentDept=findViewById(R.id.showStudentDept);
        mAssignStudentButton=findViewById(R.id.assignStudentButton);
        mFinalAssignStudentButton=findViewById(R.id.finalAssignStudentButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        DocumentReference documentReference=fStore.collection("Verified Students").document(vStudentId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mStudentName.setText(documentSnapshot.getString("Full_Name"));
                mStudentId.setText(documentSnapshot.getString("StudentID"));
                mStudentEmail.setText(documentSnapshot.getString("Email"));
                mStudentPhone.setText(documentSnapshot.getString("Phone"));
                mStudentAddress.setText(documentSnapshot.getString("Address"));
                mStudentDOB.setText(documentSnapshot.getString("Date_of_Birth"));
                mStudentDept.setText(documentSnapshot.getString("Department"));
                x=documentSnapshot.getString("IsAssigned");
                if(x.equals("0")) {
                    mAssignStudentButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mFinalAssignStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef=fStore.collection("Verified Students").document(vStudentId);
                Map<String,Object> edited =new HashMap<>();
                edited.put("Assigned_Seat",uSeatId);
                edited.put("IsAssigned","1");
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + vStudentId);
                        Toast.makeText(SeeStudentDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
                DocumentReference docRef1=fStore.collection("Empty Seats").document(uSeatId);
                Map<String,Object> edited1 =new HashMap<>();
                edited1.put("Assigned_Student",vStudentId);
                edited1.put("IsAssigned","1");
                docRef1.update(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + uSeatId);
                        Toast.makeText(SeeStudentDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mAssignStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),SeeEmptyStudents.class));

                Intent i=new Intent(v.getContext(),SeeEmptySeats.class);
                i.putExtra("StudentID",mStudentId.getText().toString());
                Log.d("Value","Value of unique seat"+mStudentId.getText().toString());
                startActivity(i);
            }
        });
    }
}