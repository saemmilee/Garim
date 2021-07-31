package com.inhatc.garim;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class OrdinanceStatusFragment extends Fragment {

    Context ct;
    Spinner searchYear;
    Spinner searchCity;
    Spinner searchCountry;
    Spinner statusSignature;
    String selectedCity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_ordinancestatus, container, false);
        ct = container.getContext();

        searchYear = (Spinner) v.findViewById(R.id.searchYear);
        searchCity = (Spinner) v.findViewById(R.id.searchCity);
        searchCountry = (Spinner) v.findViewById(R.id.searchCountry);
        statusSignature = (Spinner) v.findViewById(R.id.statusSignature);

        //년도
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(
                ct, R.array.searchYear, android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        searchYear.setAdapter(yearAdapter);

        searchYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //시도
        ArrayAdapter cityAdapter = ArrayAdapter.createFromResource(
                ct, R.array.searchCity, android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        searchCity.setAdapter(cityAdapter);

        searchCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if("".equals(selectedCity)) {
            Toast.makeText(ct, "selectedCity:: null", Toast.LENGTH_SHORT).show();
        }else if("Seoul".equals(selectedCity)) {
            //시군구
            ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(
                    ct, R.array.searchCountrySeoul, android.R.layout.simple_spinner_dropdown_item);
            countryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            searchCountry.setAdapter(countryAdapter);

            searchCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
                //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }

        //서명 현황
        ArrayAdapter signatureAdapter = ArrayAdapter.createFromResource(
                ct, R.array.statusSignature, android.R.layout.simple_spinner_dropdown_item);
        signatureAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusSignature.setAdapter(signatureAdapter);

        statusSignature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return v;
    }
}