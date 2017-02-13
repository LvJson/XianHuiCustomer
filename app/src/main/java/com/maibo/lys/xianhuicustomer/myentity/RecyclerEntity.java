package com.maibo.lys.xianhuicustomer.myentity;

/**
 * Created by LYS on 2017/2/8.
 */

public class RecyclerEntity {
    private String one;
    private String two;
    private int type;

    public RecyclerEntity() {
    }

    public RecyclerEntity(String one, String two, int type) {
        this.one = one;
        this.two = two;
        this.type = type;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
