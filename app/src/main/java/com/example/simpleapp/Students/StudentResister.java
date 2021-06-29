package com.example.simpleapp.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.SuperAdminHelperClass;
import com.example.simpleapp.Super_Admin.SuperLogin;
import com.example.simpleapp.Super_Admin.SuperRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentResister extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword,mrollno;
    Button mRegisterButton;
    TextView mLoginTextButton;
    String userID;
    Spinner mdistrict, mpresentaddress, mbloodgroup, mriligion, mphoneno, mdepartment, mbatch, myear, mhallname, mfloorname, mroomno, mseatno;
    Button back_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_resister);

        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);
        mdistrict=findViewById(R.id.district);
        mpresentaddress=findViewById(R.id.presentaddress);
        mbloodgroup=findViewById(R.id.bloodgroup);
        mriligion=findViewById(R.id.religion);
        mphoneno=findViewById(R.id.phoneno);
        mdepartment=findViewById(R.id.department);
        mrollno=findViewById(R.id.rollno);
        mbatch=findViewById(R.id.batch);
        myear=findViewById(R.id.year);
        mhallname=findViewById(R.id.hallname);
        mfloorname=findViewById(R.id.floorname);
        mroomno=findViewById(R.id.roomno);
        mseatno=findViewById(R.id.seatno);

        back_button = findViewById(R.id.back_button);

        mRegisterButton=findViewById(R.id.registerButton);
        mLoginTextButton=findViewById(R.id.loginText);


        fAuth= FirebaseAuth.getInstance();

        reference=FirebaseDatabase.getInstance().getReference("Student Accounts");



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addinfo();

                String fullname = mFullName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String rollno = mrollno.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(fullname)) {
                    mFullName.setError("Full Name is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is empty");
                    return;
                }
                if(TextUtils.isEmpty(rollno)) {
                    mPassword.setError("Student Id is empty");
                    return;
                }




                signinuser(fullname,email,password);
            }
        });

        mLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });






        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addinfo() {
        String fullname = mFullName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String district = mdistrict.getSelectedItem().toString();
        String presentaddress = mpresentaddress.getSelectedItem().toString();
        String bloodgroup = mbloodgroup.getSelectedItem().toString();
        String riligion = mriligion.getSelectedItem().toString();
        String phoneno = mphoneno.getSelectedItem().toString();
        String rollno = mrollno.getText().toString();
        String batch = mbatch.getSelectedItem().toString();
        String year = myear.getSelectedItem().toString();
        String hallname = mhallname.getSelectedItem().toString();
        String floorname = mfloorname.getSelectedItem().toString();
        String roomno = mroomno.getSelectedItem().toString();
        String seatno = mseatno.getSelectedItem().toString();
        String department = mdepartment.getSelectedItem().toString();

        if (!TextUtils.isEmpty(fullname)){

            String id = reference.push().getKey();
            SuperAdminHelperClass superAdminHelperClass= new SuperAdminHelperClass(fullname,email,password,district, presentaddress, bloodgroup, riligion, phoneno, department, rollno, batch, year, hallname, floorname, roomno, seatno);

            reference.child(rollno).setValue(superAdminHelperClass);

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
                        Intent intent = new Intent(StudentResister.this, StudentLogin.class);
                        startActivity(intent);
                        finish();
                    }

                }else{

                    String error = task.getException().toString();
                    Log.d("login",error);
                    Toast.makeText(StudentResister.this,"error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sendemail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(StudentResister.this,"check email for verification",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}