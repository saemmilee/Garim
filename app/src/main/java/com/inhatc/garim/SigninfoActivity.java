package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SigninfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinfo);
    }

    public void onClickSign(View view) {
        Intent intent = new Intent(SigninfoActivity.this, SignActivity.class);
        startActivity(intent);
    }
}
