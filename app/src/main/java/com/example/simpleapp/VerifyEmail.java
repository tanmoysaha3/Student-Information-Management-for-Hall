package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerifyEmail extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button mSendVerificationEmailButton, mCheckIfVerifiedButton;
    ImageButton mRefreshPageButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button mVerifyEmailButton, mGoToProfileButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        mVerifyEmailButton=findViewById(R.id.verifyEmailButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        mVerifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyEmail.this, "Verification email has been sent. Please Verify and " +
                                "login again", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: Email not sent." + e.getMessage());
                    }
                });
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}