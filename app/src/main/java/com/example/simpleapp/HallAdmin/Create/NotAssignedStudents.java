package com.example.simpleapp.HallAdmin.Create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.HallAdmin.NotAssignedStudentAdapter;
import com.example.simpleapp.R;

import com.example.simpleapp.SuperAdmin.SuperAdminHelperClass;
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

public class NotAssignedStudents extends AppCompatActivity {

    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_NAME = "student_name";


    ListView recviewnst;
    List<SuperAdminHelperClass> stlist;

    DatabaseReference databasestudent,Databasestudent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_assigned_students);

        recviewnst=(ListView)findViewById(R.id.recviewnst);

        databasestudent= FirebaseDatabase.getInstance().getReference("Student Accounts");
        Query query1 = FirebaseDatabase.getInstance().getReference("Student Accounts")
                .orderByChild("assignedhall").equalTo("Not Assigned");
        query1.addListenerForSingleValueEvent(valueEventListener);



        stlist=new ArrayList<>();




        recviewnst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(NotAssignedStudents.this);
                bottomSheetDialog.setContentView(R.layout.assign_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                bottomSheetDialog.show();

                Intent intent = getIntent();

                String seatname = intent.getStringExtra(HSeat3.SEAT_NAME);
                String hallname = intent.getStringExtra(HSeat3.HALL_NAME);
                String floorname = intent.getStringExtra(HSeat3.FLOOR_NAME);
                String roomname = intent.getStringExtra(HSeat3.ROOM_NAME);
                String seatid = intent.getStringExtra(HSeat3.SEAT_ID);
                String roomid = intent.getStringExtra(HSeat3.ROOM_ID);
                String floorid = intent.getStringExtra(HSeat3.FLOOR_ID);




                final EditText mhallname=bottomSheetDialog.findViewById(R.id.hallnameassign);
                final EditText mfloorname=bottomSheetDialog.findViewById(R.id.floornameassign);
                final EditText mroomname=bottomSheetDialog.findViewById(R.id.roomnameassign);
                final EditText mseatname=bottomSheetDialog.findViewById(R.id.seatnameassign);
                final EditText roll=bottomSheetDialog.findViewById(R.id.roll);
                final Button mStudentaAssigned = bottomSheetDialog.findViewById(R.id.StudentaAssigned);


                mhallname.setText(hallname);
                mfloorname.setText(floorname);
                mroomname.setText(roomname);
                mseatname.setText(seatname);
                SuperAdminHelperClass superAdminHelperClass=stlist.get(position);
                roll.setText(superAdminHelperClass.getRollno());

                mStudentaAssigned.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        databasestudent= FirebaseDatabase.getInstance().getReference("Student Accounts").child(superAdminHelperClass.getRollno());
                        Map<String,Object> map=new HashMap<>();
                        map.put("hallname",mhallname.getText().toString());
                        map.put("floorname",mfloorname.getText().toString());
                        map.put("roomno",mroomname.getText().toString());
                        map.put("seatno",mseatname.getText().toString());



                        databasestudent.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                       Databasestudent1= FirebaseDatabase.getInstance().getReference("Seats").child(roomid).child(seatname);


                        Map<String,Object> map1=new HashMap<>();
                        map1.put("assignedstudentid",roll.getText().toString());




                        Databasestudent1.updateChildren(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(NotAssignedStudents.this, "Assigned", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                return true;
            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {

            stlist.clear();

            for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                stlist.add(superAdminHelperClass);


            }

            ArrayAdapter adapter= new NotAssignedStudentAdapter(NotAssignedStudents.this, stlist);
            recviewnst.setAdapter(adapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };








}