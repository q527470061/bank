package com.star.bank.po;

import java.util.Date;

public class Card {
    private String cardid;

    private String password;

    private Date dataopen;

    private Double money;

    private Double max;

    private String userid;

    public Card() {
    }

    public Card(String cardid, Double money) {
        this.cardid = cardid;
        this.money = money;
    }

    public Card(String cardid, String password, Date dataopen, Double money, Double max, String userid) {
        this.cardid = cardid;
        this.password = password;
        this.dataopen = dataopen;
        this.money = money;
        this.max = max;
        this.userid = userid;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid == null ? null : cardid.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getDataopen() {
        return dataopen;
    }

    public void setDataopen(Date dataopen) {
        this.dataopen = dataopen;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}