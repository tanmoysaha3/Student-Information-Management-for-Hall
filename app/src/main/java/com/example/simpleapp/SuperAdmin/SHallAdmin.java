package com.example.simpleapp.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.HallAdmin.Create.HSeat3;
import com.example.simpleapp.HallAdmin.Create.NotAssignedStudents;
import com.example.simpleapp.HallAdmin.NotAssignedStudentAdapter;
import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.Screate.Floor;
import com.example.simpleapp.SuperAdmin.Screate.Hall;
import com.example.simpleapp.SuperAdmin.Screate.HallList;
import com.example.simpleapp.SuperAdmin.Screate.Hmodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SHallAdmin extends AppCompatActivity {

    public static final String HALLAdmin_NAME = "hall_admin_name";

    ImageButton back_button;
    ListView mListViewHallAdmin;
    List<SHallAdminModel> halladminList;
    EditText seachbatchmate1;
    DatabaseReference databaseSHallAdmin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hall_admin);

        back_button=findViewById(R.id.back_button);
        seachbatchmate1 = findViewById(R.id.seachbatchmate1);
        mListViewHallAdmin = findViewById(R.id.recyclerView);



        halladminList = new ArrayList<>();


        databaseSHallAdmin = FirebaseDatabase.getInstance().getReference("HallAdmin Accounts");


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        mListViewHallAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SHallAdminModel sHallAdminModel = halladminList.get(position);

                Intent intent = new Intent(getApplicationContext(), AssignHallAdmin.class);

                intent.putExtra(HALLAdmin_NAME, sHallAdminModel.getFullname());
                startActivity(intent);
            }
        });






    }




    @Override
    protected void onStart() {
        super.onStart();

        databaseSHallAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                halladminList.clear();

                for (DataSnapshot halladminSnapshot : datasnapshot.getChildren()){

                    SHallAdminModel sHallAdminModel=halladminSnapshot.getValue(SHallAdminModel.class);

                    halladminList.add(sHallAdminModel);


                }

                ArrayAdapter adapter= new SHallAdminAdapter(SHallAdmin.this, halladminList);
                mListViewHallAdmin.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }







}