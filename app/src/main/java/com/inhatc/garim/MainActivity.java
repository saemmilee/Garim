package com.inhatc.garim;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    OrdinanceStatusFragment ordinanceStatusFragment;
    ApplyFragment applyFragment;
    MypageFragment mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordinanceStatusFragment = new OrdinanceStatusFragment();
        applyFragment = new ApplyFragment();
        mypageFragment = new MypageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, ordinanceStatusFragment).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.tabOrdinanceStatus:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ordinanceStatusFragment).commit();
                        return true;
                    case R.id.tabApply:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, applyFragment).commit();
                        return true;
                    case R.id.tabMypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mypageFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
