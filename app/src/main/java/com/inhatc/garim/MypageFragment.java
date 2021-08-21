package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MypageFragment extends Fragment {

    private Button btnSignature;
    private Button btnStatus;
    private TextView txtUserID;

    private String get_writer;

    private FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        btnSignature = (Button) v.findViewById(R.id.btnSignature);
        btnStatus = (Button) v.findViewById(R.id.btnStatus);
        txtUserID = (TextView)v.findViewById(R.id.txtUserID);

        GetUser();
        txtUserID.setText(get_writer);

        btnSignature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElectronicSignatureActivity.class);
                startActivity(intent);
            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplicationStatusActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void GetUser(){
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        System.out.println(email);

        // @를 기준으로 사용자 ID 가져오기
        get_writer = email.substring(0, email.indexOf("@"));
    }
}