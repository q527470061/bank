package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.star.bank.R;
import com.star.bank.po.News;

import java.util.ArrayList;

/**
 * Created by Hello World on 2018/4/6.
 */

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup news_menu_group;
    private RadioButton rb_menu;
    private Context mContext;

    private NewsListFragment newsListFragment,newsListFragment2,newsListFragment3,newsListFragment4,newsListFragment5;

    private FragmentManager fManager;

    @SuppressLint("ValidFragment")
    public NewsFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news,container,false);

        news_menu_group=view.findViewById(R.id.news_menu_group);
        news_menu_group.setOnCheckedChangeListener(this);

        //获取第一个单选按钮，并设置其为选中状态
        rb_menu= (RadioButton) view.findViewById(R.id.tuijian);
        rb_menu.setChecked(true);
        return view;
    }

    //嵌套一个fragement
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fTransaction = getChildFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);

        switch (checkedId){
            case R.id.tuijian:
                if(newsListFragment == null){
                    ArrayList<News> mData=new ArrayList<>();

                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new2));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    newsListFragment=new NewsListFragment(mData,mContext);
                   /* fTransaction.add(R.id.news_list,newsListFragment);*/
                    fTransaction.add(R.id.news_list, newsListFragment);
                }else{
                    fTransaction.show(newsListFragment);
                }
                break;
            case R.id.industrynews:
                if(newsListFragment2 == null){
                    ArrayList<News> mData=new ArrayList<>();

                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new2));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    newsListFragment2=new NewsListFragment(mData,mContext);
                    fTransaction.add(R.id.news_list,newsListFragment2);
                }else{
                    fTransaction.show(newsListFragment2);
                }
                break;
            case R.id.self_news:
                if(newsListFragment3 == null){
                    ArrayList<News> mData=new ArrayList<>();

                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    newsListFragment3=new NewsListFragment(mData,mContext);
                    fTransaction.add(R.id.news_list,newsListFragment3);
                }else{
                    fTransaction.show(newsListFragment3);
                }
                break;
            case R.id.country_news:
                if(newsListFragment4 == null){
                    ArrayList<News> mData=new ArrayList<>();

                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new2));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    newsListFragment4=new NewsListFragment(mData,mContext);
                    fTransaction.add(R.id.news_list,newsListFragment4);
                }else{
                    fTransaction.show(newsListFragment4);
                }
                break;
            case R.id.international_news:
                if(newsListFragment5 == null){
                    ArrayList<News> mData=new ArrayList<>();

                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    mData.add(new News("一分钟了解美国历史教科书里的中国，百年国度如何看待千年古国",
                            "新一批在朝志愿军烈士陵园修缮工程开工仪式举行",R.mipmap.new1));
                    newsListFragment5=new NewsListFragment(mData,mContext);
                    fTransaction.add(R.id.news_list,newsListFragment5);
                }else{
                    fTransaction.show(newsListFragment5);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(newsListFragment != null)fragmentTransaction.hide(newsListFragment);
        if(newsListFragment2 != null)fragmentTransaction.hide(newsListFragment2);
        if(newsListFragment3 != null)fragmentTransaction.hide(newsListFragment3);
        if(newsListFragment4 != null)fragmentTransaction.hide(newsListFragment4);
        if(newsListFragment5 != null)fragmentTransaction.hide(newsListFragment5);
    }

}
