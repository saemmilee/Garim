package com.inhatc.garim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
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
    private EditText txtCertification;
    private Button btnCheck;
    private Button btnRegister;
    private Button btnSend;
    private ImageButton btnBack;

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
        txtCertification =(EditText) findViewById(R.id.txtCertification);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

        //뒤로가기 기능
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //아이디 중복체크 기능
        btnCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!txtID.getText().toString().equals("")) {
                    Check(txtID.getText().toString() + "@naver.com", txtBirth.getText().toString());
                }else {
                    Toast.makeText(RegisterActivity.this, "Please enter your ID.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //회원가입 기능
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtID.getText().toString().equals("") && !txtBirth.getText().toString().equals("") && !txtName.getText().toString().equals("") && !txtCertification.getText().toString().equals("")) {
                    userRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 회원정보가 공백이 아닐경우
                            itemRef.child("id").setValue(txtID.getText().toString());
                            itemRef.child("name").setValue(txtName.getText().toString());
                            itemRef.child("birth").setValue(txtBirth.getText().toString());
                            itemRef.child("certification").setValue(txtCertification.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(RegisterActivity.this, "Your membership registration has been completed.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else if (!txtCertification.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Please complete the message verification.", Toast.LENGTH_LONG).show();

                } else {
                    //입력안했을 때
                    Toast.makeText(RegisterActivity.this, "Please enter your membership information.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                String phoneNumber = "+821122223333";
                String smsCode = "123456";

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

                firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(RegisterActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onCodeSent(String verificationId,
//                                                       PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                RegisterActivity.this.enableUserManuallyInputCode();
//                            }
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                Toast.makeText(RegisterActivity.this, "Authentication code has been sent. \nPlease enter within 60 seconds.", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                            }
                        })
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
                }
        });
    }
    private void Check(String id, String birth) {
        firebaseAuth.createUserWithEmailAndPassword(id, birth).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //회원가입 성공시
                    Toast.makeText(RegisterActivity.this, "The ID is currently available.", Toast.LENGTH_SHORT).show();
                } else {
                    //계정이 중복된 경우
                    Toast.makeText(RegisterActivity.this, "This ID already exists.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
