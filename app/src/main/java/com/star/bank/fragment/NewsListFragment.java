package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.bank.NewsContentActivity;
import com.star.bank.R;
import com.star.bank.base.MyAdapter;
import com.star.bank.po.News;

import java.util.ArrayList;

/**
 * Created by Hello World on 2018/4/7.
 */

@SuppressLint("ValidFragment")
public class NewsListFragment extends Fragment implements  AdapterView.OnItemClickListener{

    private ArrayList<News> mData;
    private MyAdapter<News> myAdapter;
    private ListView listView;
    private View  view;
    private Context mContext;

    public NewsListFragment(ArrayList<News> mData,Context mContext){
        this.mData=mData;
        this.mContext=mContext;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newslistview,container,false);

        initNewsList();
        return view;
    }

    public void initNewsList(){
        myAdapter=new MyAdapter<News>((ArrayList<News>) mData,R.layout.newslist) {

            //将每个条目中的组件进行填充
            @Override
            public void bindView(ViewHolder holder, News obj) {
                holder.setText(R.id.news_name,obj.getName());
                holder.setImageResource(R.id.news_pic1,obj.getRes1());
/*                holder.setImageResource(R.id.news_pic2,obj.getRes2());
                holder.setImageResource(R.id.news_pic3,obj.getRes3());*/
            }

        };
        bindView();

    }

    public void bindView(){
        listView=view.findViewById(R.id.news_lists);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* FragmentTransaction fTransaction = getChildFragmentManager().beginTransaction();*/
       /* NewContentFragment ncFragment = new NewContentFragment();*/
        Intent intent = new Intent();
        intent = intent.setClass(mContext, NewsContentActivity.class);

        Bundle bd = new Bundle();

        ObjectMapper mapper = new ObjectMapper();
        try {
            intent.putExtra("news",mapper.writeValueAsString(mData.get(position)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mContext.startActivity(intent);
        /*ncFragment.setArguments(bd);*/
        //获取Activity的控件
        /*TextView txt_title = (TextView) getActivity().findViewById(R.id.txt_title);
        txt_title.setText(mData.get(position).getName());*/
        //加上Fragment替换动画
        /*fTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);

        fTransaction.replace(R.id.news_list, ncFragment);
        //调用addToBackStack将Fragment添加到栈中
        fTransaction.addToBackStack(null);
        fTransaction.commit();*/
    }
}
