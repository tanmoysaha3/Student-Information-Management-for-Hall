package com.example.simpleapp.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class StudentEditProfile extends AppCompatActivity {

    Spinner edit_bloodgroup,edit_department,edit_batch;
    Button savestinfo;
    FirebaseDatabase rootNode;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);

        edit_bloodgroup=findViewById(R.id.edit_bloodgroup);
        edit_department=findViewById(R.id.edit_department);
        edit_batch=findViewById(R.id.edit_batch);
        savestinfo=findViewById(R.id.savestinfo);

        fAuth= FirebaseAuth.getInstance();
        rootNode= FirebaseDatabase.getInstance();
        user=fAuth.getCurrentUser();


        databaseReference=FirebaseDatabase.getInstance().getReference("Student Accounts");

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot ds : datasnapshot.getChildren()){
                    String rollno=""+ds.child("rollno").getValue();

                    savestinfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Map<String,Object> edited=new HashMap<>();
                            edited.put("department",edit_department.getSelectedItem().toString());
                            edited.put("bloodgroup",edit_bloodgroup.getSelectedItem().toString());
                            edited.put("batch",edit_batch.getSelectedItem().toString());


                            databaseReference=FirebaseDatabase.getInstance().getReference("Student Accounts");

                            databaseReference.child(rollno)
                                    .updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(com.example.simpleapp.Student.StudentEditProfile.this, "Assigned", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), StudentDashboard.class));

                                }
                            });

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}