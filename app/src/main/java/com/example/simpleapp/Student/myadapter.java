package com.example.simpleapp.Student;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.R;

import com.example.simpleapp.SuperAdmin.SuperAdminHelperClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<SuperAdminHelperClass, myadapter.myviewholder> {




    public myadapter(@NonNull FirebaseRecyclerOptions<SuperAdminHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull SuperAdminHelperClass model)
    {
        holder.NSTUDENTNAME.setText(model.getFullname());
        holder.DEPARTMENT.setText(model.getDepartment());
        holder.YEAR.setText(model.getYear());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.not_assigned_student_list,parent,false);
        return new myviewholder(view);
    }




    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView NSTUDENTNAME,DEPARTMENT,YEAR;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            NSTUDENTNAME=(TextView)itemView.findViewById(R.id.NSTUDENTNAME);
            DEPARTMENT=(TextView)itemView.findViewById(R.id.DEPARTMENT);
            YEAR=(TextView)itemView.findViewById(R.id.YEAR);
        }
    }
}
