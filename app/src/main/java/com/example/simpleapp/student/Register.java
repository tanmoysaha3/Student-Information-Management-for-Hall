package com.example.simpleapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.VerifyEmail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);

        mRegisterButton=findViewById(R.id.registerButton);
        mLoginTextButton=findViewById(R.id.loginText);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mProgressBar=findViewById(R.id.registerProgressBar);

        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();

                String regex = "^[0-9]{6}.[a-z]{3}@student.just.edu.bd$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);

                if (TextUtils.isEmpty(fullName)) {
                    mFullName.setError("Name is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (!matcher.matches()) {
                    mEmail.setError("Enter university email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 8) {
                    mPassword.setError("Password length at least 8");
                    return;
                }

                String studentID = email.substring(0, email.indexOf("."));
                String dept = email.substring(email.indexOf(".") + 1, email.indexOf("@")).toUpperCase();
                String year = studentID.substring(0, 2);

                mProgressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();

                        DocumentReference documentReference = fStore.collection("Unverified Students").document(studentID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("Full_Name", fullName);
                        user.put("Email", email);
                        user.put("StudentID", studentID);
                        user.put("Department", dept);
                        user.put("Year", year);
                        user.put("IsAssigned", "0");
                        user.put("Assigned_Seat", "0");
                        user.put("Hall_Id","0");
                        user.put("Floor_No","0");
                        user.put("Room_No","0");
                        user.put("Unique_Seat_Id","X");
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "onSuccess : user profile is created for " + studentID);
                            }
                        });

                        startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
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
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}