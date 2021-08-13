package com.inhatc.garim;

import android.content.Context;
import android.icu.text.SymbolTable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdinanceStatusFragment extends Fragment {

    private static final String TAG = "OrdinanceStatusFragment";

    private Context context;
    private Spinner searchYear;
    private Spinner searchCity;
    private Spinner searchCountry;
    private Spinner statusSignature;
    private String selectedCity;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ordinance> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull  ViewGroup container,
                             @NonNull  Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ordinancestatus, container, false);
        context = container.getContext();

        searchYear = (Spinner) view.findViewById(R.id.searchYear);
        searchCity = (Spinner) view.findViewById(R.id.searchCity);
        searchCountry = (Spinner) view.findViewById(R.id.searchCountry);
        statusSignature = (Spinner) view.findViewById(R.id.statusSignature);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();  //Ordinance객체 어댑터로 담을 리스트

        database = FirebaseDatabase.getInstance();  //firebase db연동
        databaseReference = database.getReference("ordinance");  //db 테이블 연결

        //년도
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(
                context, R.array.searchYear, android.R.layout.simple_spinner_dropdown_item);
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
                context, R.array.searchCity, android.R.layout.simple_spinner_dropdown_item);
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
            Toast.makeText(context, "selectedCity:: null", Toast.LENGTH_SHORT).show();
        }else if("Seoul".equals(selectedCity)) {
            //시군구
            ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(
                    context, R.array.searchCountrySeoul, android.R.layout.simple_spinner_dropdown_item);
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
                context, R.array.statusSignature, android.R.layout.simple_spinner_dropdown_item);
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //firebase data받아오기
                arrayList.clear();  //기존배열 초기화

                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

                    Ordinance ordinanceList = snapshot.getValue(Ordinance.class);  //만들어뒀던 객체에 데이터 담기

                    System.out.println("ordinanceList:: " + ordinanceList);
                    //오름차순
                    arrayList.add(0, ordinanceList);  //담은 데이터를 배열에 넣고 리사이클러뷰로 보낼준비
                    Log.d(TAG, "arrayList:: " + arrayList);

                }
                adapter.notifyDataSetChanged();  //리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("OrdianceStatusFragment", String.valueOf(databaseError.toException()));  //에러 출력

            }
        });
        adapter = new OrdinanceAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}