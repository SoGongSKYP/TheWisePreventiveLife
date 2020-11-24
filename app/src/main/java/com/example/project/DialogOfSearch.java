package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;




public class DialogOfSearch extends Dialog {

    private Context context;
    EditText searchEditText;
    ImageButton searchButton;
    Button okButton;
    TextView placeTitleTextView, placeDetailTextView;

    RecyclerView searchRecyclerView;
    AdapterOfSearch adapter;
    ArrayList<Place> resultPlaces = new ArrayList<Place>();
    LinearLayoutManager layoutManager;
    SearchDialogListener searchDialogListener;
    Place selectedRow;

    interface SearchDialogListener{
        void onOKCliked(Place place);
    }

    public void setSearchDialogListener(SearchDialogListener searchDialogListener){
        this.searchDialogListener = searchDialogListener;
    }

    public DialogOfSearch(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_place);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        searchEditText = findViewById(R.id.dialog_search_bar_EditText);
        searchButton = findViewById(R.id.dialog_search_button);
        placeTitleTextView = findViewById(R.id.dialog_search_select_title);
        placeDetailTextView = findViewById(R.id.dialog_search_select_detail);
        okButton = findViewById(R.id.search_dialog_OK_Button);

        searchRecyclerView = findViewById(R.id.dialog_search_RecyclerView);
        dummyData();
        layoutManager = new LinearLayoutManager(getContext());
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setHasFixedSize(true);

        adapter = new AdapterOfSearch(resultPlaces);
        searchRecyclerView.setAdapter(adapter);
        adapter.setOnSearchClickListenter(new AdapterOfSearch.OnSearchClickListener() {
            @Override
            public void onSearchItemCick(View v, int pos) {
                selectedRow = resultPlaces.get(pos);
                placeTitleTextView.setText(selectedRow.get_placeAddress());
                placeDetailTextView.setText(selectedRow.get_placeDetailAddress());
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDialogListener.onOKCliked(selectedRow);
            }
        });

    }

    private void dummyData(){
        Place place1 = new Place("동국대학교 신공학관", "서울특별시 중구", 1.1, 1.5);
        Place place2 = new Place("동국대학교 원흥관", "서울특별시 중구", 1.5, 6.1);
        resultPlaces.add(place1);
        resultPlaces.add(place2);
    }
}
