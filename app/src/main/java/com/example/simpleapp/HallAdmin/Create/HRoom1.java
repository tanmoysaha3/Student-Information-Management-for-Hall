package com.example.simpleapp.HallAdmin.Create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.Screate.Hmodel;
import com.example.simpleapp.SuperAdmin.Screate.HallList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HRoom1 extends AppCompatActivity {
    public static final String HALL_ID = "hall_id";
    public static final String HALL_NAME = "hall_name";


    DatabaseReference databaseHall;
    ListView mListViewHall;
    List<Hmodel> hallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_room1);

        mListViewHall=(ListView) findViewById(R.id.ListViewHall);

        databaseHall = FirebaseDatabase.getInstance().getReference("Halls");

        hallList = new ArrayList<>();


        mListViewHall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hmodel hmodel = hallList.get(position);

                Intent intent = new Intent(getApplicationContext(), HRoom2.class);

                intent.putExtra(HALL_ID, hmodel.getHallid());
                intent.putExtra(HALL_NAME, hmodel.getHallname());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseHall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                hallList.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    Hmodel hmodel = hallSnapshot.getValue(Hmodel.class);

                    hallList.add(hmodel);


                }

                ArrayAdapter adapter= new HallList(HRoom1.this, hallList);
                mListViewHall.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}