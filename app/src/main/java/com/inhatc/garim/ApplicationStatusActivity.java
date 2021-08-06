package com.inhatc.garim;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationStatusActivity extends FragmentActivity {

    private MypageFragment mypageFragment;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationstatus);

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
