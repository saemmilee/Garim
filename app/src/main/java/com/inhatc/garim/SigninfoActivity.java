package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninfoActivity extends AppCompatActivity {

    private static final String TAG = "SigninfoActivity";

    private ImageButton btnBack;
    private TextView txtReason;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinfo);

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        txtReason = (TextView)findViewById(R.id.txtReason);
        txtContent = (TextView)findViewById(R.id.txtContent);

        //스크롤바
        txtReason.setMovementMethod(new ScrollingMovementMethod());
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        //뒤로가기 기능
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onClickSign(View view) {
        Intent intent = new Intent(SigninfoActivity.this, SignActivity.class);
        startActivity(intent);
    }
}
