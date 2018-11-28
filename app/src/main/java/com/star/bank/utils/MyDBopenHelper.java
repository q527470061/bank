package com.star.bank.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hello World on 2018/4/7.
 */

public class MyDBopenHelper extends SQLiteOpenHelper {
    private Context context;

    public MyDBopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    //数据库第一参加时被调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String sql="create table  IF not EXISTS user (\n" +
                    "\tuserid varchar(20) PRIMARY KEY not null,\n" +
                    "\tusername varchar(20) not null,\n" +
                    "\tpassword varchar(20) not null,\n" +
                    "\tphone varchar(11) ,\n" +
                    "\taddress varchar(20),\n" +
                    "\ttotalmoney double(8,0),\n" +
                    "\tintegral int(4) )";
            sqLiteDatabase.execSQL(sql);
            Toast.makeText(context, "创建完成", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("DataBase",e.getMessage());
        }
    }

    //版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context,"暂无更新",Toast.LENGTH_LONG).show();
    }
}