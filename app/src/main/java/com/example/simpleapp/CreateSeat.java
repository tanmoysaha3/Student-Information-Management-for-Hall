package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateSeat extends AppCompatActivity {

    Spinner mFloorNoSeatSpinner, mRoomNoSeatSpinner;
    TextView mShowPreCreatedSeatsNo;
    EditText mCreatedSeatsNumber;
    Button mCreateNewSeats;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String hallId;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seat);

        mFloorNoSeatSpinner=findViewById(R.id.floorNoSeatSpinner);
        mRoomNoSeatSpinner=findViewById(R.id.roomNoSeatSpinner);
        mShowPreCreatedSeatsNo=findViewById(R.id.showPreCreatedSeatsNo);
        mCreatedSeatsNumber=findViewById(R.id.createdSeatsNumber);
        mCreateNewSeats=findViewById(R.id.createNewSeats);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();

        String email=user.getEmail();
        String documentId=email.substring(0,email.indexOf("@"));

        DocumentReference docRef=fStore.collection("Verified Admins").document(documentId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                hallId=value.getString("AssignedHall");
                Toast.makeText(CreateSeat.this, ". "+hallId+" .", Toast.LENGTH_SHORT).show();

                CollectionReference floorRef = fStore.collection("Halls").document(hallId).collection("Floors");
                List<String> floors = new ArrayList<>();
                ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, floors);
                floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mFloorNoSeatSpinner.setAdapter(floorAdapter);
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

                mFloorNoSeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        CollectionReference roomRef = fStore.collection("Halls").document(hallId)
                                .collection("Floors").document(selectedItem).collection("Rooms");
                        List<String> rooms = new ArrayList<>();
                        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, rooms);
                        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mRoomNoSeatSpinner.setAdapter(roomAdapter);
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

                mRoomNoSeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        Task<QuerySnapshot> docRef = fStore.collection("Halls").document(hallId).collection("Floors")
                                .document(mFloorNoSeatSpinner.getSelectedItem().toString()).collection("Rooms").document(selectedItem)
                                .collection("Seats").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            QuerySnapshot querySnapshot = task.getResult();
                                            count = querySnapshot.size();
                                            mShowPreCreatedSeatsNo.setText(String.valueOf(count));
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                mCreateNewSeats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomNo=mRoomNoSeatSpinner.getSelectedItem().toString();
                        for(int i=1; i<=Integer.parseInt(mCreatedSeatsNumber.getText().toString());i++){
                            String x=String.valueOf(i+count);
                            if(Integer.parseInt(x)>5){
                                break;
                            }

                            DocumentReference documentReference=fStore.collection("Halls").document(hallId)
                                    .collection("Floors").document(mFloorNoSeatSpinner.getSelectedItem().toString())
                                    .collection("Rooms").document(roomNo).collection("Seats").document(x);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Seat_No", x);
                            user.put("IsAssigned","0");
                            user.put("Assigned_Student","X");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CreateSeat.this, "Seat Created", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG","onSuccess : user profile is created for " + x);
                                }
                            });

                            String uniqueSeatId=hallId+mFloorNoSeatSpinner.getSelectedItem().toString()+roomNo+x;
                            DocumentReference documentReference1=fStore.collection("Created Seats").document(uniqueSeatId);
                            Map<String,Object> user1 = new HashMap<>();
                            user1.put("Hall_Id",hallId);
                            user1.put("Floor_No",mFloorNoSeatSpinner.getSelectedItem().toString());
                            user1.put("Room_No",roomNo);
                            user1.put("Seat_No",x);
                            user1.put("Unique_Seat_Id",uniqueSeatId);
                            user1.put("IsAssigned","0");
                            user1.put("Assigned_Student","X");

                            documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CreateSeat.this, "Seat Created", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG","onSuccess : user profile is created for " + uniqueSeatId);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}