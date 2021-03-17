package com.example.simpleapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreAdapter extends FirestorePagingAdapter<SeatModel, FirestoreAdapter.SeatModelHolder> {

    private OnListItemClick onListItemClick;

    public FirestoreAdapter(@NonNull FirestorePagingOptions<SeatModel> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull SeatModelHolder holder, int position, @NonNull SeatModel model) {
        holder.seat_Id.setText(model.getUniqueSeatId());
    }

    @NonNull
    @Override
    public SeatModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
        return new SeatModelHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {
            case LOADING_INITIAL:
                Log.d("PAGING_LOG","Loading Initial Page");
                break;
            case LOADING_MORE:
                Log.d("PAGING_LOG","Loading Next Page");
                break;
            case FINISHED:
                Log.d("PAGING_LOG","All Data Loaded");
                break;
            case ERROR:
                Log.d("PAGING_LOG","Total Loading Data");
                break;
            case LOADED:
                Log.d("PAGING_LOG","Total Items Loaded : "+getItemCount());
                break;
        }
    }

    public class SeatModelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView seat_Id;
        public SeatModelHolder(@NonNull View itemView) {
            super(itemView);
            seat_Id=itemView.findViewById(R.id.emptySeatId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
}
