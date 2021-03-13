package com.example.simpleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ShowStuDetails extends AppCompatActivity {

    TextView mShowwStuName, mShowStuPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stu_details);

        Intent data = getIntent();
        String vStudentId = data.getStringExtra("vStudentId");

        mShowwStuName=findViewById(R.id.showStuName);
        mShowStuPhone=findViewById(R.id.showStuPhone);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        //userID=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("users").document(vStudentId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mShowwStuName.setText(documentSnapshot.getString("fullName"));
                mShowStuPhone.setText(documentSnapshot.getString("phone"));
            }
        });
    }
}