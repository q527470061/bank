package com.star.bank.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.star.bank.CardContentActivity;
import com.star.bank.QuiQueryActivity;
import com.star.bank.R;
import com.star.bank.base.MyAdapter;
import com.star.bank.po.Card;
import com.star.bank.utils.JacksonUtil;

import java.util.ArrayList;

/**
 * Created by Hello World on 2018/4/9.
 */

@SuppressLint("ValidFragment")
public class CardFragment extends Fragment{

    private View view=null;
    private Context mContext;

    private ArrayList<Card> mData;

    private MyAdapter<Card> myAdapter;

    private ListView listview_cards;



    @SuppressLint("ValidFragment")
    public CardFragment(Context mContext, ArrayList<Card> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.card_list, container, false);
        initCardList();
        return view;
    }

    public void initCardList(){
        myAdapter=new MyAdapter<Card>((ArrayList<Card>) mData,R.layout.card_item) {

            @Override
            public void bindView(ViewHolder holder, final Card obj) {
                holder.setText(R.id.item_card_cardid,obj.getCardid());
                holder.setText(R.id.item_card_money,String.valueOf(obj.getMoney()));

                holder.setOnClickListener(R.id.card_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String card_data= JacksonUtil.toJSon(obj);
                        Intent intent = new Intent(mContext,CardContentActivity.class);
                        intent.putExtra("card",card_data);
                        startActivity(intent);
                    }
                });
            }
        };
        bindView();
    }

    public void bindView(){
        listview_cards=view.findViewById(R.id.cards);
        listview_cards.setAdapter(myAdapter);
    }

}
