package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WriteResidenceActivity extends FragmentActivity {

    String get_address1;
    String get_address2;
    String address1_address2;

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
        String get_title = get_intent.getExtras().getString("title");

        // 콘솔 출력
        System.out.println("WriteResidence_radio: "+ get_radio);
        System.out.println("WriteResidence_content: " + get_content);
        System.out.println("WriteResidence_reason: " + get_reason);
        System.out.println("WriteResidence_title: " + get_title);

        // btnContinue 클릭 이벤트
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // CheckBox component
                CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);

                // btnContinue을 클릭 시, 체크박스 선택 여부 확인
                if(checkBox.isChecked()){
                    // checked
                    // EditText component
                    EditText name = (EditText)findViewById(R.id.editTextName);
                    EditText registrationNum1 = (EditText)findViewById(R.id.editTextBirth);
                    EditText registrationNum2 = (EditText)findViewById(R.id.editTextNumber);

                    // EditText를 String으로 변환
                    String get_name = name.getText().toString();
                    String get_registrationNum1 = registrationNum1.getText().toString();
                    String get_registrationNum2 = registrationNum2.getText().toString();

                    if (name.length()==0){
                        Toast.makeText(getApplicationContext(),"Please fill out the name.", Toast.LENGTH_SHORT).show();
                    }else if(registrationNum1.length()==0 || registrationNum2.length()==0){
                        Toast.makeText(getApplicationContext(),"Please fill out the resident registration number.", Toast.LENGTH_SHORT).show();
                    }else{
                        // 거주지 확인 진행
                        String forSearch = get_name+"_"+get_registrationNum1+"_"+get_registrationNum2; // 검색을 위한 변수
                        System.out.println(forSearch);

                        // firebase에 저장된사용자의 거주지 정보 가져오기
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("residence");
                        myRef.orderByChild("id").equalTo(forSearch).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){

                                    System.out.println("PARENT: "+ childDataSnapshot.getKey());
                                    // System.out.println("ADDRESS1: "+ childDataSnapshot.child("address1").getValue());
                                    // System.out.println("ADDRESS2: "+ childDataSnapshot.child("address2").getValue());
                                    get_address1 = String.valueOf(childDataSnapshot.child("address1").getValue());
                                    get_address2 = String.valueOf(childDataSnapshot.child("address2").getValue());
                                    address1_address2 = get_address1+"_"+get_address2;

                                    // 거주지 확인 성공
                                    // 오늘 날짜를 가져온 뒤 String으로 변환
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    Date date = new Date();
                                    String dateToStr = dateFormat.format(date);
                                    System.out.println("WriteResidence_time: "+ dateToStr);

                                    // Hashmap 생성
                                    HashMap result = new HashMap<>();
                                    result.put("writer", "smaemmi"); // 글쓴이
                                    result.put("date", dateToStr); // 오늘날짜
                                    result.put("address1", get_address1); //시도
                                    result.put("address2", get_address2); //시군구
                                    result.put("title", get_title); //제목
                                    result.put("ordinance", get_content); // 조례내용
                                    result.put("reason", get_reason); // 제안이유
                                    result.put("certificate", "test.pdf"); // 파일이름
                                    result.put("status", "wait"); // 상태는 wait 고정
                                    result.put("classification", get_radio); // 조례 구분
                                    result.put("term", ""); // 전자서명 기간
                                    result.put("availability", "no"); // 서명가능여부는 NO로 고정
                                    result.put("address1_address2", address1_address2); // 검색을 위한 주소

                                    // firebase 정의 후 Data 저장
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                                    databaseReference.child("ordinance").push().setValue(result);

                                    // Intent 전환
                                    Intent intent = new Intent(getApplicationContext(), WriteFinishActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        // 거주지가 있/없는 경우 다 null
                        // System.out.println("get_address1 : "+get_address1);

                    }

                } else{
                    // unchecked
                    Toast myToast = Toast.makeText(getApplicationContext(),
                            "Please agree to collect and use personal information.", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });

    }
}