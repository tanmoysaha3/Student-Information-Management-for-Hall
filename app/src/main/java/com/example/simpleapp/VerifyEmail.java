package com.example.simpleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerifyEmail extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button mSendVerificationEmailButton, mCheckIfVerifiedButton;
    ImageButton mRefreshPageButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        mSendVerificationEmailButton = findViewById(R.id.sendVerificationEmailButton);
        mRefreshPageButton=findViewById(R.id.refreshPageButton);
        mCheckIfVerifiedButton=findViewById(R.id.checkIfVerifiedButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        String email=fUser.getEmail();
        String emailDomain=email.substring(email.indexOf("@")+1);

        mSendVerificationEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyEmail.this, "Verification email has been sent. After verify, " +
                                "double click refresh button", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: Email not sent." + e.getMessage());
                    }
                });
            }
        });

        mCheckIfVerifiedButton.setVisibility(View.INVISIBLE);

        mRefreshPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fUser.reload();
                if(fUser.isEmailVerified()) {
                    mCheckIfVerifiedButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mCheckIfVerifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fUser.isEmailVerified()) {
                    if(emailDomain.equals("student.just.edu.bd")) {
                        String studentId=email.substring(0,email.indexOf("."));
                        DocumentReference docIdRef = fStore.collection("Verified Students").document(studentId);
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "Document exists!");
                                    } else {
                                        moveFirestoreDocument(fStore.collection("Unverified Students").document(studentId),
                                                fStore.collection("Verified Students").document(studentId));
                                        Log.d(TAG, "Document does not exist!");

                                    }
                                } else {
                                    Log.d(TAG, "Failed with: ", task.getException());
                                }
                            }
                        });
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        finish();
                    }
                    else if(emailDomain.equals("gmail.com")) {
                        Toast.makeText(VerifyEmail.this, "Email Verified", Toast.LENGTH_SHORT).show();
                        String documentId=email.substring(0,email.indexOf("@"));
                        DocumentReference docIdRef = fStore.collection("Verified Admins").document(documentId);
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "Document exists!");
                                    } else {
                                        moveFirestoreDocument(fStore.collection("Unverified Admins").document(documentId),
                                                fStore.collection("Verified Admins").document(documentId));
                                        Log.d(TAG, "Document does not exist!");

                                    }
                                } else {
                                    Log.d(TAG, "Failed with: ", task.getException());
                                }
                            }
                        });
                        Toast.makeText(VerifyEmail.this, "Moved document", Toast.LENGTH_SHORT).show();

                        /*Intent intent=new Intent(getApplicationContext(),CheckAdminLevel.class);
                        intent.putExtra("Email",email);
                        startActivity(intent);*/
                        startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                        finish();
                    }
                }
                else {
                    Toast.makeText(VerifyEmail.this, "You need to Verify your email first, then click refresh" +
                            " button", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void moveFirestoreDocument(DocumentReference fromPath, final DocumentReference toPath) {
        fromPath.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        toPath.set(document.getData())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        fromPath.delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting document", e);
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}