package com.star.bank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.star.bank.base.MyAdapter;
import com.star.bank.db.UserDao;
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

/**
安全考虑：本使要把银行卡存到本地sqlite，改为在线查询
 */
@SuppressLint("HandlerLeak")
public class TransAccountActivity extends AppCompatActivity {

    private MyAdapter<Card>  myAdapter=null;

    private ArrayList<Card> mData = null;
    private int pos=0;

    private Spinner spinner=null;
    private TextView self_money=null,zhuanzhang;
    private EditText zhuan_money,zhuan_userid,zhuan_cardid,transmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_account);
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
            Toast.makeText(TransAccountActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    //第一个request
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String json_data=new String(data);
                mData= JacksonUtil.readValue(json_data,new TypeReference<ArrayList<Card>>() { });
                if(mData==null)
                    Toast.makeText(TransAccountActivity.this, json_data, Toast.LENGTH_LONG).show();
                else{
                    initPage(mData);
                }
            }
        }
    };

    //转账handler
    final Handler handlerTrans = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String message_data=new String(data);
                if(!"1".equals(message_data))
                    Toast.makeText(TransAccountActivity.this, message_data, Toast.LENGTH_LONG).show();
                else{

                    Intent intent=new Intent(TransAccountActivity.this,MessageActivity.class);
                    intent.putExtra("message","恭喜转账成功");
                    startActivity(intent);
                }
            }
        }
    };


    //http请求
    //事先查询所有的卡
    public void selectCards() throws IOException {
        UserDao userDao=new UserDao(TransAccountActivity.this);
        User user=userDao.findUser();
        if(user==null){
            Toast.makeText(TransAccountActivity.this,"抱歉，您未登录",Toast.LENGTH_LONG);
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

    //进行转账
    public void transAccount(View view){
        //获取本人
        String self_cardid=mData.get(pos).getCardid();
        String money_zhuan=zhuan_money.getText().toString().trim();
        //被转人
        String other_cardid=zhuan_cardid.getText().toString().trim();
        String other_userid=zhuan_userid.getText().toString().trim();
        String message=transmessage.getText().toString().trim();

        if (TextUtils.isEmpty( money_zhuan) || TextUtils.isEmpty(other_cardid)||TextUtils.isEmpty(other_userid)) {
            Toast.makeText(TransAccountActivity.this, "有空未填！", Toast.LENGTH_LONG).show();
            return;
        }

      /*  private String transid;

        private Date date;

        private Integer type;

        private Double transmoney;

        private String message;

        private String cardid;

        private String otherCardid;*/


        final String path = "http://192.168.43.141:8080/BankServer/transaction.action?cardid="+self_cardid+"" +
                "&transmoney="+money_zhuan+"&otherCardid="+other_cardid+"&other_userid="+other_userid+"&message="+message;
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
                        handlerTrans.sendMessage(msg);
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




    //填充spinner,初始化界面
    public void initPage(ArrayList<Card> mData){
        bindViews();
        myAdapter=new MyAdapter<Card>((ArrayList<Card>) mData,R.layout.spin_card) {

            @Override
            public void bindView(ViewHolder holder, final Card obj) {
                holder.setText(R.id.spin_card,obj.getCardid());
            }
        };
        bindSpinner();
        self_money.setText(String.valueOf(mData.get(0).getMoney()));
    }

    //bindView
    public void bindViews(){
        spinner=findViewById(R.id.self_cardid);
        self_money=findViewById(R.id.self_money);
        zhuan_cardid=findViewById(R.id.zhuan_cardid);
        zhuan_money=findViewById(R.id.zhuan_money);
        zhuan_userid=findViewById(R.id.zhuan_userid);
        zhuanzhang=findViewById(R.id.zhuanzhang);
        transmessage=findViewById(R.id.transmessage);
    }

    //Adapter,监听器
    public void bindSpinner(){
        spinner.setAdapter(myAdapter);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                /*String data = (String)spinner.getItemAtPosition(position);//从spinner中获取被选择的数据*/
                self_money.setText(String.valueOf(mData.get(position).getMoney()));
                pos=spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



}
