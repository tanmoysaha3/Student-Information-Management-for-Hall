package com.example.simpleapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFloor extends AppCompatActivity {

    //Spinner mHallIdSpinner;
    TextView mShowPreCreatedFloorNo;
    EditText mCreatedFloorsNumber;
    Button mCreateNewFloors;
    FirebaseFirestore fStore;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_floor);

        mShowPreCreatedFloorNo=findViewById(R.id.showPreCreatedFloorsNo);
        mCreatedFloorsNumber=findViewById(R.id.createdFloorsNumber);
        mCreateNewFloors=findViewById(R.id.createNewFloors);

        fStore = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = fStore.collection("Halls");
        Spinner spinner = (Spinner) findViewById(R.id.hallIdSpinner);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("Hall_Id");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Task<QuerySnapshot> docRef = fStore.collection("Halls").document(selectedItem).collection("Floors").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    count = querySnapshot.size();
                                    mShowPreCreatedFloorNo.setText(String.valueOf(count));
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCreateNewFloors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hallId=spinner.getSelectedItem().toString();
                for(int i=1; i<=Integer.parseInt(mCreatedFloorsNumber.getText().toString());i++) {
                    String x = String.valueOf(i+count);
                    DocumentReference documentReference = fStore.collection("Halls").document(hallId)
                            .collection("Floors").document(x);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Floor_No", x);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateFloor.this, "Floor Created", Toast.LENGTH_SHORT).show();
                            Log.d("TAG","onSuccess : user profile is created for " + x);
                        }
                    });
                }
            }
        });
    }
}