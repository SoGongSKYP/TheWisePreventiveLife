package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageOfList extends Fragment {
    private Spinner bigLocSpinner, smallLocSpinner;
    private ArrayAdapter bigAdapter, smallAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_list, container, false);

        bigLocSpinner = v.findViewById(R.id.list_big_Spinner);
        smallLocSpinner = v.findViewById(R.id.list_small_Spinner);

        bigAdapter = ArrayAdapter.createFromResource(getContext(), R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);

        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.seoul_array, android.R.layout.simple_spinner_dropdown_item);
        smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallLocSpinner.setAdapter(smallAdapter);

        SpinnerAction();

        return v;
    }

    private void SpinnerAction(){
        bigLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.seoul_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 1:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.busan_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 2:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.daegu_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 3:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.incheon_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 4:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gwangju_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 5:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.daejeon_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 6:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ulsan_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 7:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sejong_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 8:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeonggi_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 9:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gangwondo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 10:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chungcheongbukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 11:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chungcheongnamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 12:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeollabukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 13:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeollanamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 14:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeongsasngbukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 15:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeongsangnamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 16:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeju_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
                smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smallLocSpinner.setAdapter(smallAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
