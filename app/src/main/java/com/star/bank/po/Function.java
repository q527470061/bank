package com.star.bank.po;

/**
 * Created by Hello World on 2018/4/5.
 */

public class Function {
    private int functionIcon;
    private String functionName;

    public Function(int functionIcon,String functionName) {
        this.functionIcon = functionIcon;
        this.functionName=functionName;
    }

    public int getFunctionIcon() {
        return functionIcon;
    }

    public void setFunctionIcon(int functionIcon) {
        this.functionIcon = functionIcon;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
