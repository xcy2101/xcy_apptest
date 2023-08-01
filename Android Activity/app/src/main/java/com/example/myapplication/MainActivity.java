package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    contact_SQLiteOpenHelper dbHelper;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    private Button btnlogin,rbtnlogin;
    private EditText etAccount,etPass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        btnlogin=findViewById(R.id.btn_log);
        etAccount=findViewById(R.id.et_account);
        etPass=findViewById(R.id.et_password);
        rbtnlogin=findViewById(R.id.r_btn_login);

        dbHelper = new contact_SQLiteOpenHelper(this);

        rbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPass.getText().toString();

                if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> userData = dbHelper.getData();
                    boolean judge = false;  //判断是否相等
                    for (int i = 0; i < userData.size(); i++) {
                        User user=userData.get(i);
                        if (account.equals(user.getAccount()) && password.equals(user.getPassword())){
                            judge=true;
                            break;
                        }else{
                            judge=false;
                        }
                    }
                    if (judge){
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        Intent intent_login =new Intent();
                        intent_login.setClass(MainActivity.this,ContactActivity.class);
                        startActivity(intent_login);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

}





