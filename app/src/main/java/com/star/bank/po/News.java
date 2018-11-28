package com.star.bank.po;

/**
 * Created by Hello World on 2018/4/6.
 */

public class News {

    private String newsid;
    private String name;
    private String content;
    private int res1;
   /* private int res2;
    private int res3;*/

   public News(){}

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public News( String name, String content, int res1/*, int res2, int res*//*3*/) {
        this.name = name;
        this.content = content;
        this.res1 = res1;
/*        this.res2 = res2;
        this.res3 = res3;*/
    }

    public int getRes1() {
        return res1;

    }

    public void setRes1(int res1) {
        this.res1 = res1;
    }

/*    public int getRes2() {
        return res2;
    }

    public void setRes2(int res2) {
        this.res2 = res2;
    }

    public int getRes3() {
        return res3;
    }

    public void setRes3(int res3) {
        this.res3 = res3;
    }*/
}
