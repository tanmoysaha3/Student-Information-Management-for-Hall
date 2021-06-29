package com.example.simpleapp.Super_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.HallAdmin.HallAdminLogin;
import com.example.simpleapp.MainActivity;
import com.example.simpleapp.R;
import com.example.simpleapp.Students.StudentLogin;
import com.example.simpleapp.Students.StudentResister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SuperLogin extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText mEmail,mPassword;
    Button mLoginButton, mResetPasswordLink,back_button;
    TextView mRegisterTextButton;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_login);



        mEmail=findViewById(R.id.lEmail);
        mPassword=findViewById(R.id.lPassword);
        mProgressBar=findViewById(R.id.loginProgressBar);
        back_button=findViewById(R.id.back_button);

        fAuth=FirebaseAuth.getInstance();
        mLoginButton=findViewById(R.id.loginButton);
        mRegisterTextButton=findViewById((R.id.registerPageLink));
        mResetPasswordLink=findViewById(R.id.resetPasswordLink);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        mRegisterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SuperRegister.class));
            }
        });



    }


}