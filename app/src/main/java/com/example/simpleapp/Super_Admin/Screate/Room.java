package com.example.simpleapp.Super_Admin.Screate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Room extends AppCompatActivity {

    public static final String ROOM_ID = "room_id";
    public static final String ROOM_NAME = "room_name";
    public static final String HALL_NAME = "hall_name";
    public static final String FLOOR_NAME = "floor_name";
    public static final String FLOOR_ID = "floor_id";

    TextView mfloorname;
    EditText rhallname,rfloorname,rfloorid;
    Spinner mroomname;
    Button mcreateroom;

    ListView mListViewRoom;
    List<Rmodel> roomList;

    DatabaseReference databaseRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mfloorname = (TextView) findViewById(R.id.floorname);
        mroomname = (Spinner) findViewById(R.id.roomname);
        mcreateroom = (Button) findViewById(R.id.createroom);
        rhallname = (EditText) findViewById(R.id.rhallname);
        rfloorname = (EditText) findViewById(R.id.rfloorname);
        rfloorid = (EditText) findViewById(R.id.rfloorid);
        mListViewRoom = (ListView) findViewById(R.id.ListViewRoom);


        Intent intent = getIntent();

        roomList = new ArrayList<>();


        String id = intent.getStringExtra(Floor.FLOOR_ID);
        String name = intent.getStringExtra(Floor.FLOOR_NAME);
        String hname = intent.getStringExtra(Floor.HALL_NAME);

        mfloorname.setText(name);
        rhallname.setText(hname);
        rfloorname.setText(name);
        rfloorid.setText(id);

        databaseRoom = FirebaseDatabase.getInstance().getReference("Rooms").child(id);

        mcreateroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom();
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

                ArrayAdapter adapter= new RoomList(Room.this, roomList);
                mListViewRoom.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void saveRoom(){
        String rname = mroomname.getSelectedItem().toString();
        String hname = rhallname.getText().toString().trim();
        String fname = rfloorname.getText().toString().trim();
        String fid = rfloorid.getText().toString().trim();


        if (1==1){

            String id = databaseRoom.push().getKey();
            Rmodel rmodel= new Rmodel(id,rname,hname,fname,fid);

            databaseRoom.child(id).setValue(rmodel);
            Toast.makeText(this, "one Room Added " , Toast.LENGTH_LONG).show();

        }


    }
}