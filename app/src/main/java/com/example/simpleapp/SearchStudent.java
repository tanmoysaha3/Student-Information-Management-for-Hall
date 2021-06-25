package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchStudent extends AppCompatActivity {

    EditText mSearchStuId/*, mSearchStuName*/;
    Button mSearchStuDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        mSearchStuId=findViewById(R.id.searchStuId);
        //mSearchStuName=findViewById(R.id.searchStuName);
        mSearchStuDetailsButton=findViewById(R.id.searchStuDetailsButton);

        mSearchStuDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),SeeStuDetails.class);
                i.putExtra("Student_Id",mSearchStuId.getText().toString());
                startActivity(i);
            }
        });
    }
}