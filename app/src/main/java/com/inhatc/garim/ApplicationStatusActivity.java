package com.inhatc.garim;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApplicationStatusActivity extends FragmentActivity {

    private static final String TAG = "App_StatusActivity";

    private MypageFragment mypageFragment;
    private ImageButton btnBack;
    private String get_writer;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ordinance> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationstatus);

        mypageFragment = new MypageFragment();
        btnBack = (ImageButton)findViewById(R.id.btnBack);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();  //Ordinance객체 어댑터로 담을 리스트

        database = FirebaseDatabase.getInstance();  //firebase db연동
        databaseReference = database.getReference("ordinance");  //db 테이블 연결

        GetUser();

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("move", "backMypage");
                startActivity(intent);
            }
        });

        databaseReference.orderByChild("writer")
                .equalTo(get_writer).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //firebase data받아오기
                arrayList.clear();  //기존배열 초기화

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

                    Log.d(TAG, "onDateChange_for");
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
                Log.e(TAG, String.valueOf(databaseError.toException()));  //에러 출력
            }
        });
        adapter = new ApplicationAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    private void GetUser(){
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        System.out.println(email);

        // @를 기준으로 사용자 ID 가져오기
        get_writer = email.substring(0, email.indexOf("@"));
    }
}
