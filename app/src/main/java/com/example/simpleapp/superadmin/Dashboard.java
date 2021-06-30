package com.example.simpleapp.superadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    TextView mShowRegisteredStuNumber, mShowAssignedStuNumber, mShowWaitingStuNumber, mShowTotalSeatNumber,
            mShowAssignedSeatNumber, mShowAvailableSeatNumber, mShowHallTotalSeatNumber,
            mShowHallAssignedSeatNumber, mShowHallAvailableSeatNumber;
    Spinner mHallIdDashboardSpinner;
    FirebaseFirestore fStore;
    int count;
    int assignedStu, waitingStu, totalStu, assignedSeat, availableSeat, totalSeat, hallAssignedSeat, hallAvailableSeat, hallTotalSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mShowRegisteredStuNumber=findViewById(R.id.showRegisteredStuNumber);
        mShowAssignedStuNumber=findViewById(R.id.showAssignedStuNumber);
        mShowWaitingStuNumber=findViewById(R.id.showWaitingStuNumber);
        mShowTotalSeatNumber=findViewById(R.id.showTotalSeatNumber);
        mShowAssignedSeatNumber=findViewById(R.id.showAssignedSeatNumber);
        mShowAvailableSeatNumber=findViewById(R.id.showAvailableSeatNumber);
        mShowHallTotalSeatNumber=findViewById(R.id.showHallTotalSeatNumber);
        mShowHallAssignedSeatNumber=findViewById(R.id.showHallAssignedSeatNumber);
        mShowHallAvailableSeatNumber=findViewById(R.id.showHallAvailableSeatNumber);
        mHallIdDashboardSpinner=findViewById(R.id.hallIdDashoardSpinner);

        fStore=FirebaseFirestore.getInstance();

        Task<QuerySnapshot> assignedStuRef = fStore.collection("Verified Students").whereEqualTo("IsAssigned","1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            count = querySnapshot.size();
                            mShowAssignedStuNumber.setText(String.valueOf(count));
                            assignedStu=count;
                            Task<QuerySnapshot> waitingStuRef = fStore.collection("Verified Students").whereEqualTo("IsAssigned","0").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                QuerySnapshot querySnapshot = task.getResult();
                                                count = querySnapshot.size();
                                                mShowWaitingStuNumber.setText(String.valueOf(count));
                                                waitingStu=count;
                                                totalStu=assignedStu+waitingStu;
                                                mShowRegisteredStuNumber.setText(String.valueOf(totalStu));
                                            }
                                        }
                                    });
                        }
                    }
                });

        Task<QuerySnapshot> assignedSeatRef = fStore.collection("Created Seats").whereEqualTo("IsAssigned","1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            count = querySnapshot.size();
                            mShowAssignedSeatNumber.setText(String.valueOf(count));
                            assignedSeat=count;
                            Task<QuerySnapshot> availableSeatRef = fStore.collection("Created Seats").whereEqualTo("IsAssigned","0").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                QuerySnapshot querySnapshot = task.getResult();
                                                count = querySnapshot.size();
                                                mShowAvailableSeatNumber.setText(String.valueOf(count));
                                                availableSeat=count;
                                                totalSeat=assignedSeat+availableSeat;
                                                mShowTotalSeatNumber.setText(String.valueOf(totalSeat));
                                            }
                                        }
                                    });
                        }
                    }
                });

        CollectionReference hallsRef = fStore.collection("Halls");
        List<String> halls = new ArrayList<>();
        ArrayAdapter<String> hallAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, halls);
        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHallIdDashboardSpinner.setAdapter(hallAdapter);
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

        mHallIdDashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String halllId=parent.getSelectedItem().toString();
                Task<QuerySnapshot> assignedSeatRef = fStore.collection("Created Seats").whereEqualTo("IsAssigned","1").whereEqualTo("Hall_Id",halllId).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    count = querySnapshot.size();
                                    mShowHallAssignedSeatNumber.setText(String.valueOf(count));
                                    hallAssignedSeat=count;
                                    Task<QuerySnapshot> availableSeatRef = fStore.collection("Created Seats").whereEqualTo("IsAssigned","0").whereEqualTo("Hall_Id",halllId).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        QuerySnapshot querySnapshot = task.getResult();
                                                        count = querySnapshot.size();
                                                        mShowHallAvailableSeatNumber.setText(String.valueOf(count));
                                                        hallAvailableSeat=count;
                                                        hallTotalSeat=hallAssignedSeat+hallAvailableSeat;
                                                        mShowHallTotalSeatNumber.setText(String.valueOf(hallTotalSeat));
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}