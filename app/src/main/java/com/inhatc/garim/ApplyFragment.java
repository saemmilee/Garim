package com.inhatc.garim;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class ApplyFragment extends Fragment {

//    public WriteFragment() {
//        // Required empty public constructor
//    }

    // file upload
    private Uri filePath;
    private final int PICK_PDF_CODE = 2342;
    String get_file;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_apply, container, false);

        // Button component
        Button btnApply = (Button)v.findViewById(R.id.btnApply);
        Button btnFile = (Button)v.findViewById(R.id.btnFile);

        // EditText component
        EditText txtFile = (EditText) v.findViewById(R.id.txtFile);

        // RadioGroup component
        final RadioGroup rg = (RadioGroup)v.findViewById(R.id.radioGroup);

        // btnFile 버튼 이벤트
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf();
            }
        });

        // btnApply 버튼 이벤트
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText component
                final EditText content = (EditText)v.findViewById(R.id.txtContent1);
                final EditText reason = (EditText)v.findViewById(R.id.txtContent2);
                final EditText title = (EditText)v.findViewById(R.id.txtOrdinance);

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
                    intent.putExtra("file", get_file);
                    startActivity(intent);
                }

            }
        });

        return v;
    }

    // pdf 선택
    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                filePath=data.getData();
                get_file = String.valueOf(filePath);
                System.out.println("ApplyFragment_Uri : " +  get_file);
            } else
                Toast.makeText(getActivity(), "NO FILE CHOSEN", Toast.LENGTH_SHORT).show();
        }

    }
}