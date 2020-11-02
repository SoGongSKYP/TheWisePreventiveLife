package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

public class AdapterOfDiagnosis extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public static class SubmitViewHolder extends RecyclerView.ViewHolder {
        public Button SubmitButton;
        public SubmitViewHolder(View v){
            super(v);
            SubmitButton = v.findViewById(R.id.selfdiagnosis_submit_Button);
        }
    }

    public AdapterOfDiagnosis(ArrayList<String> data){
        this.questionList = data;
    }

    @Override
    public int getItemViewType(int position){
        if (position == questionList.size())
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_FOOTER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selfdiagnosis_footer, parent, false);
            holder = new SubmitViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selfdiagnosis_question, parent, false);
            holder = new DiagnosisViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<String> yesList = new ArrayList<>();
        /*question row*/
        if (holder instanceof DiagnosisViewHolder){
            final DiagnosisViewHolder Qholder = (DiagnosisViewHolder) holder;
            Qholder.QuestionTextView.setText(questionList.get(position));
            Qholder.YesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    yesList.add(Integer.toString(position));
                    Qholder.YesButton.setBackgroundColor(Color.parseColor("#E1E1E1"));
                    Qholder.NoButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            });
            Qholder.NoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (yesList.contains(Integer.toString(position-1))){
                        yesList.remove(Integer.toString(position-1));
                        Qholder.YesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        Qholder.NoButton.setBackgroundColor(Color.parseColor("#E1E1E1"));
                    }
                }
            });
        }
        /*footer row*/
        else if(holder instanceof SubmitViewHolder){
            SubmitViewHolder Bholder = (SubmitViewHolder) holder;
            Bholder.SubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PageOfSelfDiagnosisResult.class);
                    intent.putExtra("result", yesList);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (null != questionList ? questionList.size()+1 : 0);
    }
}
