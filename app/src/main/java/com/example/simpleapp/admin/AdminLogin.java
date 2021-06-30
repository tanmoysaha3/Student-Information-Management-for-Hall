package com.example.simpleapp.admin;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity {

    private static final String TAG = "TAG";

    private static final int WAIT_TIME = 3 * 60 * 1000;
    private int loginAttempts = 3;

    EditText mAdminLEmail,mAdminLPassword;
    Button mLoginButton;
    TextView mAdminRegisterTextButton, mResetPasswordLink;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAdminLEmail=findViewById(R.id.adminLEmail);
        mAdminLPassword=findViewById(R.id.adminLPassword);
        mProgressBar=findViewById(R.id.loginProgressBar);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mLoginButton=findViewById(R.id.loginButton);
        mAdminRegisterTextButton=findViewById((R.id.adminRegisterPageLink));
        mResetPasswordLink=findViewById(R.id.resetPasswordLink);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginAttempts == 0) {
                    Toast.makeText(AdminLogin.this, "Your attempt reach 0, please wait 3 minutes to log again", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email=mAdminLEmail.getText().toString().trim();
                String password=mAdminLPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mAdminLEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mAdminLPassword.setError("Password is required");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(AdminLogin.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                        FirebaseUser fuser = fAuth.getCurrentUser();
                        if (fuser.isEmailVerified()) {
                            Toast.makeText(AdminLogin.this, email+",", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(), CheckAdminLevel.class);
                            intent.putExtra("Email",email);
                            startActivity(intent);

                        } else {
                            startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                        }
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminLogin.this, "Error", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        loginAttempts--;

                        Toast.makeText(AdminLogin.this, "Email and Password is incorrect",
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

        mAdminRegisterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminRegister.class));
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
                                Toast.makeText(AdminLogin.this,"Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminLogin.this, "Error! Reset Link is not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                Intent intent=new Intent(getApplicationContext(), CheckAdminLevel.class);
                intent.putExtra("Email",mAdminLEmail.getText().toString());
                startActivity(intent);
                finish();
            }
            else {
                startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
            }
        }
    }
}