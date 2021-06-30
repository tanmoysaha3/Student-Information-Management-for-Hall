package com.example.simpleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FilterStudents extends AppCompatActivity {

    Spinner mStuDeptSpinner, mStuYearSpinner, mStuHallSpinner, mStuFloorSpinner, mStuRoomSpinner;
    Button mFilterButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_students);

        mStuDeptSpinner=findViewById(R.id.stuDeptSpinner);
        mStuYearSpinner=findViewById(R.id.stuYearSpinner);
        mStuHallSpinner=findViewById(R.id.stuHallSpinner);
        mStuFloorSpinner=findViewById(R.id.stuFloorSpinner);
        mStuRoomSpinner=findViewById(R.id.stuRoomSpinner);
        mFilterButton=findViewById(R.id.filterButton);

        fStore = FirebaseFirestore.getInstance();

        populateStuDeptSpinner();
        populateStuYearSpinner();

        CollectionReference hallsRef = fStore.collection("Halls");
        List<String> halls = new ArrayList<>();
        ArrayAdapter<String> hallAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, halls);
        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStuHallSpinner.setAdapter(hallAdapter);
        hallsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String hall = document.getString("Hall_Id");
                        halls.add(hall);
                    }
                    hallAdapter.notifyDataSetChanged();
                }
            }
        });

        mStuHallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                CollectionReference floorRef = fStore.collection("Halls").document(selectedItem).collection("Floors");
                List<String> floors = new ArrayList<>();
                ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, floors);
                floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mStuFloorSpinner.setAdapter(floorAdapter);
                floorRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String floor = document.getString("Floor_No");
                                floors.add(floor);
                            }
                            floorAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mStuFloorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                CollectionReference roomRef = fStore.collection("Halls").document(mStuHallSpinner.getSelectedItem().toString())
                        .collection("Floors").document(selectedItem).collection("Rooms");
                List<String> rooms = new ArrayList<>();
                ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, rooms);
                roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mStuRoomSpinner.setAdapter(roomAdapter);
                roomRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String room = document.getString("Room_No");
                                rooms.add(room);
                            }
                            roomAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept=mStuDeptSpinner.getSelectedItem().toString();
                String year=mStuYearSpinner.getSelectedItem().toString();
                String hall=mStuHallSpinner.getSelectedItem().toString();
                String floor=mStuFloorSpinner.getSelectedItem().toString();
                String room=mStuRoomSpinner.getSelectedItem().toString();

                Intent i=new Intent(v.getContext(),SeeFilteredStudents.class);
                i.putExtra("Dept",dept);
                i.putExtra("Year",year);
                i.putExtra("Hall_Id",hall);
                i.putExtra("Floor_No",floor);
                i.putExtra("Room_No",room);
                startActivity(i);
            }
        });
    }

    private void populateStuYearSpinner() {
        ArrayAdapter<String>yearAdapter=new ArrayAdapter<>
                (this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.Years));
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mStuYearSpinner.setAdapter(yearAdapter);
    }

    private void populateStuDeptSpinner() {
        ArrayAdapter<String>deptAdapter=new ArrayAdapter<>
                (this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.DeptNames));
        deptAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mStuDeptSpinner.setAdapter(deptAdapter);
    }
}