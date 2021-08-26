package com.inhatc.garim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class FidoFragment extends Fragment {

    private TextView txtUserID;

    private String get_writer;
    private FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fido, container, false);

        txtUserID = (TextView)v.findViewById(R.id.txtUserID);

        GetUser();
        txtUserID.setText(get_writer);

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