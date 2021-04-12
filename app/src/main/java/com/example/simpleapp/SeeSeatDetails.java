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

public class SeeSeatDetails extends AppCompatActivity {

    TextView mShowHallId, mShowFloorNo, mShowRoomNo, mShowSeatId, mShowUniqueSeatId, mShowSeatStatus;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String x;
    Button mAssignSeatButton, mFinalAssignSeatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_seat_details);

        Intent data = getIntent();
        String vStudentId = data.getStringExtra("Unique Seat Id");
        String studentId = data.getStringExtra("Student Id");

        mShowHallId=findViewById(R.id.showHallId);
        mShowFloorNo=findViewById(R.id.showFloorNo);
        mShowRoomNo=findViewById(R.id.showRoomNo);
        mShowSeatId=findViewById(R.id.showSeatId);
        mShowUniqueSeatId=findViewById(R.id.showUniqueSeatId);
        mShowSeatStatus=findViewById(R.id.showSeatStatus);
        mAssignSeatButton=findViewById(R.id.assignSeatButton);
        mFinalAssignSeatButton=findViewById(R.id.finalAssignSeatButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        DocumentReference documentReference=fStore.collection("Empty Seats").document(vStudentId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mShowHallId.setText(documentSnapshot.getString("Hall_Id"));
                mShowFloorNo.setText(documentSnapshot.getString("Floor_No"));
                mShowRoomNo.setText(documentSnapshot.getString("Room_No"));
                mShowSeatId.setText(documentSnapshot.getString("Seat_Id"));
                mShowUniqueSeatId.setText(documentSnapshot.getString("Unique_Seat_Id"));
                mShowSeatStatus.setText(documentSnapshot.getString("IsAssigned"));
                x=documentSnapshot.getString("IsAssigned");
                if(x.equals("0")){
                    mAssignSeatButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mAssignSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),SeeEmptyStudents.class));

                Intent i=new Intent(v.getContext(),SeeEmptyStudents.class);
                i.putExtra("Unique_Seat_Id",mShowUniqueSeatId.getText().toString());
                Log.d("Value","Value of unique seat"+mShowUniqueSeatId.getText().toString());
                startActivity(i);
            }
        });

        mFinalAssignSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef=fStore.collection("Verified Students").document(studentId);
                Map<String,Object> edited =new HashMap<>();
                edited.put("Assigned_Seat",vStudentId);
                edited.put("IsAssigned","1");
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + vStudentId);
                        Toast.makeText(SeeSeatDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
                DocumentReference docRef1=fStore.collection("Empty Seats").document(vStudentId);
                Map<String,Object> edited1 =new HashMap<>();
                edited1.put("Assigned_Student",studentId);
                edited1.put("IsAssigned","1");
                docRef1.update(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + studentId);
                        Toast.makeText(SeeSeatDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}