package com.star.bank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {
    private String message;
    private TextView tv_message=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tv_message=findViewById(R.id.tv_message);

        Intent intent=getIntent();
        message=intent.getStringExtra("message");

        tv_message.setText(message);
    }

    public void messageLogin(View view){
        Intent intent=new Intent(MessageActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void messageMain(View view){
        Intent intent=new Intent(MessageActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
