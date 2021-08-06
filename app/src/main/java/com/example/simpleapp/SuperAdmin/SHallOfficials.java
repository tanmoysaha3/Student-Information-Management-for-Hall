package com.example.simpleapp.SuperAdmin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SHallOfficials extends AppCompatActivity {

    private ListView recyclerView;
    ListView mListViewHallAdmin;
    List<SHallAdminModel> halladminList;
    DatabaseReference databaseSHallAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hall_officials);

        recyclerView = findViewById(R.id.recyclerView);

        halladminList = new ArrayList<>();


        databaseSHallAdmin = FirebaseDatabase.getInstance().getReference("Hall Officials Accounts");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSHallAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                halladminList.clear();

                for (DataSnapshot halladminSnapshot : datasnapshot.getChildren()){

                    SHallAdminModel sHallAdminModel=halladminSnapshot.getValue(SHallAdminModel.class);

                    halladminList.add(sHallAdminModel);


                }

                ArrayAdapter adapter= new SHallAdminAdapter(SHallOfficials.this, halladminList);
                mListViewHallAdmin.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}