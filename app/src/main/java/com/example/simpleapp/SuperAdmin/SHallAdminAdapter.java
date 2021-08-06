package com.example.simpleapp.SuperAdmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleapp.R;
import com.example.simpleapp.SuperAdmin.Screate.Hmodel;

import java.util.List;

public class SHallAdminAdapter extends ArrayAdapter<SHallAdminModel> {

    private Activity context;
    private List<SHallAdminModel> sHallAdminModelList;

    public SHallAdminAdapter(Activity context, List<SHallAdminModel> sHallAdminModelList ){
        super(context, R.layout.recyclerview_halladminlist, sHallAdminModelList);
        this.context = context;
        this.sHallAdminModelList = sHallAdminModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.recyclerview_halladminlist, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.HALLADMINNAME);
        TextView textViewName1 = (TextView) listViewItem.findViewById(R.id.ASSIGNHALLNAME);


        SHallAdminModel sHallAdminModel= sHallAdminModelList.get(position);

        textViewName.setText(sHallAdminModel.getFullname());
        textViewName1.setText(sHallAdminModel.getAssignedhall());


        return listViewItem;

    }
}
