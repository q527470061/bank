package com.star.bank;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.star.bank.db.UserDao;
import com.star.bank.po.User;
import com.star.bank.utils.JacksonUtil;
import com.star.bank.utils.StreamTool;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.media.AudioTrack.SUCCESS;

@SuppressLint("HandlerLeak")
public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText et_username, et_password;
    private CheckBox cb_save;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        try {
            initPassword();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bindView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_save=findViewById(R.id.cb_save);
        et_username.setOnFocusChangeListener(this);
    }




    //handler
    //异常
    final Handler exceptionHandler=new Handler(){
        public void handleMessage(Message msg) {
            Toast.makeText(LoginActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String user_data=new String(data);
                User user= JacksonUtil.readValue(user_data,User.class);
                if(user==null)
                    Toast.makeText(LoginActivity.this, user_data, Toast.LENGTH_LONG).show();
                else{
                    UserDao userDao=new UserDao(LoginActivity.this);
                    ContentValues contentvalues = new ContentValues();
                    contentvalues.put("userid",user.getUserid());
                    contentvalues.put("username",user.getUsername());
                    contentvalues.put("password",user.getPassword());
                    contentvalues.put("totalmoney",user.getTotalmoney());
                    contentvalues.put("phone",user.getPhone());
                    contentvalues.put("address",user.getAddress());
                    contentvalues.put("integral",user.getIntegral());
                    userDao.insert("user",contentvalues);
                    //登录成功后
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    final Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String message=new String(data);
                if(message.length()!=0) {
                    Toast.makeText(LoginActivity.this, "用户名正确", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void loginHandle(View view) throws IOException {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "用户名或密码为空", Toast.LENGTH_LONG).show();
            return;
        }

        savePassword(username,password);

        final String path = "http://192.168.43.141:8080/BankServer/login.action?username=" + username + "&password=" + password;
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
               /* msg.what = ERROR2;
                handler.sendMessage(msg);*/
                }
            }
        }.start();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
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
                            handler2.sendMessage(msg);
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

    //记住密码
    public void savePassword(String name,String pass) throws IOException {
        boolean saveState=cb_save.isChecked();
        if(saveState){
            XmlSerializer xmlSerializer= Xml.newSerializer();
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(this.getFilesDir(),"user.xml")));
            xmlSerializer.setOutput(bw);
            xmlSerializer.startDocument("utf-8",null);

            //<users>
            xmlSerializer.startTag(null,"users");

            //<user>
            xmlSerializer.startTag(null,"user");

            //<savestate>
            xmlSerializer.startTag(null,"save_state");
            //value
            xmlSerializer.text(String.valueOf(true).toString());
            //</savestate>
            xmlSerializer.endTag(null,"save_state");

            //<name>
            xmlSerializer.startTag(null,"name");
            //value
            xmlSerializer.text(name);
            //</name>
            xmlSerializer.endTag(null,"name");

            //<password>
            xmlSerializer.startTag(null,"password");
            //value
            xmlSerializer.text(pass);
            //</password>
            xmlSerializer.endTag(null,"password");

            //</user>
            xmlSerializer.endTag(null,"user");

            //</users>
            xmlSerializer.endTag(null,"users");

            xmlSerializer.endDocument();

            bw.close();
        }else {
            Log.e("保存按钮的状态",new Boolean(cb_save.isChecked()).toString());
            Log.e("save State",String.valueOf(cb_save.isChecked()));
            File file=new File(this.getFilesDir(),"user.xml");
            file.delete();
        }
    }

    //解析xml,获取用户名密码,并设置登录页面值
    public void initPassword() throws XmlPullParserException, IOException {
        //Pull解析
        XmlPullParserFactory xmlPullParserFactory=XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser=xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new BufferedReader(new FileReader(new File(this.getFilesDir(),"user.xml"))));
        int eventType=xmlPullParser.getEventType();

        while (eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    eventType=xmlPullParser.next();
                    break;
                case XmlPullParser.START_TAG:
                    if("name".equals(xmlPullParser.getName()))
                        username=xmlPullParser.nextText();
                    if("password".equals(xmlPullParser.getName()))
                        password=xmlPullParser.nextText();
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType=xmlPullParser.next();
        }
        if(username!=null&&password!=null){
            et_username.setText(username);
            et_password.setText(password);
            cb_save.setChecked(true);
        }
    }


    //点击事件
    public void registe(View view){
        Intent intent=new Intent(LoginActivity.this,RegisteActivity.class);

        startActivity(intent);
    }

    //快速查询
    public void quickQuery(View view){
        Intent intent=new Intent(LoginActivity.this,QuiQueryActivity.class);
        startActivity(intent);
    }

    public void goMain(View view){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
