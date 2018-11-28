package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.star.bank.R;

/**
 * Created by Hello World on 2018/4/6.
 */

public class NewContentFragment extends Fragment {

    @SuppressLint("ValidFragment")
    NewContentFragment() {
    }


   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newscontent, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        ImageView txt_pic1=(ImageView) view.findViewById(R.id.txt_pic1);
        //getArgument获取传递过来的Bundle对象
        txt_content.setText(getArguments().getString("content"));
        txt_pic1.setBackgroundResource(R.mipmap.pic1);
        return view;
    }*/

}
