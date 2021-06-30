package com.example.simpleapp.halladmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.adapter.FirestoreAdapterStudent;
import com.example.simpleapp.adapter.ModelStudent;
import com.example.simpleapp.R;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SeeStuList extends AppCompatActivity implements FirestoreAdapterStudent.OnListItemClick {

    FirebaseFirestore firebaseFirestore;
    RecyclerView mEmptyStudentsRecView;
    FirestoreAdapterStudent adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_stu_list);

        Intent data = getIntent();
        String value = data.getStringExtra("Value");

        firebaseFirestore=FirebaseFirestore.getInstance();
        mEmptyStudentsRecView=findViewById(R.id.emptyStudentsRecView);

        //Query
        Query query=firebaseFirestore.collection("Verified Students").whereEqualTo("IsAssigned",value);

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
        mEmptyStudentsRecView.setHasFixedSize(true);
        mEmptyStudentsRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEmptyStudentsRecView.addItemDecoration(new DividerItemDecoration(mEmptyStudentsRecView.getContext(), DividerItemDecoration.VERTICAL));
        mEmptyStudentsRecView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent i=new Intent(getApplicationContext(), SeeStuDetails.class);
        i.putExtra("Student_Id",snapshot.getId());
        startActivity(i);
        Log.d("ITEM_CLICK","Clicked on item :" + position + "and the ID : " + snapshot.getId());
    }
}