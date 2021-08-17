package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

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

                // 저장
                // Writer / Data / address1 / address2 / content / reason
                // certificate / status / term / title / availability
            }
        });
    }
}
