package com.star.bank;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.star.bank.base.MyAdapter;
import com.star.bank.db.UserDao;
import com.star.bank.po.Card;
import com.star.bank.po.Transaction;
import com.star.bank.po.TransactionCustom;
import com.star.bank.po.User;
import com.star.bank.utils.JacksonUtil;
import com.star.bank.utils.StreamTool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.media.AudioTrack.SUCCESS;

@SuppressLint("HandlerLeak")
public class BillActivity extends AppCompatActivity {

    private ListView list_bill=null;

    private MyAdapter<TransactionCustom> myAdapter=null;

    private ArrayList<TransactionCustom> mData=null;
    private int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        selectBill();
    }

    //bindView
    public void bindView(){
        list_bill=findViewById(R.id.list_bill);
        list_bill.setAdapter(myAdapter);
    }


    //initBillPage
    public void initBillPage(ArrayList<TransactionCustom> mData){
        myAdapter=new MyAdapter<TransactionCustom>((ArrayList<TransactionCustom >) mData,R.layout.item_bill) {
            @Override
            public void bindView(ViewHolder holder, TransactionCustom obj) {
                switch (obj.getType()){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        holder.setText(R.id.item_type,"转入");
                        holder.setText(R.id.item_transmoney,"-"+obj.getTransmoney());
                        break;
                    case 4:
                        holder.setText(R.id.item_type,"转出");
                        holder.setText(R.id.item_transmoney,"-"+obj.getTransmoney());
                        break;
                }
                DateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");
                holder.setText(R.id.item_user,obj.getUsername());
                holder.setText(R.id.item_date,df.format(obj.getDate()));

            }
        };

        bindView();
    }




    //handler
    //异常
    final Handler exceptionHandler=new Handler(){
        public void handleMessage(Message msg) {
            Toast.makeText(BillActivity.this, "访问有误"+(int)msg.obj, Toast.LENGTH_LONG).show();
        }
    };


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                byte[] data=(byte[])msg.obj;
                String json_data=new String(data);
                mData=new ArrayList<TransactionCustom>();
                /*mData= JacksonUtil.readValue(json_data,new TypeReference<ArrayList<TransactionCustom>>() { });*/
                try {
                    JSONArray jsonArray = new JSONArray(json_data);
                    int t=jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        TransactionCustom transactionCustom=new TransactionCustom();
                        transactionCustom.setTransid(jsonObject.getString("transid"));
                        transactionCustom.setCardid(jsonObject.getString("cardid"));
                        transactionCustom.setType(jsonObject.getInt("type"));
                        transactionCustom.setTransmoney(jsonObject.getDouble("transmoney"));
                        transactionCustom.setUsername(jsonObject.getString("username"));
                        transactionCustom.setDate(new Date(new Long(jsonObject.getString("date"))));
                        mData.add(transactionCustom);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(mData==null)
                    Toast.makeText(BillActivity.this, json_data, Toast.LENGTH_LONG).show();
                else{
                    initBillPage(mData);
                }
            }
        }
    };


    //http请求
    public void selectBill(){
        UserDao userDao=new UserDao(BillActivity.this);
        User user=userDao.findUser();
        if(user==null){
            Toast.makeText(BillActivity.this,"抱歉，您未登录",Toast.LENGTH_LONG);
            return ;
        }


        final String path = "http://192.168.43.141:8080/BankServer/selectBill.action?userid="+user.getUserid();
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

}
