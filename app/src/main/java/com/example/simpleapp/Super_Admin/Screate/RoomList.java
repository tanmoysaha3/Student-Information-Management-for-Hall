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

public class RoomList extends ArrayAdapter<Rmodel> {
    private Activity context;
    private List<Rmodel> rmodelList;

    public RoomList(Activity context, List<Rmodel> rmodelList) {
        super(context, R.layout.roomlist_layout, rmodelList);
        this.context = context;
        this.rmodelList = rmodelList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.roomlist_layout, null, true);

        TextView rtextViewName = (TextView) listViewItem.findViewById(R.id.ROOMNAME);


        Rmodel rmodel = rmodelList.get(position);

        rtextViewName.setText(rmodel.getRoomname());


        return listViewItem;

    }
}