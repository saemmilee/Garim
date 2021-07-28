package com.inhatc.garim;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrdinanceStatusActivity extends AppCompatActivity {

    Spinner searchYear;
    Spinner searchCity;
    Spinner searchCountry;
    Spinner statusSignature;
    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinancestatus);

        searchYear = (Spinner) findViewById(R.id.searchYear);
        searchCity = (Spinner) findViewById(R.id.searchCity);
        searchCountry = (Spinner) findViewById(R.id.searchCountry);
        statusSignature = (Spinner) findViewById(R.id.statusSignature);

        //년도
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(
                this, R.array.searchYear, android.R.layout.simple_spinner_dropdown_item);
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
                this, R.array.searchCity, android.R.layout.simple_spinner_dropdown_item);
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
            Toast.makeText(this, "selectedCity:: null", Toast.LENGTH_SHORT).show();
        }else if("Seoul".equals(selectedCity)) {
            //시군구
            ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(
                    this, R.array.searchCountrySeoul, android.R.layout.simple_spinner_dropdown_item);
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
                this, R.array.statusSignature, android.R.layout.simple_spinner_dropdown_item);
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

    }
}
