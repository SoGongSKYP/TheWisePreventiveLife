package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterOfList extends RecyclerView.Adapter<AdapterOfList.ListViewHolder>{

    public interface OnListClickListener{
        void onListItemCick(View v, int pos);
    }
    private OnListClickListener listClickListener = null;
    public void setOnListClickListenter(OnListClickListener listenter){
        this.listClickListener = listenter;
    }

    private ArrayList<RowOfPatient> datalist;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView patientNumTextView, patientDateTextView;
        public ListViewHolder(@NonNull View v) {
            super(v);
            patientNumTextView = v.findViewById(R.id.patient_num_TextView);
            patientDateTextView = v.findViewById(R.id.patient_date_TextView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(listClickListener != null){
                            listClickListener.onListItemCick(view, pos);
                        }
                    }
                }
            });
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

        String dateFromData = datalist.get(position).getConfirmDate();
        String date = dateFromData.substring(0, 4)+"년 "+dateFromData.substring(5, 7)+"월 "+dateFromData.substring(8,10)+"일";
        holder.patientDateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return (null != datalist ? datalist.size() : 0);
    }
}
