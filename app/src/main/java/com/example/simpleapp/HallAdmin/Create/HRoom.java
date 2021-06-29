package com.example.simpleapp.HallAdmin.Create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.Screate.Floor;
import com.example.simpleapp.Super_Admin.Screate.Rmodel;
import com.example.simpleapp.Super_Admin.Screate.RoomList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HRoom extends AppCompatActivity {
    TextView mfloorname;

    ListView mListViewRoom;
    List<Rmodel> roomList;

    DatabaseReference databaseRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_room);
        mfloorname = (TextView) findViewById(R.id.floorname);

        mListViewRoom = (ListView) findViewById(R.id.ListViewRoom);

        Intent intent = getIntent();

        roomList = new ArrayList<>();


        String id = intent.getStringExtra(Floor.FLOOR_ID);
        String name = intent.getStringExtra(Floor.FLOOR_NAME);

        mfloorname.setText(name);

        databaseRoom = FirebaseDatabase.getInstance().getReference("Rooms").child(id);



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                roomList.clear();

                for (DataSnapshot roomSnapshot : datasnapshot.getChildren()){
                    Rmodel rmodel = roomSnapshot.getValue(Rmodel.class);

                    roomList.add(rmodel);
                }

                ArrayAdapter adapter= new RoomList(HRoom.this, roomList);
                mListViewRoom.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}