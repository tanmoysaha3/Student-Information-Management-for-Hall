package com.example.simpleapp.Super_Admin.Screate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simpleapp.R;

import java.util.List;

public class HallList extends ArrayAdapter<Hmodel> {
    private Activity context;
    private List<Hmodel> hmodelList;

    public HallList(Activity context, List<Hmodel> hmodelList ){
        super(context, R.layout.halllist_layout, hmodelList);
        this.context = context;
        this.hmodelList = hmodelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.halllist_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.HALLNAME);


        Hmodel hmodel= hmodelList.get(position);

        textViewName.setText(hmodel.getHallname());


        return listViewItem;

    }
}
