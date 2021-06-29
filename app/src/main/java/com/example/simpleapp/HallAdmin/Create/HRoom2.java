package com.example.simpleapp.HallAdmin.Create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.Screate.FloorList;
import com.example.simpleapp.Super_Admin.Screate.Fmodel;
import com.example.simpleapp.Super_Admin.Screate.Hall;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HRoom2 extends AppCompatActivity {
    public static final String FLOOR_ID = "floor_id";
    public static final String FLOOR_NAME = "floor_name";

    TextView mhallname;
    Spinner mfloorname;
    Button mcreatefloor;

    ListView mListViewFloor;
    List<Fmodel> floorList;

    DatabaseReference databaseFloor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_room2);

        mhallname = (TextView) findViewById(R.id.hallname);

        mhallname = (TextView) findViewById(R.id.hallname);
        mfloorname = (Spinner) findViewById(R.id.floorname);
        mcreatefloor = (Button) findViewById(R.id.createfloor);
        mListViewFloor = (ListView) findViewById(R.id.ListViewFloor);

        Intent intent = getIntent();

        floorList = new ArrayList<>();


        //See Hall name//
        String id = intent.getStringExtra(Hall.HALL_ID);
        String name = intent.getStringExtra(Hall.HALL_NAME);

        mhallname.setText(name);
        //...............



        databaseFloor = FirebaseDatabase.getInstance().getReference("Floors").child(name);

        mListViewFloor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fmodel fmodel = floorList.get(position);

                Intent intent = new Intent(getApplicationContext(), HRoom.class);

                intent.putExtra(FLOOR_ID, fmodel.getFloorid());
                intent.putExtra(FLOOR_NAME, fmodel.getFloorname());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFloor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                floorList.clear();

                for (DataSnapshot floorSnapshot : datasnapshot.getChildren()){
                    Fmodel fmodel = floorSnapshot.getValue(Fmodel.class);

                    floorList.add(fmodel);
                }

                ArrayAdapter adapter= new FloorList(HRoom2.this, floorList);
                mListViewFloor.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}