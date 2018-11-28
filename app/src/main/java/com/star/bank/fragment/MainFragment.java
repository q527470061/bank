package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.star.bank.AccountActivity;
import com.star.bank.BillActivity;
import com.star.bank.R;
import com.star.bank.TransAccountActivity;
import com.star.bank.base.MyAdapter;
import com.star.bank.po.Function;
import com.star.bank.po.Fund;
import com.star.bank.po.Licai;

import java.util.ArrayList;

/**
 * Created by Hello World on 2018/4/6.
 */
/**
如
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements View.OnClickListener{
    private int resc;//第一个图片
    private ArrayList<Function> mData;
    private ArrayList<Licai> mLicai;
    private ArrayList<Fund> mFund;

    private View view =null;
    private ImageView img_gun=null;
    private ListView licaiListView;
    private GridView gridView=null;
    private ImageView img_function=null;
    private TextView txt_function=null;
    private RelativeLayout account_menu,zhuanzhang_menu,bill_menu;

    private MyAdapter<Function> myAdapter1;
    private MyAdapter<Licai> myAdapter2;
    private MyAdapter<Fund> myAdapter3;

    private Context mContext;

    public MainFragment(Context mContext,int resc,ArrayList<Function> mData,ArrayList<Licai> mLicai, ArrayList<Fund> mFund) {
        this.mContext=mContext;
        this.resc=resc;
        this.mData=mData;
        this.mLicai=mLicai;
        this.mFund=mFund;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main,container,false);
        img_gun=view.findViewById(R.id.img_gun);
        img_gun.setBackgroundResource(R.mipmap.pic1);
        /*initFunction();*/
        initLicai();
        vindFunctionViews();
        return view;
    }

    /**
    因为菜单获取不便，舍去gridview
     */
    public void initFunction(){
        myAdapter1=new MyAdapter<Function>((ArrayList<Function>) mData,R.layout.item_grid_function) {

            //将每个条目中的组件进行填充
            @Override
            public void bindView(MyAdapter.ViewHolder holder, Function function) {
                holder.setImageResource(R.id.img_function,function.getFunctionIcon());
                holder.setText(R.id.txt_function,function.getFunctionName());
            }
        };

        bindmDateViews();

    }

    public void initLicai(){
        //licai
        myAdapter2=new MyAdapter<Licai>((ArrayList<Licai>) mLicai,R.layout.item_list_licai){

            //将每个条目中的组件进行填充
            @Override
            public void bindView(ViewHolder holder, Licai obj) {
                licaiListView=view.findViewById(R.id.licai);
                holder.setText(R.id.percent,obj.getPercent());
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.name,obj.getExplain());
                licaiListView.addHeaderView(holder.setHeader(R.layout.licai_header));
            }
        };
        bindLicaiViews();
    }

    public void initFund(){
        //licai
        myAdapter3=new MyAdapter<Fund>((ArrayList<Fund>) mFund,R.layout.item_list_licai){

            @Override
            public void bindView(ViewHolder holder, Fund obj) {
                licaiListView=view.findViewById(R.id.fund);
                holder.setText(R.id.percent,obj.getPercent());
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.name,obj.getExplain());
                licaiListView.addHeaderView(holder.setHeader(R.layout.licai_header));
            }
        };
        bindFundViews();
    }

    public void bindFundViews(){
        licaiListView=view.findViewById(R.id.licai);
        licaiListView.setAdapter(myAdapter3);
    }

    public void bindLicaiViews(){
        licaiListView=view.findViewById(R.id.fund);
        licaiListView.setAdapter(myAdapter2);
    }

    private void bindmDateViews(){
       /* gridView=view.findViewById(R.id.grid_function);
        gridView.setAdapter(myAdapter1);*/
    }

    //綁定function
    public void vindFunctionViews(){
        account_menu=view.findViewById(R.id.accout_menu);
        account_menu.setOnClickListener(this);
        zhuanzhang_menu=view.findViewById(R.id.function_zhuanzhang);
        zhuanzhang_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, TransAccountActivity.class);
                startActivity(intent);
            }
        });
        bill_menu=view.findViewById(R.id.bill_menu);
        bill_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, BillActivity.class);
                startActivity(intent);
            }
        });
    }


    //账户管理
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mContext, AccountActivity.class);
        startActivity(intent);
    }
}
