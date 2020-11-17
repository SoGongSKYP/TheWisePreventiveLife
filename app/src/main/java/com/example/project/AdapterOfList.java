package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOfList extends RecyclerView.Adapter<AdapterOfList.ListViewHolder>{
    private ArrayList<RowOfPatient> datalist;

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView patientNumTextView, patientDateTextView;
        public ListViewHolder(@NonNull View v) {
            super(v);
            patientNumTextView = v.findViewById(R.id.patient_num_TextView);
            patientDateTextView = v.findViewById(R.id.patient_date_TextView);
        }
    }

    public AdapterOfList(ArrayList<RowOfPatient> data){
        this.datalist = data;
    }

    @NonNull
    @Override
    public AdapterOfList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_manager_list, parent, false);
        ListViewHolder vh = new ListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfList.ListViewHolder holder, int position) {
        holder.patientNumTextView.setText("확진자 "+ datalist.get(position).getPatientNum());
        holder.patientDateTextView.setText(datalist.get(position).getConfirmDate());
    }

    @Override
    public int getItemCount() {
        return (null != datalist ? datalist.size() : 0);
    }
}
