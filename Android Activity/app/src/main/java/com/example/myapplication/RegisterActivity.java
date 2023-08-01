package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private contact_SQLiteOpenHelper dbHelper;
    private Button btnRegister;
    private EditText retAccount,retPass,retPassConfirm;
    private RadioButton rbAgree;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        retAccount=findViewById(R.id.r_et_account);
        retPass=findViewById(R.id.r_et_password);
        retPassConfirm=findViewById(R.id.r_et_password_confirm);
        rbAgree=findViewById(R.id.rb_agree);
        btnRegister=findViewById(R.id.btn_reg);

        btnRegister.setOnClickListener(this);
        dbHelper =new contact_SQLiteOpenHelper(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String r_account = retAccount.getText().toString();
        String r_password = retPass.getText().toString();
        String r_password_confirm = retPassConfirm.getText().toString();

        if(!TextUtils.isEmpty(r_account) && !TextUtils.isEmpty(r_password) && !TextUtils.isEmpty(r_password_confirm)){
            if (rbAgree.isChecked()){
                if(r_password.equals(r_password_confirm)) {
                    if (dbHelper.add(r_account, r_password)) {//将用户名和密码加入到数据库中
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "该用户名已存在", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this,"请勾选用户协议",Toast.LENGTH_LONG).show();
            }}
        else{
            Toast.makeText(RegisterActivity.this,"用户名和密码不能为空",Toast.LENGTH_LONG).show();
            }

    }
}
