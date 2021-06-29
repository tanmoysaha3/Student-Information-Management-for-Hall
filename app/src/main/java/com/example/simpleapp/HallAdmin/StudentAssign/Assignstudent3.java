package com.example.simpleapp.HallAdmin.StudentAssign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.simpleapp.HallAdmin.Create.NotAssignedStudents;
import com.example.simpleapp.HallAdmin.SeatList;
import com.example.simpleapp.HallAdmin.Smodel;
import com.example.simpleapp.R;
import com.example.simpleapp.Super_Admin.Screate.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Assignstudent3 extends AppCompatActivity {

    public static final String SEAT_ID = "seat_id";
    public static final String SEAT_NAME = "seat_name";
    public static final String ROOM_NAME = "room_name";
    public static final String ROOM_ID = "room_id";
    public static final String HALL_NAME = "hall_name";
    public static final String FLOOR_NAME = "floor_name";
    public static final String FLOOR_ID = "floor_id";

    TextView mroomname;
    EditText shallname,sfloorname,sroomname,sroomid,sfloorid;
    Spinner mseatname,massignedstudentid;
    Button mcreateseat;

    ListView mListViewSeat;
    List<Smodel> seatList;

    DatabaseReference databaseSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignstudent3);

        mroomname = (TextView) findViewById(R.id.roomname);
        shallname = (EditText) findViewById(R.id.shallname);
        sfloorname = (EditText) findViewById(R.id.sfloorname);
        sroomname = (EditText) findViewById(R.id.sroomname);
        sroomid = (EditText) findViewById(R.id.sroomid);
        sfloorid = (EditText) findViewById(R.id.sfloorid);


        mseatname = (Spinner) findViewById(R.id.seatname);
        massignedstudentid=(Spinner)findViewById(R.id.assignedstudentid);
        mcreateseat = (Button) findViewById(R.id.createseat);
        mListViewSeat = (ListView) findViewById(R.id.ListViewSeat);

        Intent intent = getIntent();

        seatList = new ArrayList<>();




        String id = intent.getStringExtra(Room.ROOM_ID);
        String name = intent.getStringExtra(Room.ROOM_NAME);
        String hname = intent.getStringExtra(Room.HALL_NAME);
        String fname = intent.getStringExtra(Room.FLOOR_NAME);
        String fid = intent.getStringExtra(Room.FLOOR_ID);

        mroomname.setText(name);
        shallname.setText(hname);
        sfloorname.setText(fname);
        sroomname.setText(name);
        sroomid.setText(id);
        sfloorid.setText(fid);

        databaseSeat = FirebaseDatabase.getInstance().getReference("Seats").child(id);



        mListViewSeat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Smodel smodel = seatList.get(position);

                Intent intent = new Intent(getApplicationContext(), NotAssignedStudents.class);

                intent.putExtra(SEAT_ID, smodel.getSeatid());
                intent.putExtra(SEAT_NAME, smodel.getSeatname());
                intent.putExtra(ROOM_NAME, smodel.getRoomname());
                intent.putExtra(ROOM_ID, smodel.getRoomid());
                intent.putExtra(HALL_NAME, smodel.getHallname());
                intent.putExtra(FLOOR_NAME, smodel.getFloorname());
                intent.putExtra(FLOOR_ID, smodel.getFloorid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSeat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                seatList.clear();

                for (DataSnapshot seatSnapshot : datasnapshot.getChildren()){
                    Smodel smodel = seatSnapshot.getValue(Smodel.class);

                    seatList.add(smodel);
                }

                ArrayAdapter adapter= new SeatList(Assignstudent3.this, seatList);
                mListViewSeat.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}