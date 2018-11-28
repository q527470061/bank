package com.star.bank.po;

/**
 * Created by Hello World on 2018/4/6.
 */

public class Licai {
    private String name;
    private String explain;
    private String percent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Licai(String name, String explain, String percent) {
        this.name = name;
        this.explain = explain;
        this.percent = percent;
    }
}
