package com.inhatc.garim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText txtName;
    private EditText txtBirth;
    private EditText txtID;
    private Button btnCheck;
    private Button btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        //하나의 노드에 벨류가 여러개
        //실시간 DB 관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //저장시킬 노드 참조객체 가져오기
        DatabaseReference rootRef = firebaseDatabase.getReference();
        DatabaseReference userRef = rootRef.child("users");
        DatabaseReference itemRef = userRef.push(); //자식노드 생김

        txtName = (EditText) findViewById(R.id.txtName);
        txtBirth = (EditText) findViewById(R.id.txtBirth);
        txtID = (EditText) findViewById(R.id.txtID);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //아이디 중복체크 기능
        btnCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                itemRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("id").getValue().equals(txtID.getText().toString())) {
                            Toast.makeText(RegisterActivity.this, "이미 사용하고 있는 ID입니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();
                        }
                        //user 안에 여러개의 자식노드들이 있으므로
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for(DataSnapshot ds : snapshot.getChildren()) {

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //회원가입 기능
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtID.getText().toString().equals("") && !txtBirth.getText().toString().equals("") && !txtName.getText().toString().equals("")) {
                    Register(txtID.getText().toString() + "@naver.com", txtBirth.getText().toString());
                    userRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 아이디와 이름이 공백이 아닐 경우
                            itemRef.child("id").setValue(txtID.getText().toString());
                            itemRef.child("name").setValue(txtName.getText().toString());
                            itemRef.child("birth").setValue(txtBirth.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //입력안했을 때
                    Toast.makeText(RegisterActivity.this, "회원정보를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void Register(String id, String birth) {
        firebaseAuth.createUserWithEmailAndPassword(id, birth).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //회원가입 성공시
                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //계정이 중복된 경우
                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
//
//    String phoneNum = "+821012345678";
//    String testVerificationCode = "123175";
//
//    // Whenever verification is triggered with the whitelisted number,
//// provided it is not set for auto-retrieval, onCodeSent will be triggered.
//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNum)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onCodeSent(String verificationId,
//                                       PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                }
//
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                    // Sign in with the credential
//                    // ...
//                    //인증코드 전송
//                    Toast.makeText(RegisterActivity.this, "인증코드가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    // ...
//                    //인증실패
//                    Toast.makeText(RegisterActivity.this, "인증이 실패되었습니다.", Toast.LENGTH_SHORT).show();
//                }
//            })
//            .build();


}
