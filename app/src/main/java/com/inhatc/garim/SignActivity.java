package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignActivity extends FragmentActivity {

    private static final String TAG = "SignActivity";

    private TextView txtStart;
    private TextView txtEnd;
    private TextView txtTitle;
    private TextView txtReason;
    private TextView txtContent;
    private ImageButton btnBack;
    private ImageButton btnSignInfo;
    private Button btnElecSign;

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
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnElecSign = (Button)findViewById(R.id.btnElecSign);

        txtReason.setMovementMethod(new ScrollingMovementMethod());
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        database = FirebaseDatabase.getInstance();  //firebase db연동
        databaseReference = database.getReference("ordinance");  //db 테이블 연결

        Intent signIntent = getIntent();
        ordinanceNum = signIntent.getStringExtra("ordinanceNum");
        String moveFrom = signIntent.getStringExtra("moveFrom");
        Log.d(TAG, "{ordinanceNum, moveFrom}: " + "{" + ordinanceNum + ", " + moveFrom + "}");

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //본인이 작성한 조례 조회했을 때
                if(("ApplicationAdapter").equals(moveFrom)) {
                    Intent intent2 = new Intent(getApplicationContext(), ApplicationStatusActivity.class);
                    startActivity(intent2);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("move", "backOrdinanceStatus");
                    startActivity(intent);
                }
            }
        });
        
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
                    
                    if(("yes").equals(ordinance.getAvailability())) {

                        Log.d(TAG, "ordinance_Availability: " + "yes!!");
                        btnElecSign.setBackgroundColor(getColor(R.color.navy));
                        btnElecSign.setTextColor(getColor(R.color.white));

                        btnElecSign.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SignActivity.this, SignatureFidoActivity.class);
                                startActivity(intent);
                            }
                        });

                    } else if(("no").equals(ordinance.getAvailability())){

                        Log.d(TAG, "ordinance_Availability: " + ordinance.getAvailability());

                        //서명 불가능한 조례 서명버튼 비활성화
                        btnElecSign.setEnabled(false);
                        btnElecSign.setBackgroundColor(getColor(R.color.gray));

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
