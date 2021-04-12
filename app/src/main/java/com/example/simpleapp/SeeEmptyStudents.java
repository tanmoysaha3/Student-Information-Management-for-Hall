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

public class SeeEmptyStudents extends AppCompatActivity implements FirestoreAdapterStudent.OnListItemClick {

    FirebaseFirestore firebaseFirestore;
    RecyclerView mEmptyStudentsRecView;
    FirestoreAdapterStudent adapter;
    String uSeatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_empty_students);

        Intent data = getIntent();
        uSeatId=data.getStringExtra("Unique_Seat_Id");

        firebaseFirestore=FirebaseFirestore.getInstance();
        mEmptyStudentsRecView=findViewById(R.id.emptyStudentsRecView);

        Query query=firebaseFirestore.collection("Verified Students").whereEqualTo("IsAssigned","0");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<StudentModel> options =new FirestorePagingOptions.Builder<StudentModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<StudentModel>() {
                    @NonNull
                    @Override
                    public StudentModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        StudentModel studentModel=snapshot.toObject(StudentModel.class);
                        String itemId=snapshot.getId();
                        studentModel.setStudentID(itemId);
                        return studentModel;
                    }
                })
                .build();
        adapter= new FirestoreAdapterStudent(options,this);
        mEmptyStudentsRecView.setHasFixedSize(true);
        mEmptyStudentsRecView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyStudentsRecView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent i=new Intent(getApplicationContext(),SeeStudentDetails.class);
        i.putExtra("Student Id",snapshot.getId());
        i.putExtra("Unique_Seat_Id",uSeatId);
        startActivity(i);
        Log.d("ITEM_CLICK","Clicked on item :" + position + "and the ID : " + snapshot.getId());
    }
}