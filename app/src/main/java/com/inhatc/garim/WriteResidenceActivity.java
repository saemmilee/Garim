package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WriteResidenceActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_residence);

        // Button component
        Button btnContinue = (Button)findViewById(R.id.btnContinue);

        // WriteFidoActivity에서 보낸 intent를 가져옴
        Intent get_intent = getIntent();

        // 넘겨온 값
        String get_radio = get_intent.getExtras().getString("radio");
        String get_content = get_intent.getExtras().getString("content");
        String get_reason = get_intent.getExtras().getString("reason");

        // 콘솔 출력
        System.out.println("WriteResidence_radio: "+ get_radio);
        System.out.println("WriteResidence_content: " + get_content);
        System.out.println("WriteResidence_reason: " + get_reason);

        // btnContinue 클릭 이벤트
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // btnContinue을 클릭 시, 거주지 인증 진행

                // 거주지 인증 성공

                // Data 저장
                // Writer / Data / address1 / address2 / content / reason
                // certificate / status / term / title / availability

                // 오늘 날짜를 가져온 뒤 String으로 변환
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = new Date();
                String dateToStr = dateFormat.format(date);
                System.out.println("WriteResidence_time: "+ dateToStr);

                // Hashmap 생성
                HashMap result = new HashMap<>();
                result.put("writer", "smaemmi"); // 글쓴이
                result.put("date", dateToStr); // 오늘날짜
                result.put("address1", "addressTest1"); //시도
                result.put("address2", "addressTest2"); //시군구
                result.put("title", "fido 화이팅"); //제목
                result.put("ordinance", get_content); // 조례내용
                result.put("reason", get_reason); // 제안이유
                result.put("certificate", "test.pdf"); // 파일이름
                result.put("status", "wait"); // 상태는 wait로 고정
                result.put("classification", get_radio); // 조례 구분
                result.put("term", ""); // 전자서명 기간
                result.put("availability", "no"); // 서명가능여부

                //firebase 정의
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.child("ordinance").push().setValue(result);
            }
        });
    }
}
