package com.star.bank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.star.bank.po.User;
import com.star.bank.utils.MyDBopenHelper;

/**
 * Created by Hello World on 2018/4/9.
 */

public class UserDao {

    Context context;
    MyDBopenHelper dbhelper;
    public SQLiteDatabase sqlitedatabase;

    public UserDao(Context context) {
        super();
        this.context = context;
    }
    //打开数据库连接
    public void opendb(Context context) {
        dbhelper = new MyDBopenHelper(context,"bank1.db",null,1);
        sqlitedatabase = dbhelper.getWritableDatabase();
    }


    //关闭数据库连接
    public void closedb(Context context) {
        if(sqlitedatabase.isOpen())
        {
            sqlitedatabase.close();
        }
    }
    //插入表数据
    public void insert (String table_name,ContentValues values) {
        opendb(context);
        sqlitedatabase.insert(table_name, null, values);
        closedb(context);
    }
    //更新数据
    public int updatatable(String table_name,ContentValues values,int ID) {
        opendb(context);
        return sqlitedatabase.update(table_name, values, " Type_ID = ? ", new String[]{String.valueOf(ID)});
    }

    //删除表数据
    public void delete(String table_name) {
        opendb(context);
        try{

            sqlitedatabase.delete(table_name, null, null);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            closedb(context);
        }
    }



    //数据库表第一条记录，user表只存一条记录
    public User findUser()
    {
        opendb(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM user",
                null);
        //存在数据才返回true
        if(cursor.moveToFirst())
        {
            User user=new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setUserid(cursor.getString(cursor.getColumnIndex("userid")));
            user.setTotalmoney(cursor.getDouble(cursor.getColumnIndex("totalmoney")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setIntegral(cursor.getInt(cursor.getColumnIndex("integral")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            return user;
        }
        cursor.close();
        return null;
    }
}
