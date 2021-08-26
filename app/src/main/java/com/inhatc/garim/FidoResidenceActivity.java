package com.inhatc.garim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.fragment.app.FragmentActivity;

public class FidoResidenceActivity extends FragmentActivity {

    private Button btnContinue;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_residence);

        // Button component
        btnContinue = (Button) findViewById(R.id.btnContinue);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // btnContinue 클릭 이벤트
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ElectronicSignatureActivity.class);
                startActivity(intent);
            }
        });
    }
}