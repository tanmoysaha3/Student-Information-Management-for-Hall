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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatAssign extends AppCompatActivity {

    Spinner mFloorNoSeatAssignSpinner, mRoomNoSeatAssignSpinner, mSeatNoSeatAssignSpinner, mStuIdSeatAssignSpinner;
    TextView mStuNameSeatAssign, mStuIdSeatAssign, mStuPhoneSeatAssign, mStuAddressSeatAssign;
    Button mConfirmSeatAssignButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String hallId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_assign);

        mFloorNoSeatAssignSpinner=findViewById(R.id.floorNoSeatAssignSpinner);
        mRoomNoSeatAssignSpinner=findViewById(R.id.roomNoSeatAssignSpinner);
        mSeatNoSeatAssignSpinner=findViewById(R.id.seatNoSeatAssignSpinner);
        mStuIdSeatAssignSpinner=findViewById(R.id.stuIdSeatAssignSpinner);
        mStuNameSeatAssign=findViewById(R.id.stuNameSeatAssign);
        mStuIdSeatAssign=findViewById(R.id.stuIdSeatAssign);
        mStuPhoneSeatAssign=findViewById(R.id.stuPhoneSeatAssign);
        mStuAddressSeatAssign=findViewById(R.id.stuAddressSeatAssign);
        mConfirmSeatAssignButton=findViewById(R.id.confirmSeatAssignButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        fUser=fAuth.getCurrentUser();

        String email=fUser.getEmail();
        String documentId=email.substring(0,email.indexOf("@"));

        DocumentReference docRef=fStore.collection("Verified Admins").document(documentId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                hallId=value.getString("AssignedHall");
                //Toast.makeText(SeatAssign.this, ". "+hallId+" .", Toast.LENGTH_SHORT).show();

                CollectionReference floorRef = fStore.collection("Halls").document(hallId).collection("Floors");
                List<String> floors = new ArrayList<>();
                ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, floors);
                floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mFloorNoSeatAssignSpinner.setAdapter(floorAdapter);
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

                mFloorNoSeatAssignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        CollectionReference roomRef = fStore.collection("Halls").document(hallId)
                                .collection("Floors").document(selectedItem).collection("Rooms");
                        List<String> rooms = new ArrayList<>();
                        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, rooms);
                        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mRoomNoSeatAssignSpinner.setAdapter(roomAdapter);
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

                mRoomNoSeatAssignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        CollectionReference seatRef = fStore.collection("Halls").document(hallId)
                                .collection("Floors").document(mFloorNoSeatAssignSpinner.getSelectedItem().toString())
                                .collection("Rooms").document(selectedItem).collection("Seats");
                        List<String> seats = new ArrayList<>();
                        ArrayAdapter<String> seatAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, seats);
                        seatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSeatNoSeatAssignSpinner.setAdapter(seatAdapter);
                        seatRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String seat = document.getString("Seat_No");
                                        seats.add(seat);
                                    }
                                    seatAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Query studentQuery=fStore.collection("Verified Students").whereEqualTo("IsAssigned","0");
                List<String> students = new ArrayList<>();
                ArrayAdapter<String> studentAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, students);
                studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mStuIdSeatAssignSpinner.setAdapter(studentAdapter);
                studentQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String student = document.getString("StudentID");
                                students.add(student);
                            }
                            studentAdapter.notifyDataSetChanged();
                        }
                    }
                });

                mStuIdSeatAssignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        DocumentReference documentReference=fStore.collection("Verified Students").document(mStuIdSeatAssignSpinner.getSelectedItem().toString());
                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                mStuNameSeatAssign.setText(value.getString("Full_Name"));
                                mStuIdSeatAssign.setText(value.getString("StudentID"));
                                mStuPhoneSeatAssign.setText(value.getString("Phone"));
                                mStuAddressSeatAssign.setText(value.getString("Address"));
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                mConfirmSeatAssignButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference documentReference=fStore.collection("Halls").document(hallId)
                                .collection("Floors").document(mFloorNoSeatAssignSpinner.getSelectedItem().toString())
                                .collection("Rooms").document(mRoomNoSeatAssignSpinner.getSelectedItem().toString())
                                .collection("Seats").document(mSeatNoSeatAssignSpinner.getSelectedItem().toString());
                        Map<String,Object> user = new HashMap<>();
                        user.put("IsAssigned","1");
                        user.put("Assigned_Student",mStuIdSeatAssignSpinner.getSelectedItem().toString());

                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SeatAssign.this, "Seat Assigned", Toast.LENGTH_SHORT).show();
                                Log.d("TAG","onSuccess : user profile is created for " );
                            }
                        });

                        String uniqueSeatId=hallId+mFloorNoSeatAssignSpinner.getSelectedItem().toString()+mRoomNoSeatAssignSpinner
                                .getSelectedItem().toString()+mSeatNoSeatAssignSpinner.getSelectedItem().toString();
                        DocumentReference documentReference1=fStore.collection("Created Seats").document(uniqueSeatId);
                        Map<String,Object> user1 = new HashMap<>();
                        user1.put("IsAssigned","1");
                        user1.put("Assigned_Student",mStuIdSeatAssignSpinner.getSelectedItem().toString());

                        documentReference1.update(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SeatAssign.this, "Seat Assigned", Toast.LENGTH_SHORT).show();
                                Log.d("TAG","onSuccess : user profile is created for " + uniqueSeatId);
                            }
                        });

                        DocumentReference documentReference2=fStore.collection("Verified Students")
                                .document(mStuIdSeatAssignSpinner.getSelectedItem().toString());
                        Map<String,Object> user2 = new HashMap<>();
                        user2.put("IsAssigned","1");
                        user2.put("Unique_Seat_Id",uniqueSeatId);
                        user2.put("Hall_Id",hallId);
                        user2.put("Floor_No",mFloorNoSeatAssignSpinner.getSelectedItem().toString());
                        user2.put("Room_No",mFloorNoSeatAssignSpinner.getSelectedItem().toString());
                        user2.put("Assigned_Seat",mSeatNoSeatAssignSpinner.getSelectedItem().toString());

                        documentReference2.update(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SeatAssign.this, "Seat Assigned", Toast.LENGTH_SHORT).show();
                                Log.d("TAG","onSuccess : user profile is created for ");
                            }
                        });
                    }
                });
            }
        });
    }
}