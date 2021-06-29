package com.example.simpleapp.Super_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.HallAdmin.Create.HSeat3;
import com.example.simpleapp.HallAdmin.Create.NotAssignedStudents;
import com.example.simpleapp.HallAdmin.NotAssignedStudentAdapter;
import com.example.simpleapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SStudent extends AppCompatActivity {

    ListView recyclerView;
    List<SuperAdminHelperClass> stlist;

    DatabaseReference databasestudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_student);

        recyclerView = findViewById(R.id.recyclerView);


        databasestudent= FirebaseDatabase.getInstance().getReference("Student Accounts");

        stlist=new ArrayList<>();

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(SStudent.this);
                bottomSheetDialog.setContentView(R.layout.studentdetails_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                bottomSheetDialog.setCancelable(false);
                bottomSheetDialog.show();


                final TextView name=bottomSheetDialog.findViewById(R.id.nameField1);
                final TextView district=bottomSheetDialog.findViewById(R.id.districtField1);
                final TextView paddress=bottomSheetDialog.findViewById(R.id.presentaddressField1);
                final TextView blood=bottomSheetDialog.findViewById(R.id.bloodField1);
                final TextView religion=bottomSheetDialog.findViewById(R.id.religionField1);
                final TextView department=bottomSheetDialog.findViewById(R.id.departmentField1);
                final TextView batch=bottomSheetDialog.findViewById(R.id.batchField1);
                final TextView year=bottomSheetDialog.findViewById(R.id.yearField1);
                final TextView roll=bottomSheetDialog.findViewById(R.id.rollField1);
                final TextView hall=bottomSheetDialog.findViewById(R.id.hallnameField1);
                final TextView floor=bottomSheetDialog.findViewById(R.id.floorField1);
                final TextView room=bottomSheetDialog.findViewById(R.id.roomField1);
                final TextView seat=bottomSheetDialog.findViewById(R.id.seatField1);
                final TextView phone=bottomSheetDialog.findViewById(R.id.phoneField1);
                final TextView email=bottomSheetDialog.findViewById(R.id.emailField1);
                final ImageButton close=bottomSheetDialog.findViewById(R.id.back_button);




                SuperAdminHelperClass superAdminHelperClass=stlist.get(position);

                name.setText(superAdminHelperClass.getFullname());
                district.setText(superAdminHelperClass.getDistrict());
                paddress.setText(superAdminHelperClass.getPresentaddress());
                blood.setText(superAdminHelperClass.getBloodgroup());
                religion.setText(superAdminHelperClass.getRiligion());
                department.setText(superAdminHelperClass.getDepartment());
                batch.setText(superAdminHelperClass.getBatch());
                roll.setText(superAdminHelperClass.getRollno());
                year.setText(superAdminHelperClass.getYear());
                hall.setText(superAdminHelperClass.getHallname());
                floor.setText(superAdminHelperClass.getFloorname());
                room.setText(superAdminHelperClass.getRoomno());
                seat.setText(superAdminHelperClass.getSeatno());
                phone.setText(superAdminHelperClass.getPhoneno());
                email.setText(superAdminHelperClass.getEmail());





                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();

                    }
                });


            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databasestudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(SStudent.this, stlist);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}