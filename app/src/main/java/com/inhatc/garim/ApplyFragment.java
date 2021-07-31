package com.inhatc.garim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ApplyFragment extends Fragment {

//    public WriteFragment() {
//        // Required empty public constructor
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ordinancestatus, container, false);
        // Inflate the layout for this fragment
        return v;
    }
}