package com.example.simpleapp.Student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.R;

import com.example.simpleapp.SuperAdmin.SuperAdminHelperClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText seachbatchmate;
    ImageView mainprofileImage;
    ImageButton Studentmore;
    StorageReference storageReference;
    FirebaseUser user;
    List<SuperAdminHelperClass> stlist;
    RecyclerView recyclerView;
    myadapter adapter;
    DatabaseReference databasestudent;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        setTitle("");

        mainprofileImage= findViewById(R.id.mainprofileImage);
        Studentmore= findViewById(R.id.Studentmore);
        recyclerView=findViewById(R.id.recyclerView);
        seachbatchmate=findViewById(R.id.seachbatchmate1);





        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SuperAdminHelperClass> options =
                new FirebaseRecyclerOptions.Builder<SuperAdminHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("department").equalTo("EEE"), SuperAdminHelperClass.class)
                        .build();

        adapter=new myadapter(options);
        recyclerView.setAdapter(adapter);







        mainprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentProfile.class));
            }
        });



        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mainprofileImage);
            }
        });

        databasestudent= FirebaseDatabase.getInstance().getReference("Student Accounts");

        stlist=new ArrayList<>();

        /*recyclerView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(StudentDashboard.this);
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
        })*/

        seachbatchmate.addTextChangedListener(new TextWatcher() {
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
    private void processsearch(String s) {
        FirebaseRecyclerOptions<SuperAdminHelperClass> options =
                new FirebaseRecyclerOptions.Builder<SuperAdminHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student Accounts").orderByChild("department").startAt(s).endAt(s+"\uf8ff"), SuperAdminHelperClass.class)
                        .build();

        adapter=new myadapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter); }








    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }




    /*@Override
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

                ArrayAdapter adapter= new NotAssignedStudentAdapter(StudentDashboard.this, stlist);
                recyclerView1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void logoutstudent(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), StudentLogin.class));
        finish();
    }






}