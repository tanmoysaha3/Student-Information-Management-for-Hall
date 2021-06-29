package com.example.simpleapp.HallAdmin.StudentAssign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class Assignstudent2 extends AppCompatActivity {

    public static final String ROOM_ID = "room_id";
    public static final String ROOM_NAME = "room_name";
    public static final String HALL_NAME = "hall_name";
    public static final String FLOOR_NAME = "floor_name";
    public static final String FLOOR_ID = "floor_id";


    TextView mfloorname;

    ListView mListViewRoom;
    List<Rmodel> roomList;


    DatabaseReference databaseRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignstudent2);

        mfloorname = (TextView) findViewById(R.id.floorname);

        mListViewRoom = (ListView) findViewById(R.id.ListViewRoom);

        Intent intent = getIntent();

        roomList = new ArrayList<>();



        String id = intent.getStringExtra(Floor.FLOOR_ID);
        String name = intent.getStringExtra(Floor.FLOOR_NAME);


        mfloorname.setText(name);

        databaseRoom = FirebaseDatabase.getInstance().getReference("Rooms").child(id);

        mListViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rmodel rmodel = roomList.get(position);


                Intent intent = new Intent(getApplicationContext(), Assignstudent3.class);

                intent.putExtra(ROOM_ID, rmodel.getRoomid());
                intent.putExtra(ROOM_NAME, rmodel.getRoomname());
                intent.putExtra(HALL_NAME, rmodel.getHallname());
                intent.putExtra(FLOOR_NAME, rmodel.getFloorname());
                intent.putExtra(FLOOR_ID, rmodel.getFloorid());
                startActivity(intent);
            }
        });
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

                ArrayAdapter adapter= new RoomList(Assignstudent2.this, roomList);
                mListViewRoom.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}