package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

public class SignatureFidoActivity extends FragmentActivity {

    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_fido);

        // Button component
        btnContinue = (Button)findViewById(R.id.btnContinue);

        // ApplyFragment에서 보낸 intent를 가져옴
        //Intent get_intent = getIntent();

        // btnContinue 클릭 이벤트
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // btnContinue을 클릭 시, Fido 본인 인증 진행

                // 본인 인증 성공
                // intent 생성 후 writeResidenceActivity로 전달
                Intent intent = new Intent(getApplicationContext(), FidoResidenceActivity.class);
                startActivity(intent);
            }
        });

    }
}
