package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.HallAdmin.HallAdminDashboard;
import com.example.simpleapp.HallAdmin.HallAdminLogin;
import com.example.simpleapp.MainActivity;
import com.example.simpleapp.R;
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

public class SuperLogin extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText mEmail, mPassword;
    Button loginButton, back_button;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser user;
    String email, password;
    public static final String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_login);


        mEmail = findViewById(R.id.lEmail);
        mPassword = findViewById(R.id.lPassword);
        back_button = findViewById(R.id.back_button);
        loginButton = findViewById(R.id.loginButton);

        fAuth = FirebaseAuth.getInstance();


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Intent intent = new Intent(SuperLogin.this, MainDashboard.class);
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
            Toast.makeText(SuperLogin.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(SuperLogin.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }


        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {


                    Toast.makeText(SuperLogin.this, "Login not successfull", Toast.LENGTH_SHORT).show();

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
                    Intent intent = new Intent(SuperLogin.this, MainDashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail, email);

                    startActivity(intent);

                }
                else {

                    Toast.makeText(SuperLogin.this, "You are not Super Admin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        //boolean emailVerified = users.isEmailVerified();
        /*if (!emailVerified) {
            Toast.makeText(this, "Verify the Email Id", Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            finish();
        } else {
            mEmail.getText().clear();

            mPassword.getText().clear();
            Intent intent = new Intent(SuperLogin.this, MainDashboard.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(userEmail, email);

            startActivity(intent);

        }*/
    }

}