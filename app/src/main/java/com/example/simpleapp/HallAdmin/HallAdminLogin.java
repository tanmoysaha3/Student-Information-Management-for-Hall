package com.example.simpleapp.HallAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.MainActivity;
import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HallAdminLogin extends AppCompatActivity {

    private static final Object TAG = "LOGIN";
    EditText hEmail,hPassword;
    TextView resetPasswordLink;
    Button hloginButton,back_button;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser user;
    String email, password;
    public static final String userEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_login);



        hEmail=findViewById(R.id.hEmail);
        hPassword=findViewById(R.id.hPassword);

        fAuth= FirebaseAuth.getInstance();
        hloginButton=findViewById(R.id.hloginButton);
        back_button=findViewById(R.id.back_button);
        resetPasswordLink=findViewById(R.id.hresetPasswordLink);
        user = FirebaseAuth.getInstance().getCurrentUser();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));;
            }
        });

        resetPasswordLink.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(HallAdminLogin.this,"Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(HallAdminLogin.this, "Error! Reset Link is not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Intent intent = new Intent(HallAdminLogin.this, HallAdminDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        };

        hloginButton.setOnClickListener(new View.OnClickListener() {
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
        email = hEmail.getText().toString().trim();
        password = hPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(HallAdminLogin.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(HallAdminLogin.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }


        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {


                    Toast.makeText(HallAdminLogin.this, "Login not successfull", Toast.LENGTH_SHORT).show();

                } else {


                    checkIfEmailVerified();

                }
            }
        });
    }

    private void checkIfEmailVerified() {
        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Verify the Email Id",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            finish();
        }
        else {
            hEmail.getText().clear();

            hPassword.getText().clear();
            Intent intent = new Intent(HallAdminLogin.this, HallAdminDashboard.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(userEmail,email);

            startActivity(intent);

        }
    }


}