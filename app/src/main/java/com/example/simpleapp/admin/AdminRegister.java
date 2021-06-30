package com.example.simpleapp.admin;

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

public class AdminRegister extends AppCompatActivity {

    EditText mAdminFullName, mAdminEmail, mAdminPassword;
    Button mAdminRegisterButton;
    TextView mLoginTextButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        mAdminFullName=findViewById(R.id.adminFullName);
        mAdminEmail=findViewById(R.id.adminEmail);
        mAdminPassword=findViewById(R.id.adminPassword);

        mAdminRegisterButton=findViewById(R.id.adminRegisterButton);
        mLoginTextButton=findViewById(R.id.loginText);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mProgressBar=findViewById(R.id.registerProgressBar);

        mAdminRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=mAdminEmail.getText().toString().trim();
                String password=mAdminPassword.getText().toString().trim();
                String fullName = mAdminFullName.getText().toString();

                //String regex = "\\S+@just.edu.bd$";
                String regex = "\\S+@gmail.com$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);

                if(TextUtils.isEmpty(fullName)) {
                    mAdminFullName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mAdminEmail.setError("Email is required");
                    return;
                }

                if (!matcher.matches()) {
                    mAdminEmail.setError("Enter university email");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mAdminPassword.setError("Password is required");
                    return;
                }

                if(password.length()<8) {
                    mAdminPassword.setError("Password length at least 8");
                    return;
                }

                String documentID=email.substring(0,email.indexOf("@"));

                mProgressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(AdminRegister.this, "Account created", Toast.LENGTH_SHORT).show();

                        DocumentReference documentReference = fStore.collection("Unverified Admins").document(documentID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Full_Name",fullName);
                        user.put("Email",email);
                        user.put("DocumentId", email.substring(0,email.indexOf("@")));
                        user.put("IsAdmin","0");
                        user.put("AssignedHall","0");
                        user.put("Role","X");
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","onSuccess : user profile is created for " + documentID);
                            }
                        });
                        startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminRegister.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility((View.GONE));
                    }
                });
            }
        });

        mLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminLogin.class));
            }
        });
    }
}