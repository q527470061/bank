package com.star.bank;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.star.bank.db.UserDao;
import com.star.bank.fragment.CardFragment;
import com.star.bank.po.Card;
import com.star.bank.po.User;
import com.star.bank.utils.JacksonUtil;
import com.star.bank.utils.StreamTool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.media.AudioTrack.SUCCESS;

@SuppressLint("HandlerLeak")
public class AccountActivity extends AppCompatActivity {

    private CardFragment cardFragment;
    private FragmentManager fManager;
    ArrayList<Card> mData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        fManager = getFragmentManager();

        try {
            selectCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //handler
    //异常
    final Handler exceptionHandler=new Handler(){
        public void handleMessage(Message msg) {
            Toast.makeText(AccountActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String json_data=new String(data);
                mData= JacksonUtil.readValue(json_data,new TypeReference<ArrayList<Card>>() { });
                if(mData==null)
                    Toast.makeText(AccountActivity.this, json_data, Toast.LENGTH_LONG).show();
                else{
                    initFragment(mData);
                }
            }
        }
    };

    //http请求
    public void selectCards() throws IOException {
        UserDao userDao=new UserDao(AccountActivity.this);
        User user=userDao.findUser();
        if(user==null){
                    Toast.makeText(AccountActivity.this,"抱歉，您未登录",Toast.LENGTH_LONG);
            return ;
        }


        final String path = "http://192.168.43.141:8080/BankServer/findListCardByUserId.action?userid="+user.getUserid() ;
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
                        handler.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.obj=code;
                        exceptionHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Message msg = Message.obtain();
                /*    msg.what = ERROR2;*//*
                handler.sendMessage(msg);*/

                }
            }
        }.start();
    }

    public void initFragment(ArrayList<Card> mData){
        FragmentTransaction fTransaction = fManager.beginTransaction();
        cardFragment=new CardFragment(AccountActivity.this,mData);
        fTransaction.add(R.id.account_list,cardFragment);
        fTransaction.commit();
    }

}
