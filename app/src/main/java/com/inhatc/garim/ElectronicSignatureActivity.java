package com.inhatc.garim;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ElectronicSignatureActivity extends AppCompatActivity {

    private MypageFragment mypageFragment;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronicsignature);

        mypageFragment = new MypageFragment();
        btnBack = (ImageButton)findViewById(R.id.btnBack);

        //Activity > Fragment
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().
//                        replace(R.id.fragment_container, mypageFragment).commit();
//            }
//        });
    }
}