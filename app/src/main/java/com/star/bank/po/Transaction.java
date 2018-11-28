package com.star.bank.po;

import java.util.Date;

public class Transaction {
    private String transid;

    private Date date;

    private Integer type;

    private Double transmoney;

    private String message;

    private String cardid;

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid == null ? null : transid.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getTransmoney() {
        return transmoney;
    }

    public void setTransmoney(Double transmoney) {
        this.transmoney = transmoney;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid == null ? null : cardid.trim();
    }
}