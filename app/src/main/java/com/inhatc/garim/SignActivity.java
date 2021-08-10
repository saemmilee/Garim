package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    public void onClickSigninfo(View view) {
        Intent intent = new Intent(SignActivity.this, SigninfoActivity.class);
        startActivity(intent);
    }
}
