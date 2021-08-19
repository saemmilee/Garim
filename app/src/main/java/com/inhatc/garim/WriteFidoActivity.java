package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.fragment.app.FragmentActivity;

public class WriteFidoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_fido);

        // Button component
        Button btnContinue = (Button)findViewById(R.id.btnContinue);

        // ApplyFragment에서 보낸 intent를 가져옴
        Intent get_intent = getIntent();

        // 넘겨온 값
        String get_radio = get_intent.getExtras().getString("radio");
        String get_content = get_intent.getExtras().getString("content");
        String get_reason = get_intent.getExtras().getString("reason");
        String get_title = get_intent.getExtras().getString("title");

        // 콘솔 출력
        System.out.println("WriteFido_radio: "+ get_radio);
        System.out.println("WriteFido_content: " + get_content);
        System.out.println("WriteFido_reason: " + get_reason);
        System.out.println("WriteFido_title: " + get_title);

        // btnContinue 클릭 이벤트
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // btnContinue을 클릭 시, Fido 본인 인증 진행

                // 본인 인증 성공
                // intent 생성 후 writeResidenceActivity로 전달
                Intent intent = new Intent(getApplicationContext(), WriteResidenceActivity.class);
                intent.putExtra("radio", get_radio);
                intent.putExtra("content", get_content);
                intent.putExtra("reason", get_reason);
                intent.putExtra("title", get_title);
                startActivity(intent);
            }
        });

    }
}
