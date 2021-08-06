package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.HallAdmin.HallAdminHelperClass;
import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HallAdminCreate extends AppCompatActivity {


    EditText mhdName, mhdEmail, mhdPassword;
    Button mhdcreateButton,mdback_halladmin;
    String userID;
    Spinner mdesignation,massignedhall,mphoneno,mdepartment;
    Button back_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth fAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_create);

        mdback_halladmin=findViewById(R.id.back_halladmin);
        mhdName=findViewById(R.id.hdName);
        mhdEmail=findViewById(R.id.hdEmail);
        mhdPassword=findViewById(R.id.hdPassword);
        mhdcreateButton=findViewById(R.id.hdcreateButton);
        massignedhall=findViewById(R.id.hassignedhall);
        mphoneno=findViewById(R.id.hphoneno);
        mdesignation=findViewById(R.id.hdesignation);
        mdepartment=findViewById(R.id.hdepartment);




        fAuth= FirebaseAuth.getInstance();

        reference=FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");

        mhdcreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addinfo();

                String fullname = mhdName.getText().toString();
                String email = mhdEmail.getText().toString();
                String password = mhdPassword.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    mhdEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(fullname)) {
                    mhdName.setError("Full Name is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mhdPassword.setError("Password is empty");
                    return;
                }


                signinuser(fullname,email,password);
            }
        });



        mdback_halladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void signinuser(String fullname, String email, String password) {
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    sendemail();
                    if(!user.isEmailVerified()){
                        Intent intent = new Intent(com.example.simpleapp.SuperAdmin.HallAdminCreate.this, com.example.simpleapp.SuperAdmin.MainDashboard.class);
                        startActivity(intent);
                        finish();
                    }

                }else{

                    String error = task.getException().toString();
                    Log.d("login",error);
                    Toast.makeText(com.example.simpleapp.SuperAdmin.HallAdminCreate.this,"error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendemail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(com.example.simpleapp.SuperAdmin.HallAdminCreate.this,"check email for verification",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private void addinfo() {
        String fullname = mhdName.getText().toString();
        String email = mhdEmail.getText().toString();
        String password = mhdPassword.getText().toString();
        String designation = mdesignation.getSelectedItem().toString();
        String department = mdepartment.getSelectedItem().toString();
        String assignedhall = massignedhall.getSelectedItem().toString();
        String phoneno = mphoneno.getSelectedItem().toString();

        if (!TextUtils.isEmpty(fullname)){

            String id = reference.push().getKey();
            HallAdminHelperClass hallAdminHelperClass= new HallAdminHelperClass(fullname,email,password,designation,assignedhall, phoneno, department);

            reference.child(fullname).setValue(hallAdminHelperClass);

        }
        else {
            Toast.makeText(this, "Enter Name First", Toast.LENGTH_LONG).show();
        }

    }
}