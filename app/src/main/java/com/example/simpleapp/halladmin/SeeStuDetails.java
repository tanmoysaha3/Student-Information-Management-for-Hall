package com.example.simpleapp.halladmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class SeeStuDetails extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView mStudentName, mStudentId, mStudentEmail, mStudentPhone, mStudentAddress, mStudentDOB, mStudentDept, mStudentSeat;
    String x;
    Button mDestroyStuButton, mRemoveSeatFromStuButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_stu_details);

        Intent data = getIntent();
        String vStudentId = data.getStringExtra("Student_Id");

        mStudentName=findViewById(R.id.showStudentName);
        mStudentId=findViewById(R.id.showStudentId);
        mStudentEmail=findViewById(R.id.showStudentEmail);
        mStudentPhone=findViewById(R.id.showStudentPhone);
        mStudentAddress=findViewById(R.id.showStudentAddress);
        mStudentDOB=findViewById(R.id.showStudentDOB);
        mStudentDept=findViewById(R.id.showStudentDept);
        mStudentSeat=findViewById(R.id.showStudentSeat);
        mDestroyStuButton=findViewById(R.id.destroyStuButton);
        mRemoveSeatFromStuButton=findViewById(R.id.removeSeatFromStuButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mDestroyStuButton.setVisibility(View.INVISIBLE);
        mRemoveSeatFromStuButton.setVisibility(View.INVISIBLE);

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
                mStudentSeat.setText(documentSnapshot.getString("Unique_Seat_Id"));
                x=documentSnapshot.getString("IsAssigned");
                if(x.equals("0")) {
                    mDestroyStuButton.setVisibility(View.VISIBLE);
                }
                else if(x.equals("1")){
                    mRemoveSeatFromStuButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mDestroyStuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef1=fStore.collection("Verified Students").document(mStudentId.getText().toString());
                docRef1.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                startActivity(new Intent(getApplicationContext(), HallAdmin.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

            }
        });

        mRemoveSeatFromStuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueSeatId=mStudentSeat.getText().toString();
                DocumentReference docRef=fStore.collection("Verified Students").document(mStudentId.getText().toString());
                Map<String,Object> edited =new HashMap<>();
                edited.put("Unique_Seat_Id","X");
                edited.put("IsAssigned","0");
                edited.put("Hall_Id","0");
                edited.put("Floor_No","0");
                edited.put("Room_No","0");
                edited.put("Assigned_Seat","0");
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + mStudentId.getText().toString());
                        Toast.makeText(SeeStuDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
                DocumentReference docRef1=fStore.collection("Created Seats").document(mStudentSeat.getText().toString());
                Map<String,Object> edited1 =new HashMap<>();
                edited1.put("Assigned_Student","X");
                edited1.put("IsAssigned","0");
                docRef1.update(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + mStudentSeat.getText().toString());
                        Toast.makeText(SeeStuDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });

                String hallId=uniqueSeatId.substring(0,1);
                Toast.makeText(SeeStuDetails.this, "Hall Id "+hallId, Toast.LENGTH_SHORT).show();
                String floorNo=uniqueSeatId.substring(1,2);
                Toast.makeText(SeeStuDetails.this, "Floor No "+floorNo, Toast.LENGTH_SHORT).show();
                String roomNo=uniqueSeatId.substring(2,4);
                String seatNo=uniqueSeatId.substring(4,5);
                DocumentReference docRef2=fStore.collection("Halls").document(hallId).collection("Floors")
                        .document(floorNo).collection("Rooms").document(roomNo).collection("Seats")
                        .document(seatNo);
                Map<String,Object> edited2 =new HashMap<>();
                edited2.put("Assigned_Student","X");
                edited2.put("IsAssigned","0");
                docRef2.update(edited2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + mStudentSeat.getText().toString());
                        Toast.makeText(SeeStuDetails.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        mRemoveSeatFromStuButton.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}