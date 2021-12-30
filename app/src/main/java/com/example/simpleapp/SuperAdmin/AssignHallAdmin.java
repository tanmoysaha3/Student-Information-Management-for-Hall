package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.Screate.Hall;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignHallAdmin extends AppCompatActivity {

    Spinner mhallname;
    EditText madminname;
    Button assignhallbutton;
    DatabaseReference databasestudent,databasest,reference1,reference2;
    ArrayList<String> list,nlist;
    ArrayAdapter<String> adapter,aadapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_hall_admin);


        assignhallbutton=findViewById(R.id.assignhallbutton);


        madminname = findViewById(R.id.adminname);
        mhallname = findViewById(R.id.hallname);


        //.....................spinner.......

        databasestudent= FirebaseDatabase.getInstance().getReference("Halls");
        Query query1 = FirebaseDatabase.getInstance().getReference("Halls")
                .orderByChild("assignedhalladmin").equalTo("Not Assigned");
        query1.addListenerForSingleValueEvent(valueEventListener);

        list=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        mhallname.setAdapter(adapter);


        //...................spinner



        Intent intent=getIntent();
        String adminname = intent.getStringExtra(SHallAdmin.HALLAdmin_NAME);
        madminname.setText(adminname);

        assignhallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference1= FirebaseDatabase.getInstance().getReference("HallAdmin Accounts").child(adminname);
                Map<String,Object> edited=new HashMap<>();
                edited.put("assignedhall",mhallname.getSelectedItem().toString());


                reference1.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.simpleapp.SuperAdmin.AssignHallAdmin.this, "Assigned", Toast.LENGTH_SHORT).show();

                    }
                });

                reference2=FirebaseDatabase.getInstance().getReference("Halls").child(mhallname.getSelectedItem().toString());
                Map<String,Object> edited1=new HashMap<>();
                edited1.put("assignedhalladmin",adminname);

                reference2.updateChildren(edited1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

            }
        });



    }
    //.................Spinner..........

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            for(DataSnapshot mydata : snapshot.getChildren()){
                String hallname=mydata.child("hallname").getValue().toString();
                list.add(hallname);
                adapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener nvalueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            nlist.clear();
            for(DataSnapshot myydata : snapshot.getChildren()){
                String adminname=myydata.child("fullname").getValue(String.class);
                nlist.add(adminname);
                aadapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    //.......................Spinner................
}