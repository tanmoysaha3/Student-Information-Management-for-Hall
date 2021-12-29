package com.example.simpleapp.SuperAdmin;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.HallAdmin.NotAssignedStudentAdapter;
import com.example.simpleapp.R;
import com.example.simpleapp.Student.Filter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SStudent extends AppCompatActivity {

    ListView recyclerView;
    List<SuperAdminHelperClass> stlist;
    EditText seachbatchmate1;
    Button mcatagory;
    ImageButton back;
    TextView gone;


    DatabaseReference databasestudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_student);

        recyclerView = findViewById(R.id.recyclerView);
        seachbatchmate1 = findViewById(R.id.seachbatchmate1);
        mcatagory = findViewById(R.id.catagory1);
        gone = findViewById(R.id.gone);
        back = findViewById(R.id.back_button);



        databasestudent= FirebaseDatabase.getInstance().getReference("Student Accounts");

        stlist=new ArrayList<>();

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(com.example.simpleapp.SuperAdmin.SStudent.this);
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

        seachbatchmate1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString()!=null){
                    processsearch(s.toString());

                }
                else {
                    processsearch("");
                }

            }
        });








        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SStudent.this);
                        dlg.setTitle("Search by")
                        .setItems(Filter.catagoryy, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String selected=Filter.catagoryy[which];

                                if(selected.equals("Name")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch(s.toString());

                                            }
                                            else {
                                                processsearch("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("Roll No")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch1(s.toString());

                                            }
                                            else {
                                                processsearch1("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("Department")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch2(s.toString());

                                            }
                                            else {
                                                processsearch2("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("District")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch3(s.toString());

                                            }
                                            else {
                                                processsearch3("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("Blood Group")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch4(s.toString());

                                            }
                                            else {
                                                processsearch4("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("Phone No")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch5(s.toString());

                                            }
                                            else {
                                                processsearch5("");
                                            }

                                        }
                                    });
                                }

                                else if(selected.equals("Batch")){
                                    seachbatchmate1.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                            if(s.toString()!=null){
                                                processsearch6(s.toString());

                                            }
                                            else {
                                                processsearch6("");
                                            }

                                        }
                                    });
                                }

                            }


                        }).show();

            }
        });

    }

    private void processsearch(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("fullname").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch1(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("rollno").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch2(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("department").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch3(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("district").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch4(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("bloodgroup").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch5(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("phoneno").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
    }

    private void processsearch6(String s) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("batch").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                stlist.clear();

                for (DataSnapshot hallSnapshot : datasnapshot.getChildren()){
                    SuperAdminHelperClass superAdminHelperClass = hallSnapshot.getValue(SuperAdminHelperClass.class);

                    stlist.add(superAdminHelperClass);


                }

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        stlist=new ArrayList<>();
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

                ArrayAdapter adapter= new NotAssignedStudentAdapter(com.example.simpleapp.SuperAdmin.SStudent.this, stlist);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}