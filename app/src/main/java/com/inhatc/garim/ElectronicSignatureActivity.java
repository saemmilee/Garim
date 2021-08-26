package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ElectronicSignatureActivity extends AppCompatActivity {

    private static final String TAG = "Elec_SignatureActivity";

    private MypageFragment mypageFragment;
    private ImageButton btnBack;
    private TextView txtTitle4;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ordinance> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronicsignature);

        mypageFragment = new MypageFragment();
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        txtTitle4 = (TextView)findViewById(R.id.txtTitle4);

        //조회페이지로 이동
        txtTitle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                startActivity(new Intent(ElectronicSignatureActivity.this, SignCancelActivity.class));
            }
        });

//        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        arrayList = new ArrayList<>();  //Ordinance객체 어댑터로 담을 리스트
//
//        database = FirebaseDatabase.getInstance();  //firebase db연동
//        databaseReference = database.getReference("ordinance");  //db 테이블 연결

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("move", "backMypage");
                startActivity(intent);
            }
        });
    }
}
