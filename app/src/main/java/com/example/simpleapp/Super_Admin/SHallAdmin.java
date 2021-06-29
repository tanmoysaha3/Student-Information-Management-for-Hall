package com.example.simpleapp.Super_Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.simpleapp.HallAdmin.Create.HSeat3;
import com.example.simpleapp.HallAdmin.Create.NotAssignedStudents;
import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SHallAdmin extends AppCompatActivity {

    ImageButton back_button;
    Button assignhalladminpage;

    private RecyclerView recyclerView;
    private SHallAdminAdapter adapter;
    private List<SHallAdminModel> hmList;

    DatabaseReference databaseSHallAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hall_admin);

        back_button=findViewById(R.id.back_button);
        assignhalladminpage=findViewById(R.id.assignhalladminpage);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hmList = new ArrayList<>();
        adapter = new SHallAdminAdapter(this, hmList);
        recyclerView.setAdapter(adapter);

        databaseSHallAdmin = FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");
        databaseSHallAdmin.addValueEventListener(valueEventListener);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        assignhalladminpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AssignHallAdmin.class));
            }
        });


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            hmList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SHallAdminModel hm = snapshot.getValue(SHallAdminModel.class);
                    hmList.add(hm);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}