package com.example.simpleapp.Super_Admin.Screate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class Hall extends AppCompatActivity {

    public static final String HALL_ID = "hall_id";
    public static final String HALL_NAME = "hall_name";


    EditText mhallname;
    Button mcreatehall;
    Spinner mhalltype,massignedhalladmin,massignedhallofficials;
    DatabaseReference databaseHall;
    ListView mListViewHall;
    List<Hmodel> hallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        databaseHall = FirebaseDatabase.getInstance().getReference("Halls");

        mhallname=(EditText) findViewById(R.id.hallname);
        mhalltype=(Spinner) findViewById(R.id.halltype);
        massignedhalladmin=(Spinner) findViewById(R.id.assignedhalladmin);
        massignedhallofficials=(Spinner)findViewById(R.id.assignedhallofficials);
        mcreatehall=(Button) findViewById(R.id.createhall);
        mListViewHall=(ListView) findViewById(R.id.ListViewHall);

        hallList = new ArrayList<>();

        mcreatehall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addhall();

            }
        });

        mListViewHall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hmodel hmodel = hallList.get(position);

                Intent intent = new Intent(getApplicationContext(), Floor.class);

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

                    ArrayAdapter adapter= new HallList(Hall.this, hallList);
                    mListViewHall.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void addhall(){
        String hname = mhallname.getText().toString().trim();
        String htype = mhalltype.getSelectedItem().toString();
        String assignedhalladmin = massignedhalladmin.getSelectedItem().toString();
        String assignedhallofficials = massignedhallofficials.getSelectedItem().toString();

        if (!TextUtils.isEmpty(hname)){

            String id = databaseHall.push().getKey();
            Hmodel hmodel= new Hmodel(id, hname, htype, assignedhalladmin,assignedhallofficials);

            databaseHall.child(hname).setValue(hmodel);
            Toast.makeText(this, "Hall Added Successfully" , Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this, "Enter Hall Name First", Toast.LENGTH_LONG).show();
        }
    }
}