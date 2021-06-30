package com.example.simpleapp.superadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRoom extends AppCompatActivity {

    TextView mShowPreCreatedRoomsNo;
    EditText mCreatedRoomsNumber;
    Spinner mHallIdRoomSpinner, mFloorNoRoomSpinner;
    Button mCreateNewRooms;
    FirebaseFirestore fStore;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mShowPreCreatedRoomsNo=findViewById(R.id.showPreCreatedRoomsNo);
        mCreatedRoomsNumber=findViewById(R.id.createdRoomsNumber);
        mHallIdRoomSpinner=findViewById(R.id.hallIdRoomSpinner);
        mFloorNoRoomSpinner=findViewById(R.id.floorNoRoomSpinner);
        mCreateNewRooms=findViewById(R.id.createNewRooms);

        fStore=FirebaseFirestore.getInstance();

        CollectionReference hallsRef = fStore.collection("Halls");
        List<String> halls = new ArrayList<>();
        ArrayAdapter<String> hallAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, halls);
        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHallIdRoomSpinner.setAdapter(hallAdapter);
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

        mHallIdRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                CollectionReference floorRef = fStore.collection("Halls").document(selectedItem).collection("Floors");
                List<String> floors = new ArrayList<>();
                ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, floors);
                floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mFloorNoRoomSpinner.setAdapter(floorAdapter);
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

        mFloorNoRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Task<QuerySnapshot> docRef = fStore.collection("Halls").document(mHallIdRoomSpinner.getSelectedItem().toString()).collection("Floors").document(selectedItem).collection("Rooms").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    count = querySnapshot.size();
                                    mShowPreCreatedRoomsNo.setText(String.valueOf(count));
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCreateNewRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String floorNo=mFloorNoRoomSpinner.getSelectedItem().toString();
                for(int i=1; i<=Integer.parseInt(mCreatedRoomsNumber.getText().toString());i++){
                    String x=String.valueOf(i+count);
                    String y;
                    if(Integer.parseInt(x)<10){
                        y="0"+x;
                    }
                    else {
                        y=x;
                    }
                    String z=mFloorNoRoomSpinner.getSelectedItem().toString()+y;
                    DocumentReference documentReference=fStore.collection("Halls").document(mHallIdRoomSpinner.getSelectedItem().toString())
                            .collection("Floors").document(floorNo).collection("Rooms").document(y);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Room_No", y);
                    user.put("Updated_Room_No",z);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateRoom.this, "Room Created", Toast.LENGTH_SHORT).show();
                            Log.d("TAG","onSuccess : user profile is created for " + x);
                        }
                    });
                }
            }
        });
    }
}