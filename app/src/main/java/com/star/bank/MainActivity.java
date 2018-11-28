package com.star.bank;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.star.bank.db.UserDao;
import com.star.bank.fragment.MainFragment;
import com.star.bank.fragment.NewsFragment;
import com.star.bank.fragment.SettingFragment;
import com.star.bank.po.Function;
import com.star.bank.po.Fund;
import com.star.bank.po.Licai;
import com.star.bank.po.User;
import com.star.bank.utils.MyDBopenHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup rg_menus;
    private RadioButton rb_menu;

    private MainFragment mainFragment;
    private NewsFragment newsFragment;
    private SettingFragment settingFragment;

    private FragmentManager fManager;

    private long exitTime = 0;

    private TextView text_login,exit;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=MainActivity.this;
        UserDao userDao=new UserDao(mContext);
        userDao.opendb(mContext);
        User user =userDao.findUser();

        TextView text_login=findViewById(R.id.login);
        TextView exit=LayoutInflater.from(MainActivity.this).inflate(R.layout.setting,null).findViewById(R.id.exit);
        if(user!=null) {
            text_login.setText(user.getUsername());
            exit.setText("退出");
        }else{
            text_login.setText("登录");
            exit.setText("请登陆");
        }


        fManager = getFragmentManager();

        rg_menus=findViewById(R.id.rg_menus);
        rg_menus.setOnCheckedChangeListener(this);

        //获取第一个单选按钮，并设置其为选中状态
        rb_menu= (RadioButton) findViewById(R.id.rb_main);
        rb_menu.setChecked(true);

        text_login=findViewById(R.id.login);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_main:
                if(mainFragment==null){
                    ArrayList<Function> mData=new ArrayList<Function>();
                    mData.add(new Function(R.mipmap.menu1,"账户管理"));
                    mData.add(new Function(R.mipmap.menu2,"转账汇款"));
                    mData.add(new Function(R.mipmap.menu3,"信用卡"));
                    mData.add(new Function(R.mipmap.menu4,"贷款"));
                    mData.add(new Function(R.mipmap.menu1,"结汇购汇"));
                    mData.add(new Function(R.mipmap.menu2,"中银理财"));
                    mData.add(new Function(R.mipmap.menu4,"基金"));
                    mData.add(new Function(R.mipmap.menu3,"更多"));

                    ArrayList<Licai> licaiData=new ArrayList<>();
                    licaiData.add(new Licai("尊享2018年第15期","20万起|182天|期间不可赎回","5.05%"));
                    licaiData.add(new Licai("尊享2018年第15期","20万起|182天|期间不可赎回","5.05%"));

                    ArrayList<Fund> fundData=new ArrayList<>();

                    mainFragment = new MainFragment(MainActivity.this,R.mipmap.pic1,mData,licaiData,fundData);
                    fTransaction.add(R.id.ly_content,mainFragment);
                }else{
                    fTransaction.show(mainFragment);
                }
                break;
            case R.id.rb_news:
                if(newsFragment == null){
                    newsFragment = new NewsFragment(MainActivity.this);
                    fTransaction.add(R.id.ly_content,newsFragment);
                }else{
                    fTransaction.show(newsFragment);
                }
                break;
            /*case R.id.rb_better:
                if(fg3 == null){
                    fg3 = new MyFragment("第三个Fragment");
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
                */
            case R.id.rb_setting:
                if(settingFragment == null){
                    settingFragment = new SettingFragment(MainActivity.this);
                    fTransaction.add(R.id.ly_content,settingFragment);
                }else{
                    fTransaction.show(settingFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(mainFragment != null)fragmentTransaction.hide(mainFragment);
        if(newsFragment != null)fragmentTransaction.hide(newsFragment);
        if(settingFragment != null)fragmentTransaction.hide(settingFragment);
       /* if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);*/
    }

    public void login(View view){
        Intent intent = new Intent(this,LoginActivity.class);

        startActivity(intent);
    }


    //账户管理
    public void accountManage(View view){

    }


    //点击回退键的处理：判断Fragment栈中是否有Fragment
    //没，双击退出程序，否则像是Toast提示
    //有，popbackstack弹出栈
    @Override
    public void onBackPressed() {
        if (fManager.getBackStackEntryCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        } else {
            fManager.popBackStack();
            /*txt_title.setText("新闻列表");*/
        }
    }


}
