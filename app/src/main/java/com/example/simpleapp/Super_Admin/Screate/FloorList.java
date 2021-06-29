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

public class FloorList extends ArrayAdapter<Fmodel> {
    private Activity context;
    private List<Fmodel> fmodelList;

    public FloorList(Activity context, List<Fmodel> fmodelList) {
        super(context, R.layout.floorlist_layout, fmodelList);
        this.context = context;
        this.fmodelList = fmodelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.floorlist_layout, null, true);

        TextView ftextViewName = (TextView) listViewItem.findViewById(R.id.FLOORNAME);


        Fmodel fmodel = fmodelList.get(position);

        ftextViewName.setText(fmodel.getFloorname());


        return listViewItem;

    }
}
