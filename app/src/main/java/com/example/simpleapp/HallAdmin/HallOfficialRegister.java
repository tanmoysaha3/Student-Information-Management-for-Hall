package com.example.simpleapp.HallAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.HallAdminCreate;
import com.example.simpleapp.Super_Admin.MainDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HallOfficialRegister extends AppCompatActivity {

    EditText mhoName, mhoEmail, mhoPassword;
    Button mhocreateButton,moback_halladmin;
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
        setContentView(R.layout.activity_hall_official_register);

        moback_halladmin=findViewById(R.id.back_halladmin);
        mhoName=findViewById(R.id.hoName);
        mhoEmail=findViewById(R.id.hoEmail);
        mhoPassword=findViewById(R.id.hoPassword);
        mhocreateButton=findViewById(R.id.hocreateButton);
        massignedhall=findViewById(R.id.hassignedhall);
        mphoneno=findViewById(R.id.hphoneno);
        mdesignation=findViewById(R.id.hdesignation);
        mdepartment=findViewById(R.id.hdepartment);


        fAuth= FirebaseAuth.getInstance();

        reference=FirebaseDatabase.getInstance().getReference("Hall Officials Accounts");

        mhocreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addinfo();

                String fullname = mhoName.getText().toString();
                String email = mhoEmail.getText().toString();
                String password = mhoPassword.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    mhoEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(fullname)) {
                    mhoName.setError("Full Name is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mhoPassword.setError("Password is empty");
                    return;
                }


                signinuser(fullname,email,password);
            }
        });



        moback_halladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addinfo() {
        String fullname = mhoName.getText().toString();
        String email = mhoEmail.getText().toString();
        String password = mhoPassword.getText().toString();
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

    private void signinuser(String fullname, String email, String password) {
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    sendemail();
                    if(!user.isEmailVerified()){
                        Intent intent = new Intent(HallOfficialRegister.this, HallAdminDashboard.class);
                        startActivity(intent);
                        finish();
                    }

                }else{

                    String error = task.getException().toString();
                    Log.d("login",error);
                    Toast.makeText(HallOfficialRegister.this,"error",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(HallOfficialRegister.this,"check email for verification",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}