package com.example.simpleapp.superadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HallAdminAssign extends AppCompatActivity {

    Spinner mHallIdHallAdminSpinner, mAdminIdHallAdminSpinner;
    Button mConfirmHallAdminAssignButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_admin_assign);

        mHallIdHallAdminSpinner=findViewById(R.id.hallIdHallAdminSpinner);
        mAdminIdHallAdminSpinner=findViewById(R.id.adminIdHallAdminSpinner);
        mConfirmHallAdminAssignButton=findViewById(R.id.confirmHallAdminAssignButton);

        fStore=FirebaseFirestore.getInstance();

        Query adminQuery=fStore.collection("Verified Admins").whereEqualTo("IsAdmin","0");
        List<String> admins = new ArrayList<>();
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, admins);
        adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdminIdHallAdminSpinner.setAdapter(adminAdapter);
        adminQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String admin = document.getString("DocumentId");
                        admins.add(admin);
                    }
                    adminAdapter.notifyDataSetChanged();
                }
            }
        });

        Query hallQuery=fStore.collection("Halls").whereEqualTo("IsHallAdminAssigned","0");
        List<String> halls = new ArrayList<>();
        ArrayAdapter<String> hallAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, halls);
        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHallIdHallAdminSpinner.setAdapter(hallAdapter);
        hallQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String hall = document.getString("Hall_Id");
                        halls.add(hall);
                    }
                    hallAdapter.notifyDataSetChanged();
                }
            }
        });

        mConfirmHallAdminAssignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminId=mAdminIdHallAdminSpinner.getSelectedItem().toString();
                String hallId=mHallIdHallAdminSpinner.getSelectedItem().toString();
                DocumentReference docRef=fStore.collection("Verified Admins").document(adminId);
                Map<String,Object> edited=new HashMap<>();
                edited.put("IsAdmin","2");
                edited.put("Role","Hall Admin");
                edited.put("AssignedHall",hallId);
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + adminId);
                        Toast.makeText(HallAdminAssign.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference docRef1=fStore.collection("Halls").document(hallId);
                Map<String,Object> edited1 =new HashMap<>();
                edited1.put("IsHallAdminAssigned","1");
                edited1.put("Hall_Admin",adminId);
                docRef1.update(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","onSuccess : user profile is created for " + hallId);
                        Toast.makeText(HallAdminAssign.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}