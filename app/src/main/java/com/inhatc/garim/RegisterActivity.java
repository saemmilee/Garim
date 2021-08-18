package com.inhatc.garim;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


        //아이디 중복체크 기능
        btnCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                itemRef.child("id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);

                        if(value!=null){
                            Toast.makeText(getApplicationContext(),"이미 존재하는 그룹명입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"사용 가능한 id 입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던중 에러 발생 시
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });

//                userRef.addValueEventListener(new ValueEventListener() {
//
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
////                    {
////                        String id = dataSnapshot.child("id").getValue().toString();
////                        if (id.equals(txtID.getText().toString())) {
////                            Toast.makeText(RegisterActivity.this, "이미 사용하고 있는 ID입니다.", Toast.LENGTH_LONG).show();
////                        } else {
////                            Toast.makeText(RegisterActivity.this, "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();
////                        }
////                    }
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        //user 안에 여러개의 자식노드들이 있으므로
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            for(DataSnapshot ds : snapshot.getChildren()) {
//                                if(ds.child("id").equals(txtID.getText().toString())) {
//                                    Toast.makeText(RegisterActivity.this, "이미 사용하고 있는 ID입니다.", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(RegisterActivity.this, "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
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
                            itemRef.child("certification").setValue(txtCertification.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (!txtCertification.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "문자 인증을 완료해주세요.", Toast.LENGTH_LONG).show();

                } else {
                    //입력안했을 때
                    Toast.makeText(RegisterActivity.this, "회원정보를 입력하세요.", Toast.LENGTH_LONG).show();
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
                                // Instant verification is applied and a credential is directly returned.
                                Toast.makeText(RegisterActivity.this, "인증코드가 전송되었습니다. \n60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();

                            }

                            // [START_EXCLUDE]
                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                            }
                            // [END_EXCLUDE]
                        })
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
                }
        });
    }

    private void enableUserManuallyInputCode() {
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


}
