package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Floor extends AppCompatActivity {

    EditText mEnterHallId, mEnterFloorNo;
    Button mCreateNewFloorButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);

        mEnterHallId=findViewById(R.id.enterHallId);
        mEnterFloorNo=findViewById(R.id.enterFloorNo);
        mCreateNewFloorButton=findViewById(R.id.createNewFloorButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateNewFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hallTd=mEnterHallId.getText().toString();
                String floorNo=mEnterFloorNo.getText().toString();

                DocumentReference documentReference=fStore.collection("Hall").document(hallTd).collection("Floors").document(floorNo);
                Map<String,Object> user = new HashMap<>();
                user.put("Date", Timestamp.now());

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + userID);
                        startActivity(new Intent(getApplicationContext(),Floor.class));
                    }
                });
            }
        });
    }
}