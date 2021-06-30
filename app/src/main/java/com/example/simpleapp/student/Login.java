package com.example.simpleapp.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.VerifyEmail;
import com.example.simpleapp.admin.AdminLogin;
import com.example.simpleapp.admin.CheckAdminLevel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class Login extends AppCompatActivity {
    private static final String TAG = "TAG";

    private static final int WAIT_TIME = 3 * 60 * 1000;
    private int loginAttempts = 3;

    EditText mLEmail,mLPassword;
    Button mLoginButton, mAdminLoginTextButton;
    TextView mRegisterTextButton, mResetPasswordLink;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLEmail=findViewById(R.id.lEmail);
        mLPassword=findViewById(R.id.lPassword);
        mProgressBar=findViewById(R.id.loginProgressBar);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mLoginButton=findViewById(R.id.loginButton);
        mRegisterTextButton=findViewById((R.id.registerPageLink));
        mAdminLoginTextButton=findViewById(R.id.adminLoginTextButton);
        mResetPasswordLink=findViewById(R.id.resetPasswordLink);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginAttempts == 0) {
                    Toast.makeText(Login.this, "Your attempt reach 0, please wait 3 minutes to log again", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = mLEmail.getText().toString().trim();
                String password = mLPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mLEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mLPassword.setError("Password is required");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                        FirebaseUser fuser = fAuth.getCurrentUser();
                        if (fuser.isEmailVerified()) {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        loginAttempts--;

                        Toast.makeText(Login.this, "Email and Password is incorrect",
                                Toast.LENGTH_SHORT).show();

                        if (loginAttempts == 2) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    loginAttempts = 3;
                                }
                            }, WAIT_TIME);
                        }
                    }
                });
            }
        });

        mRegisterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        mAdminLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

        mResetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to receive the password link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset Link is not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                String lEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if (lEmail.substring(lEmail.indexOf("@") + 1).equals("student.just.edu.bd")) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                } else {
                    Intent intent=new Intent(getApplicationContext(), CheckAdminLevel.class);
                    intent.putExtra("Email",lEmail);
                    startActivity(intent);
                    finish();
                }
            }
            else {
                startActivity(new Intent(getApplicationContext(),VerifyEmail.class));
            }
            finish();
        }
    }
}