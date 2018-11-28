package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.star.bank.LoginActivity;
import com.star.bank.MainActivity;
import com.star.bank.R;
import com.star.bank.db.UserDao;
import com.star.bank.po.User;

import java.util.Set;

/**
 * Created by Hello World on 2018/4/8.
 */

@SuppressLint("ValidFragment")
public class SettingFragment extends Fragment implements View.OnClickListener{
    private TextView exit;
    private Context mContext;

    public SettingFragment(Context mContext){
        this.mContext=mContext;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting, container, false);
        exit=view.findViewById(R.id.exit);
        exit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        UserDao userDao=new UserDao(mContext);
        User user=userDao.findUser();
        if(user==null) {
            exit.setText("退出");
            Intent intent=new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }else{
            userDao.delete("user");
            Intent intent=new Intent(mContext,LoginActivity.class);
            startActivity(intent);
        }
    }
}
