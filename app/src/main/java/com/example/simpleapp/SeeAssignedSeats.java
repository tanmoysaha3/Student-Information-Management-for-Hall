package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SeeAssignedSeats extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {

    FirebaseFirestore firebaseFirestore;
    RecyclerView mAssignedSeatsRecView;
    FirestoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_assigned_seats);

        firebaseFirestore=FirebaseFirestore.getInstance();
        mAssignedSeatsRecView=findViewById(R.id.assignedSeatsRecView);

        Query query=firebaseFirestore.collection("Empty Seats").whereEqualTo("IsAssigned","1");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<SeatModel> options =new FirestorePagingOptions.Builder<SeatModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<SeatModel>() {
                    @NonNull
                    @Override
                    public SeatModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        SeatModel seatModel=snapshot.toObject(SeatModel.class);
                        String itemId=snapshot.getId();
                        seatModel.setUniqueSeatId(itemId);
                        return seatModel;
                    }
                })
                .build();
        adapter= new FirestoreAdapter(options,this);
        mAssignedSeatsRecView.setHasFixedSize(true);
        mAssignedSeatsRecView.setLayoutManager(new LinearLayoutManager(this));
        mAssignedSeatsRecView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent i=new Intent(getApplicationContext(),SeeSeatDetails.class);
        i.putExtra("Unique Seat Id",snapshot.getId());
        startActivity(i);
        Log.d("ITEM_CLICK","Clicked on item :" + position + "and the ID : " + snapshot.getId());
    }
}