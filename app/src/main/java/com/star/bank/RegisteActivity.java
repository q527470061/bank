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

import com.star.bank.po.User;
import com.star.bank.utils.JacksonUtil;
import com.star.bank.utils.StreamTool;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.media.AudioTrack.SUCCESS;

@SuppressLint("HandlerLeak")
public class RegisteActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText edtUername, edtPassword, edtUserId, edtPhone, edtAddress;
    private String username, password, userid, phone, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        bindViews();
    }


    public void bindViews() {
        edtUername = findViewById(R.id.reg_username);
        edtPassword = findViewById(R.id.reg_password);
        edtAddress = findViewById(R.id.reg_address);
        edtUserId = findViewById(R.id.reg_id);
        edtPhone = findViewById(R.id.reg_phone);

        //注册事件
        edtUername.setOnFocusChangeListener(this);
        edtPhone.setOnFocusChangeListener(new PhoneFocus());
        edtUserId.setOnFocusChangeListener(new UserIdFocus());
    }


    //handler
    //异常
    final Handler exceptionHandler=new Handler(){
        public void handleMessage(Message msg) {
            Toast.makeText(RegisteActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    //校验用户名
    final Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data = (byte[]) msg.obj;
                String message = new String(data);
                if (message.length() != 0) {
                    Toast.makeText(RegisteActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    //校验手机号
    final Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data = (byte[]) msg.obj;
                String message = new String(data);
                if (message.length() != 0) {
                    Toast.makeText(RegisteActivity.this, message, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(RegisteActivity.this, "手机号有效", Toast.LENGTH_LONG).show();
            }
        }
    };

    //身份证号验证
    final Handler handler3 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data = (byte[]) msg.obj;
                String message = new String(data);
                if (message.length() != 0) {
                    Toast.makeText(RegisteActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    //注册
    final Handler registeHandler=new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data = (byte[]) msg.obj;
                String message = new String(data);
                if (message!="1") {
                    Toast.makeText(RegisteActivity.this, message, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent=new Intent(RegisteActivity.this,MessageActivity.class);
                    intent.putExtra("message","恭喜注册成功");
                    startActivity(intent);
                }
            }
        }
    };

    //Http请求
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        username = edtUername.getText().toString().trim();
        if (hasFocus) {

        } else {
            final String path = "http://192.168.43.141:8080/BankServer/checkName.action?username=" + username;
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

    //手机号请求
    class PhoneFocus implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            phone = edtPhone.getText().toString().trim();
            if (hasFocus) {

            } else {
                final String path = "http://192.168.43.141:8080/BankServer/checkPhone.action?phone=" + phone;
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
    }

    //身份证号请求
    class UserIdFocus implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            userid = edtUserId.getText().toString().trim();
            if (hasFocus) {

            } else {
                final String path = "http://192.168.43.141:8080/BankServer/checkUserId.action?userid=" + userid;
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
    }

    //注册请求
    public void registeRequest(View view) {
        username = edtUername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        phone=edtPhone.getText().toString().trim();
        userid=edtUserId.getText().toString().trim();
        address=edtAddress.getText().toString().trim();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisteActivity.this, "用户名或密码为空", Toast.LENGTH_LONG).show();
            return;
        }
        final User user=new User();
        user.setAddress(address);
        user.setIntegral(0);
        user.setPassword(password);
        user.setPhone(phone);
        user.setTotalmoney(0.0);
        user.setUserid(userid);
        user.setUsername(username);

        final String path = "http://192.168.43.141:8080/BankServer/registe.action";
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                    //设置运行输入,输出:
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    //Post方式不能缓存,需手动设置为false
                    conn.setUseCaches(false);
                    //我们请求的数据:
                    String user_data = JacksonUtil.toJSon(user);

                    //获取输出流
                    OutputStream out = conn.getOutputStream();
                    out.write(user_data.getBytes());
                    out.flush();
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        byte[] data = StreamTool.read(is);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = data;
                        registeHandler.sendMessage(msg);
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
