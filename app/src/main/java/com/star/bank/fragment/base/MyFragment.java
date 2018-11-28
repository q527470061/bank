package com.star.bank.fragment.base;/*
package com.star.bank.utils;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
*/
/**
使其成为复用类
 所用的数据由调用出传入
 1. 支持一般图片，文本传入
 2. 支持ListView嵌套 出入
 3. 支持GridView嵌套传入
 4. 支持监听器出入
可惜白写了，没必要，使主类复杂了好多
 *//*

@SuppressLint("ValidFragment")
public abstract class MyFragment<T> extends Fragment {

   */
/*
   fragemennt设计分析
	1.出入一般组件，text,image...
	2.嵌套组件，ListView,GridView...
	3.监听

	一般与嵌套分离

	所以一开始仅需要传入的仅有 item
	其它组件一概后续步骤
   private T mData;//仅仅存入一个对象，突然感觉没必要弄一个复用类,可怜
    private int[] mLayoutRes;           //布局id*//*


    */
/*
    输入要传入显示的数据
    依次由外到内的输入布局id，以防有嵌套ListView等
     *//*

    */
/**又要重新写
       private MyFragment(T mData,int...mLayoutRes){
        this.mData = mData;
        for(int i=0;i<mLayoutRes.length;i++){
            this.mLayoutRes[i]=mLayoutRes[i];
        }

    }*//*



  */
/*  private String content;
    public MyFragment(String content) {
        this.content = content;
    }*//*



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       */
/*
        View view = inflater.inflate(R.layout.main,container,false);
        ImageView imageView=view.findViewById(R.id.img_gun);
        imageView.setBackgroundResource(R.mipmap.pic1);*//*

        ViewHolder holder = new ViewHolder(inflater,container,mLayoutRes);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    public static class ViewHolder {

        private View item;   //存放convertView，由此查找各组件

        //构造方法，完成相关初始化
        private ViewHolder(LayoutInflater inflater, ViewGroup container, int...layoutRes) {
            View convertView = inflater.inflate(layoutRes[0],container,false);
            item = convertView;
        }

        */
/**
         * 设置文字
         *//*

        public ViewHolder setText(int id, CharSequence text) {
            View view =item.findViewById(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        */
/**
         * 设置图片
         *//*

        public ViewHolder setImageResource(int id, int drawableRes) {
            View view =item.findViewById(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        */
/**
         * 设置ListView,传入数据，layout_id
         *//*

        public ViewHolder setListViewResource(int id, int drawableRes) {
            View view =item.findViewById(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        */
/**
         * 设置点击监听
         *//*

        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            item.findViewById(id).setOnClickListener(listener);
            return this;
        }

        */
/**
         * 设置可见
         *//*

        public ViewHolder setVisibility(int id, int visible) {
            item.findViewById(id).setVisibility(visible);
            return this;
        }

        */
/**
         * 设置标签
         *//*

        public ViewHolder setTag(int id, Object obj) {
            item.findViewById(id).setTag(obj);
            return this;
        }

    }
}
*/
