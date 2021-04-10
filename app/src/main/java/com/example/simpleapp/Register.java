package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    Button mRegisterButton;
    TextView mLoginTextButton;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageButton back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);

        back_button = findViewById(R.id.back_button);

        mRegisterButton=findViewById(R.id.registerButton);
        mLoginTextButton=findViewById(R.id.loginText);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mProgressBar=findViewById(R.id.registerProgressBar);

        if(fAuth.getCurrentUser() !=null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();

                String emailDomain = email.substring(email.indexOf("@")+1);
                String studentID=email.substring(0,email.indexOf("."));
                String dept=email.substring(email.indexOf(".")+1,email.indexOf("@")).toUpperCase();

                if(TextUtils.isEmpty(fullName)) {
                    mFullName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if(!"student.just.edu.bd".equals(emailDomain)) {
                    mEmail.setError("Enter university email");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Set Password");
                    return;
                }

                if(password.length()<8) {
                    mPassword.setError("Password length at least 8");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();

                        DocumentReference documentReference = fStore.collection("Unverified Students").document(studentID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Full_Name",fullName);
                        user.put("Email",email);
                        user.put("StudentID",studentID);
                        user.put("Department",dept);
                        user.put("IsAssigned","0");
                        user.put("Assigned_Seat","X");
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","onSuccess : user profile is created for " + studentID);
                            }
                        });

                        startActivity(new Intent(getApplicationContext(),VerifyEmail.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility((View.GONE));
                    }
                });
            }
        });

        mLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}