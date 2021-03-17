package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUniqueSeat extends AppCompatActivity {

    private static final String TAG = "TAG";

    EditText mSeatHallId, mSeatFloorNo, mSeatRoomNo, mSeatId;
    Button mCreateNewSeatButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unique_seat);

        mSeatHallId=findViewById(R.id.seatHallId);
        mSeatFloorNo=findViewById(R.id.seatFloorNo);
        mSeatRoomNo=findViewById(R.id.seatRoomNo);
        mSeatId=findViewById(R.id.seatId);
        mCreateNewSeatButton=findViewById(R.id.createNewSeatButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateNewSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hallId=mSeatHallId.getText().toString();
                String floorNo=mSeatFloorNo.getText().toString();
                int roomNo=Integer.parseInt(mSeatRoomNo.getText().toString()) ;
                String seatId=mSeatId.getText().toString();
                String fRoomNo;
                if(roomNo<10){
                    fRoomNo="0"+roomNo;
                }
                else {
                    fRoomNo=""+roomNo;
                }
                String uniqueSeatId = hallId + floorNo + fRoomNo + seatId;

                DocumentReference docIdRef = fStore.collection("Empty Seats").document(uniqueSeatId);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(AddUniqueSeat.this, "Seat exists", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Document exists!");
                            } else {
                                DocumentReference documentReference=fStore.collection("Empty Seats").document(uniqueSeatId);
                                Map<String,Object> user = new HashMap<>();
//                              user.put("Date", Timestamp.now());
                                user.put("Hall_Id", hallId);
                                user.put("Floor_No",floorNo);
                                user.put("Room_No",fRoomNo);
                                user.put("Seat_Id",seatId);
                                user.put("Unique_Seat_Id", uniqueSeatId);
                                user.put("IsAssigned", "0");
                                user.put("Assigned_Student","X");

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddUniqueSeat.this, "Seat Created", Toast.LENGTH_SHORT).show();
                                        Log.d("TAG","onSuccess : user profile is created for " + userID);
                                        startActivity(new Intent(getApplicationContext(),Admin.class));
                                    }
                                });
                                Log.d(TAG, "Document does not exist!");

                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });
            }
        });
    }
}