package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.HallAdmin.HallAdminDashboard;
import com.example.simpleapp.HallOfficials.HallOfficialsDashboard;
import com.example.simpleapp.Student.StudentDashboard;
import com.example.simpleapp.SuperAdmin.MainDashboard;
import com.example.simpleapp.SuperAdmin.SuperLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView mcreate_account_student;
    EditText mEmail, mPassword;
    Button loginButton;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser user;
    String email, password;
    public static final String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.lEmail);
        mPassword = findViewById(R.id.lPassword);
        loginButton = findViewById(R.id.loginButton);
        mcreate_account_student = findViewById(R.id.create_account_student);

        fAuth = FirebaseAuth.getInstance();

        mcreate_account_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.simpleapp.Student.StudentResister.class));
            }
        });




        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Intent intent = new Intent(Login.this, MainDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                userSign();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out.
        fAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            fAuth.removeAuthStateListener(mAuthListner);
        }

    }

    private void userSign() {
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Login.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }


        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {


                    Toast.makeText(Login.this, "Login not ", Toast.LENGTH_SHORT).show();

                } else {


                    checkIfEmailExist();

                }
            }
        });
    }

    private void checkIfEmailExist() {
        final String userEnteredUsername = mEmail.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Super Admin");
        Query query = reference.orderByChild("Email").equalTo(userEnteredUsername);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mEmail.getText().clear();

                    mPassword.getText().clear();
                    Intent intent = new Intent(Login.this, MainDashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail, email);

                    startActivity(intent);

                }
                else {

                    checkIfhalladmin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void checkIfhalladmin() {

        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Verify the Email Id",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            finish();
        }


        final String userEnteredUsername = mEmail.getText().toString().trim();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");
        Query query1 = reference1.orderByChild("email").equalTo(userEnteredUsername);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mEmail.getText().clear();

                    mPassword.getText().clear();
                    Intent intent = new Intent(Login.this, HallAdminDashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail, email);

                    startActivity(intent);

                }
                else {

                    checkIfhallofficials();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkIfhallofficials() {

        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Verify the Email Id",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            finish();
        }


        final String userEnteredUsername = mEmail.getText().toString().trim();

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Hall Officials Accounts");
        Query query2 = reference2.orderByChild("email").equalTo(userEnteredUsername);

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mEmail.getText().clear();

                    mPassword.getText().clear();
                    Intent intent = new Intent(Login.this, HallOfficialsDashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail, email);

                    startActivity(intent);

                }
                else {

                    checkIfstudent();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkIfstudent() {

        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Verify the Email Id",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            finish();
        }


        final String userEnteredUsername = mEmail.getText().toString().trim();

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Student Accounts");
        Query query3 = reference3.orderByChild("email").equalTo(userEnteredUsername);

        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mEmail.getText().clear();

                    mPassword.getText().clear();
                    Intent intent = new Intent(Login.this, StudentDashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail, email);

                    startActivity(intent);

                }
                else {

                    Toast.makeText(Login.this,"Email is not resistered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}