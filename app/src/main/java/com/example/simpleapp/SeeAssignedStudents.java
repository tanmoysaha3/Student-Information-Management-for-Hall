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

public class SeeAssignedStudents extends AppCompatActivity implements FirestoreAdapterStudent.OnListItemClick {

    FirebaseFirestore firebaseFirestore;
    RecyclerView mAssignedStudentsRecView;
    FirestoreAdapterStudent adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_assigned_students);

        firebaseFirestore=FirebaseFirestore.getInstance();
        mAssignedStudentsRecView=findViewById(R.id.assignedStudentsRecView);

        Query query=firebaseFirestore.collection("Verified Students").whereEqualTo("IsAssigned","1");

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
        mAssignedStudentsRecView.setHasFixedSize(true);
        mAssignedStudentsRecView.setLayoutManager(new LinearLayoutManager(this));
        mAssignedStudentsRecView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent i=new Intent(getApplicationContext(),SeeStudentDetails.class);
        i.putExtra("Student Id",snapshot.getId());
        startActivity(i);
        Log.d("ITEM_CLICK","Clicked on item :" + position + "and the ID : " + snapshot.getId());
    }
}