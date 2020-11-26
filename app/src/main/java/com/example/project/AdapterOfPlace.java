package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOfPlace extends RecyclerView.Adapter<AdapterOfPlace.PlaceViewHolder>{

    public class PlaceViewHolder extends RecyclerView.ViewHolder{
        private TextView placeTitleTextView, visitDateTextView;
        private ImageButton deleteButton;
        public PlaceViewHolder(@NonNull View v) {
            super(v);
            placeTitleTextView = v.findViewById(R.id.visit_place_title_TextView);
            visitDateTextView = v.findViewById(R.id.visit_place_date_TextView);
            deleteButton = v.findViewById(R.id.row_visit_delete_Button);
        }
    }


    private ArrayList<VisitPlace> placeList = new ArrayList<VisitPlace>();
    public AdapterOfPlace(ArrayList<VisitPlace> data) {
        this.placeList = data;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_patient_visit, parent, false);
        AdapterOfPlace.PlaceViewHolder vh = new AdapterOfPlace.PlaceViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.placeTitleTextView.setText(placeList.get(position).getVisitPlace().get_placeAddress());
        holder.visitDateTextView.setText(placeList.get(position).getVisitDate());

        // 여기서 리스트를 없앤다면, 실제 Entity와 DB에서도
        // 해당 내용이 업데이트가 되어서 사라져야 함
        // 이 부분을 어떻게 구현할 것인가?
    }

    @Override
    public int getItemCount() {
        return (placeList != null? placeList.size() : 0);
    }


}
