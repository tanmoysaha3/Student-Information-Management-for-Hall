package com.example.simpleapp.SuperAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.R;

import java.util.List;

public class SHallAdminAdapter extends RecyclerView.Adapter<SHallAdminAdapter.SHallViewHolder> {

    private Context mCtx;
    private List<com.example.simpleapp.SuperAdmin.SHallAdminModel> hmList;

    public SHallAdminAdapter(Context mCtx, List<com.example.simpleapp.SuperAdmin.SHallAdminModel> hmList) {
        this.mCtx = mCtx;
        this.hmList = hmList;
    }

    @NonNull
    @Override
    public SHallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_halladminlist,parent,false);
        return new SHallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SHallViewHolder holder, int position) {
        com.example.simpleapp.SuperAdmin.SHallAdminModel hm = hmList.get(position);
        holder.textViewName.setText(hm.fullname);
        holder.textViewName1.setText(hm.assignedhall);
    }

    @Override
    public int getItemCount() {
        return hmList.size();
    }

    class SHallViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewName1;

        public SHallViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.HALLADMINNAME);
            textViewName1 = itemView.findViewById(R.id.ASSIGNHALLNAME);

        }
    }
}
