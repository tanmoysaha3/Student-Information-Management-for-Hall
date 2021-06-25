package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SeeFilteredStudents extends AppCompatActivity implements FirestoreAdapterStudent.OnListItemClick{

    FirebaseFirestore firebaseFirestore;
    RecyclerView mFilteredStudentsRecView;
    FirestoreAdapterStudent adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_filtered_students);

        Intent data=getIntent();
        String Dept = data.getStringExtra("Dept");
        String Year=data.getStringExtra("Year");
        //String Unique=data.getStringExtra("Unique");
        String Hall=data.getStringExtra("Hall_Id");
        String Floor=data.getStringExtra("Floor_No");
        String Room=data.getStringExtra("Room_No");

        Toast.makeText(this, Dept+","+Year+","+Hall+","+Floor+","+Room, Toast.LENGTH_SHORT).show();

        firebaseFirestore=FirebaseFirestore.getInstance();
        mFilteredStudentsRecView=findViewById(R.id.filteredStudentsRecView);

        //Query

        Query query=firebaseFirestore.collection("Verified Students").whereEqualTo("Department",Dept)
                .whereEqualTo("Year",Year).whereEqualTo("Hall_Id",Hall).whereEqualTo("Floor_No",Floor)
                .whereEqualTo("Room_No",Room);
        if(Room.equals("ALL")){
            query=firebaseFirestore.collection("Verified Students").whereEqualTo("Department",Dept)
                    .whereEqualTo("Year",Year).whereEqualTo("Hall_Id",Hall).whereEqualTo("Floor_No",Floor);
            if (Floor.equals("ALL")){
                query=firebaseFirestore.collection("Verified Students").whereEqualTo("Department",Dept)
                        .whereEqualTo("Year",Year).whereEqualTo("Hall_Id",Hall);
                if(Hall.equals("ALL")){
                    query=firebaseFirestore.collection("Verified Students").whereEqualTo("Department",Dept)
                            .whereEqualTo("Year",Year);
                }
            }
        }

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        //Recycler Option
        FirestorePagingOptions<ModelStudent> options =new FirestorePagingOptions.Builder<ModelStudent>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<ModelStudent>() {
                    @NonNull
                    @Override
                    public ModelStudent parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ModelStudent studentModel=snapshot.toObject(ModelStudent.class);
                        String itemId=snapshot.getId();
                        studentModel.setStudentID(itemId);
                        return studentModel;
                    }
                })
                .build();
        adapter= new FirestoreAdapterStudent(options,this);
        mFilteredStudentsRecView.setHasFixedSize(true);
        mFilteredStudentsRecView.setLayoutManager(new LinearLayoutManager(this));
        mFilteredStudentsRecView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent i=new Intent(getApplicationContext(),SeeStuDetails.class);
        i.putExtra("Student Id",snapshot.getId());
        startActivity(i);
        Log.d("ITEM_CLICK","Clicked on item :" + position + "and the ID : " + snapshot.getId());
    }
}