package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ApplyFragment extends Fragment {

//    public WriteFragment() {
//        // Required empty public constructor
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_apply, container, false);

        // Button component
        Button btnApply = (Button)v.findViewById(R.id.btnApply);
        Button btnFile = (Button)v.findViewById(R.id.btnFile);

        // RadioGroup component
        final RadioGroup rg = (RadioGroup)v.findViewById(R.id.radioGroup);

        // btnFile 버튼 이벤트
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일 선택
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,10);
            }
        });

        // btnApply 버튼 이벤트
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText component
                final EditText content = (EditText)v.findViewById(R.id.txtContent1);
                final EditText reason = (EditText)v.findViewById(R.id.txtContent2);
                final EditText title = (EditText) v.findViewById(R.id.txtOrdinance);

                if(title.length()==0 || reason.length()==0 || content.length()==0){
                    Toast.makeText(getActivity(),"Fill in the blanks, please.",Toast.LENGTH_SHORT).show();
                }else{
                    // RadioGroup 값 추출 후 String으로 변환
                    int id = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton)v.findViewById(id);
                    String get_radio = rb.getText().toString();

                    // EditText를 String으로 변환
                    String get_content = content.getText().toString();
                    String get_reason = reason.getText().toString();
                    String get_title = title.getText().toString();

                    // Intent 생성
                    Intent intent = new Intent(getContext(),WriteFidoActivity.class);
                    intent.putExtra("radio", get_radio);
                    intent.putExtra("content", get_content);
                    intent.putExtra("reason", get_reason);
                    intent.putExtra("title", get_title);
                    startActivity(intent);
                }

            }
        });

        return v;
    }
}