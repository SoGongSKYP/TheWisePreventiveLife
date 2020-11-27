package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOfRow extends RecyclerView.Adapter<AdapterOfRow.RowViewHolder> {

    ArrayList<SearchPath> paths;
    public AdapterOfRow(ArrayList<SearchPath> data) {
        this.paths = data;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder{
        TextView startStationTextView, finishStationTextView;
        RecyclerView colRecyclerView;
        public RowViewHolder(@NonNull View v) {
            super(v);
            startStationTextView = v.findViewById(R.id.route_start_TextView);
            finishStationTextView = v.findViewById(R.id.route_finish_TextView);
            //colRecyclerView = v.findViewById(R.id.route_RecyclerView);
        }
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route_result, parent, false);
        RowViewHolder vh = new RowViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        holder.startStationTextView.setText(paths.get(position).getInfo().getFirstStartStation());
        holder.finishStationTextView.setText(paths.get(position).getInfo().getLastEndStation());
    }

    @Override
    public int getItemCount() {
        return (paths != null? paths.size() : 0);
    }


}
