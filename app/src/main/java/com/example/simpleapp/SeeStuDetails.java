package com.example.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SeeStuDetails extends AppCompatActivity {

    EditText mStuDetailsStuId;
    Button mSearchStuDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_stu_details);

        mStuDetailsStuId=findViewById(R.id.stuDetailsStuId);
        mSearchStuDetailsButton=findViewById(R.id.searchStuDetailsButton);

        mSearchStuDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),ShowStuDetails.class);
                i.putExtra("vStudentId",mStuDetailsStuId.getText().toString());
                startActivity(i);
            }
        });
    }
}