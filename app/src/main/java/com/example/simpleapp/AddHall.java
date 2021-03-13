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

public class AddHall extends AppCompatActivity {

    EditText mHallName, mHallId;
    Button mCreateNewHallButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hall);

        mHallName=findViewById(R.id.hallName);
        mHallId=findViewById(R.id.hallId);
        mCreateNewHallButton=findViewById(R.id.createNewHallButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateNewHallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String previousID;
                String hallName=mHallName.getText().toString();
                String hallId=mHallId.getText().toString();
                previousID=fStore.collection("Hall").document().getId();
                DocumentReference documentReference=fStore.collection("Hall").document(hallId);
                Map<String,Object> user = new HashMap<>();
                user.put("HallName",hallName);
                user.put("Date", Timestamp.now());

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + userID);
                        startActivity(new Intent(getApplicationContext(),Admin.class));
                    }
                });
            }
        });
    }
}