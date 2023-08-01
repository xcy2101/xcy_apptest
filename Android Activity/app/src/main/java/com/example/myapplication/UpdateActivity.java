package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    TextView update_name,update_phone;
    Button update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_update);

        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        String contactName=intent.getStringExtra("contactName");
        String Num=intent.getStringExtra("Num");

        update_name=findViewById(R.id.update_name);
        update_phone=findViewById(R.id.update_phone);
        update_btn=findViewById(R.id.update_btn);

        update_name.setText(contactName);
        update_phone.setText(Num);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactInfo contactInfo=new ContactInfo(id,update_name.getText().toString(),update_phone.getText().toString());
                contact_SQLiteOpenHelper myHelper= new contact_SQLiteOpenHelper(UpdateActivity.this);
                myHelper.updateOne(contactInfo);
                finish();
            }
        });

    }
}

