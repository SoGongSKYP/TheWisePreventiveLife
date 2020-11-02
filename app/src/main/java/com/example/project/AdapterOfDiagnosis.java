package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOfDiagnosis extends RecyclerView.Adapter<AdapterOfDiagnosis.DiagnosisViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private ArrayList<String> questionList;

    public static class DiagnosisViewHolder extends RecyclerView.ViewHolder {
        public Button YesButton;
        public Button NoButton;
        public TextView QuestionTextView;
        public DiagnosisViewHolder(View v) {
            super(v);
            YesButton = v.findViewById(R.id.selfdiagnosis_YES_Button);
            NoButton = v.findViewById(R.id.selfdiagnosis_NO_Button);
            QuestionTextView = v.findViewById(R.id.selfdiagnosis_question_TextView);
        }
    }

    public AdapterOfDiagnosis(ArrayList<String> data){
        this.questionList = data;
    }

    public DiagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selfdiagnosis_question, parent, false);
        return new DiagnosisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfDiagnosis.DiagnosisViewHolder holder, final int position) {
       holder.QuestionTextView.setText(questionList.get(position));

       final ArrayList<String> yesList = new ArrayList<>();
       holder.YesButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               yesList.add(Integer.toString(position));
           }
       });

       holder.NoButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               if (yesList.contains(Integer.toString(position))){
                   yesList.remove(Integer.toString(position));
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return (null != questionList ? questionList.size() : 0);
    }
}
