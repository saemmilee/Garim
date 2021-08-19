package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    private TextView txtStart;
    private TextView txtEnd;
    private TextView txtTitle;
    private TextView txtReason;
    private TextView txtContent;
    private ImageButton btnSignInfo;

    private String ordinanceNum;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        txtStart = (TextView)findViewById(R.id.txtStart);
        txtEnd = (TextView)findViewById(R.id.txtEnd);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtReason = (TextView)findViewById(R.id.txtReason);
        txtContent = (TextView)findViewById(R.id.txtContent);
        btnSignInfo = (ImageButton)findViewById(R.id.btnSignInfo);

        txtReason.setMovementMethod(new ScrollingMovementMethod());
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        database = FirebaseDatabase.getInstance();  //firebase db연동
        databaseReference = database.getReference("ordinance");  //db 테이블 연결

        Intent SignIntent = getIntent();
        ordinanceNum = SignIntent.getStringExtra("ordinanceNum");
        Log.d("Tag ", "ordinanceNum: " + ordinanceNum + "_" + ordinanceNum.getClass().getName());

        
        //num으로 조례 가져오기
        databaseReference.orderByChild("num")
                .equalTo(Integer.parseInt(ordinanceNum)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDateChange");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {  //반복문으로 데이터리스트 추출

                    Log.d(TAG, "onDateChange_for");

                    Ordinance ordinance = snapshot.getValue(Ordinance.class);  //만들어뒀던 객체에 데이터 담기

                    System.out.println("ordinanceList:: " + ordinance);

                    txtStart.setText(ordinance.getTerm().substring(0, 8));
                    txtEnd.setText(ordinance.getTerm().substring(9, 17));
                    txtTitle.setText(ordinance.getTitle());
                    txtReason.setText(ordinance.getReason());
                    txtContent.setText(ordinance.getOrdinance());

                    if("no".equals(ordinance.getAvailability())) {
                        btnSignInfo.setEnabled(false);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("OrdianceStatusFragment", String.valueOf(databaseError.toException()));  //에러 출력

            }


        });
    }
    

    public void onClickSigninfo(View view) {
        Intent intent = new Intent(SignActivity.this, SigninfoActivity.class);
        startActivity(intent);
    }
}
