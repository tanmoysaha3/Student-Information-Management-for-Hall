package com.example.simpleapp.Super_Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.simpleapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SHallOfficials extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SHallAdminAdapter adapter;
    private List<SHallAdminModel> hmList;
    DatabaseReference databaseSHallAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hall_officials);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hmList = new ArrayList<>();
        adapter = new SHallAdminAdapter(this, hmList);
        recyclerView.setAdapter(adapter);

        databaseSHallAdmin = FirebaseDatabase.getInstance().getReference("Hall Officials Accounts");
        databaseSHallAdmin.addValueEventListener(valueEventListener);
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