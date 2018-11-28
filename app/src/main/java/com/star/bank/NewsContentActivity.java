package com.star.bank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.bank.po.News;

import org.w3c.dom.Text;

import java.io.IOException;

public class NewsContentActivity extends AppCompatActivity {

    private TextView txt_content;
    private ImageView pic1,pic2,pic3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        String newJson=getIntent().getStringExtra("news");
       /* User user = mapper.readValue(json, User.class);*/
        ObjectMapper mapper = new ObjectMapper();
        try {
            News news=mapper.readValue(newJson,News.class);
            bindView();
            txt_content.setText(news.getContent());
            pic1.setBackgroundResource(news.getRes1());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void bindView(){
        txt_content=findViewById(R.id.txt_content);
        pic1=findViewById(R.id.txt_pic1);
        pic2=findViewById(R.id.txt_pic1);
        pic3=findViewById(R.id.txt_pic1);
    }
}
