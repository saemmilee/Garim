package com.inhatc.garim;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Spinner;
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
    private Spinner searchCity;
    private Spinner searchCountry;
    private ArrayAdapter<String> arrayAdapter;
    private Button btnSearchCity;

    private String selectedCity;
    private String selectedCountry;
    private String address1_address2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ordinance> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ordinancestatus, container, false);
        context = container.getContext();

        searchCity = (Spinner) view.findViewById(R.id.searchCity);
        searchCountry = (Spinner) view.findViewById(R.id.searchCountry);
        btnSearchCity = (Button) view.findViewById(R.id.btnSearchCity);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();  //Ordinance객체 어댑터로 담을 리스트

        database = FirebaseDatabase.getInstance();  //firebase db연동
        databaseReference = database.getReference("ordinance");  //db 테이블 연결

//        //jh 조회 페이지로 이동
//        Button btnProgress =(Button) view.findViewById(R.id.btnProgress);
//        btnProgress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),SignActivity.class);
//                startActivity(intent);
//            }
//        });

        //시도
        ArrayAdapter cityAdapter = ArrayAdapter.createFromResource(
                context, R.array.searchCity, android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        searchCity.setAdapter(cityAdapter);

        searchCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        initAddressSpinner();

        //시군구 선택
        ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(
                context, R.array.searchCity, android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        searchCountry.setAdapter(countryAdapter);

        searchCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됐는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "선택!!시군구:: " + selectedCountry);
                address1_address2 = selectedCity + "_" + selectedCountry;
                Log.d(TAG, "선택!!지역은:: " + address1_address2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //firebase data받아오기
                arrayList.clear();  //기존배열 초기화

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

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

        btnSearchCity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "도시검색!!!:: " + address1_address2);
                if (("City").equals(selectedCity)) {
                    Toast.makeText(context, "Please choose a city.", Toast.LENGTH_LONG).show();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //firebase data받아오기
                            arrayList.clear();  //기존배열 초기화

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

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
                } else if (("Country").equals(selectedCountry)) {
                    databaseReference.orderByChild("address1")
                            .equalTo(selectedCity).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //firebase data받아오기
                            arrayList.clear();  //기존배열 초기화

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

                                Log.d(TAG, "도시검색!!!:: else for 들어왔니?");
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
                } else {
                    databaseReference.orderByChild("address1_address2")
                            .equalTo(address1_address2).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //firebase data받아오기
                            arrayList.clear();  //기존배열 초기화

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

                                Log.d(TAG, "도시검색!!!:: else for 들어왔니?");
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
                }
            }
        });

        return view;
    }

    private void initAddressSpinner() {
        searchCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "선택!!시도:: " + selectedCity);
                // 시군구, 동의 스피너를 초기화한다.
                switch (position) {
                    case 0:
                        searchCountry.setAdapter(null);
                        break;
                    case 1:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountrySeoul);
                        break;
                    case 2:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryBusan);
                        break;
                    case 3:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryDaegu);
                        break;
                    case 4:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryIncheon);
                        break;
                    case 5:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryGwangju);
                        break;
                    case 6:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryDaejeon);
                        break;
                    case 7:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryUlsan);
                        break;
                    case 8:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountrySejong);
                        break;
                    case 9:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryGyeonggido);
                        break;
                    case 10:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryGangwondo);
                        break;
                    case 11:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryChungcheongbukdo);
                        break;
                    case 12:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryCungcheongnamdo);

                        break;
                    case 13:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryJeollabukdo);
                        break;
                    case 14:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryJeollanamdo);
                        break;
                    case 15:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryGyeongsangbukdo);
                        break;
                    case 16:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryGyeongsangnamdo);
                        break;
                    case 17:
                        setSearchCountrySpinnerAdapterItem(R.array.searchCountryJejudo);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setSearchCountrySpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            searchCountry.setAdapter(null);
            arrayAdapter = null;
        }

        if (searchCity.getSelectedItemPosition() > 1) {
            searchCountry.setAdapter(null);
        }

        arrayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item,
                (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCountry.setAdapter(arrayAdapter);
    }
}