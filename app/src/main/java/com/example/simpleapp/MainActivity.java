package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView memailVerifyMsg;
    Button mProfileButton, mEmailVerifyResendButton;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileButton=findViewById(R.id.profilePageLink);
        memailVerifyMsg=findViewById(R.id.emailVerifyMsg);
        mEmailVerifyResendButton=findViewById(R.id.verifyEmailButton);
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        final FirebaseUser user=fAuth.getCurrentUser();

        if(!user.isEmailVerified()) {
            memailVerifyMsg.setVisibility(View.VISIBLE);
            mEmailVerifyResendButton.setVisibility(View.VISIBLE);

            mEmailVerifyResendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(),"Verification email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","onFailure: Email not sent."+e.getMessage());
                        }
                    });
                }
            });
        }

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Profile.class));
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}