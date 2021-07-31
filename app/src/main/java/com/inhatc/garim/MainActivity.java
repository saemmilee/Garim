package com.inhatc.garim;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListFragment listFragment;
    WriteFragment writeFragment;
    MypageFragment mypageFragment;
    OrdinanceStatusFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListFragment();
        writeFragment = new WriteFragment();
        mypageFragment = new MypageFragment();
        homeFragment = new OrdinanceStatusFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.tabHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.tabList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
                        return true;
                    case R.id.tabWrite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, writeFragment).commit();
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
