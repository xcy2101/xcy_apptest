package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    contact_SQLiteOpenHelper myHelper;
    private EditText add_name, add_phone;
    private Button add_btn,chat_btn;

    private ListView view_all;


    protected void onRestart(){
        super.onRestart();
        viewAll(myHelper);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        //new创建对象  传递上下文this
        
        add_name = (EditText) findViewById(R.id.et_name);
        add_phone = (EditText) findViewById(R.id.et_phone);
        add_btn = (Button) findViewById(R.id.btn_add);
        view_all=findViewById(R.id.view_all);
        chat_btn=findViewById(R.id.btn_chat);
        

        myHelper = new contact_SQLiteOpenHelper(ContactActivity.this);
        viewAll(myHelper);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactInfo contactInfo=new ContactInfo(-1,add_name.getText().toString(),add_phone.getText().toString());
                myHelper = new contact_SQLiteOpenHelper(ContactActivity.this);
                String s =myHelper.addOne(contactInfo);
                Toast.makeText(ContactActivity.this,"ADD:"+s,Toast.LENGTH_SHORT).show();
                viewAll(myHelper);
            }
        });


        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(ContactActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });

        view_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactInfo contactInfo= (ContactInfo) adapterView.getItemAtPosition(i);

                AlertDialog.Builder dialog=new AlertDialog.Builder(ContactActivity.this);
                dialog.setTitle("请选择操作");
                dialog.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s =myHelper.deleteOne(contactInfo);
                        Toast.makeText(ContactActivity.this,"DELETE:"+s,Toast.LENGTH_SHORT).show();
                        viewAll(myHelper);
                    }
                });
                dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent= new Intent(ContactActivity.this,UpdateActivity.class);
                        intent.putExtra("id",contactInfo.getId());
                        intent.putExtra("contactName",contactInfo.getContactName());
                        intent.putExtra("Num",contactInfo.getNum());
                        startActivity(intent);
                    }
                });

                dialog.setNeutralButton("聊天", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent= new Intent(ContactActivity.this,MessageActivity.class);
                        intent.putExtra("id",contactInfo.getId());
                        intent.putExtra("contactName",contactInfo.getContactName());
                        intent.putExtra("Num",contactInfo.getNum());
                        startActivity(intent);
                    }
                });


                dialog.create();
                dialog.show();
            }
        });
    }

    private void viewAll(contact_SQLiteOpenHelper myHelper) {
        myHelper = new contact_SQLiteOpenHelper(ContactActivity.this);
        ArrayAdapter<ContactInfo> adapter = new ArrayAdapter<>(ContactActivity.this, android.R.layout.simple_list_item_1,myHelper.getAll());
        view_all.setAdapter(adapter);
    }


}
