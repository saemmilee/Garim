package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class MypageFragment extends Fragment {

    Button btnSignature;
    Button btnStatus;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        btnSignature = (Button) v.findViewById(R.id.btnSignature);
        btnStatus = (Button) v.findViewById(R.id.btnStatus);

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
}