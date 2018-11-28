package com.star.bank;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.star.bank.po.Card;
import com.star.bank.utils.JacksonUtil;

public class CardContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);

        String cardJson=getIntent().getStringExtra("card");
        Card card= JacksonUtil.readValue(cardJson,Card.class);

        final TextView cardid,userid,opendate,max,money;

        cardid=findViewById(R.id.content_carid);
        userid=findViewById(R.id.content_userid);
        opendate=findViewById(R.id.content_date);
        max=findViewById(R.id.content_max);
        money=findViewById(R.id.content_money);

        cardid.setText(card.getCardid());
        userid.setText(card.getUserid());
        opendate.setText(card.getDataopen().toString());
        max.setText(String.valueOf(card.getMax()));
        money.setText(String.valueOf(card.getMoney()));
    }



}
