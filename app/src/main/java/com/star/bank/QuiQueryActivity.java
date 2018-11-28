package com.star.bank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.star.bank.po.Card;
import com.star.bank.po.User;
import com.star.bank.utils.JacksonUtil;
import com.star.bank.utils.StreamTool;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.media.AudioTrack.SUCCESS;

@SuppressLint("HandlerLeak")
public class QuiQueryActivity extends AppCompatActivity {
    private EditText et_cardid,card_password;
    String cardid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qui_query);
        bindViews();
    }

    //bindView
    public void bindViews(){
        et_cardid=findViewById(R.id.et_cardid);
        card_password=findViewById(R.id.card_password);
    }

    //Hanler
    //异常
    final Handler exceptionHandler=new Handler(){
        public void handleMessage(Message msg) {
            Toast.makeText(QuiQueryActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    final Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String card_data=new String(data);
                Card card= JacksonUtil.readValue(card_data,Card.class);
                if(card==null)
                    Toast.makeText(QuiQueryActivity.this, card_data, Toast.LENGTH_LONG).show();
                else{
                    Intent intent = new Intent(QuiQueryActivity.this,CardContentActivity.class);
                    intent.putExtra("card",card_data);
                    startActivity(intent);
                }
            }
        }
    };

    //http请求
    public void selectCard(View view) {
        cardid=et_cardid.getText().toString().trim();
        password=card_password.getText().toString().trim();
        if (TextUtils.isEmpty(cardid) ||TextUtils.isEmpty(password)) {
            Toast.makeText(QuiQueryActivity.this, "卡号或者密码为空", Toast.LENGTH_LONG).show();
            return ;
        }

        final String path = "http://192.168.43.141:8080/BankServer/findCardById.action?cardid="+cardid+"&password="+password;
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        byte[] data = StreamTool.read(is);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = data;
                        handler1.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.obj=code;
                        exceptionHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Message msg = Message.obtain();
               /* msg.what = ERROR2;
                handler.sendMessage(msg);*/
                }
            }
        }.start();
    }
}
